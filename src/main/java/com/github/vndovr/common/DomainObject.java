package com.github.vndovr.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

/**
 * Base class for domain object with versioning
 * 
 * @author Vladimir Radchuk
 *
 */
@Data
@MappedSuperclass
public class DomainObject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;


  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime creationTimestamp;

  @UpdateTimestamp
  private LocalDateTime updateTimestamp;

  @Version
  private long version;
}
