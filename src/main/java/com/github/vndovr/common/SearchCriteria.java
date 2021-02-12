package com.github.vndovr.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

  public enum Operation {
    EQUAL, GREATER, LESS, GREATER_OR_EQUAL, LESS_OR_EQUAL, LIKE
  }

  private String key;

  private Operation operation;

  private Object value;
}
