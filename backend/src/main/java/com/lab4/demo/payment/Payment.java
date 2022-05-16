package com.lab4.demo.payment;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private Double amount;
    private String featureRequest;
}
