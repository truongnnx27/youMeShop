package com.poly.entity;


import lombok.Builder;
import lombok.ToString;

@ToString
public abstract class Payment {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
