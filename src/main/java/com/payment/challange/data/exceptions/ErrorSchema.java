package com.payment.challange.data.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorSchema {
    private String field;
    private String message;
}
