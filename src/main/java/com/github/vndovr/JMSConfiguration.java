package com.github.vndovr;

import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;

@Configuration
@EnableJms
@AllArgsConstructor
public class JMSConfiguration {

  /**
   * Listener factory for ActiveMQ
   * 
   * @return
   */
  @Bean
  public JmsListenerContainerFactory<?> topicListenerFactory(MessageConverter messageConverter) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setMessageConverter(messageConverter);
    factory.setPubSubDomain(true);
    return factory;
  }

  /**
   * Message converter for Java8 date types
   * 
   * @return
   */
  @Bean
  public MessageConverter messageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter
        .setObjectMapper(new ObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule()));
    return converter;
  }

  @Bean
  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory);
    template.setMessageConverter(messageConverter());
    template.setPubSubDomain(true);
    return template;
  }
}
