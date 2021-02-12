package com.github.vndovr.common;

import java.time.LocalDate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.github.vndovr.common.SearchCriteria.Operation;


public class GenericSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = 1L;

  private SearchCriteria criteria;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    if (criteria.getOperation().equals(Operation.GREATER_OR_EQUAL)) {
      if (root.get(criteria.getKey()).getJavaType() == LocalDate.class)
        return builder.greaterThanOrEqualTo(root.<LocalDate>get(criteria.getKey()),
            (LocalDate) criteria.getValue());
      else
        return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()),
            criteria.getValue().toString());
    }
    if (criteria.getOperation().equals(Operation.LESS_OR_EQUAL)) {
      if (root.get(criteria.getKey()).getJavaType() == LocalDate.class)
        return builder.lessThanOrEqualTo(root.<LocalDate>get(criteria.getKey()),
            (LocalDate) criteria.getValue());
      else
        return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()),
            criteria.getValue().toString());
    }
    if (criteria.getOperation().equals(Operation.EQUAL)) {
      return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue());
    }
    if (criteria.getOperation().equals(Operation.LIKE)) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
      } else {
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }
    return builder.isTrue(builder.literal(true));
  }

  public static <T> GenericSpecification<T> of(SearchCriteria criteria, Class<T> clazz) {
    GenericSpecification<T> genericSpecification = new GenericSpecification<>();
    genericSpecification.criteria = criteria;
    return genericSpecification;
  }
}
