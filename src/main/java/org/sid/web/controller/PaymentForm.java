package org.sid.web.controller;


import lombok.Data;

@Data
public class PaymentForm {
    private String type;
    private Long cardNumber;
    private Long idOrder;
}
