package com.payment.challange.data.dtos;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum StatusTransacao {
    paid,
    waiting_funds
}
