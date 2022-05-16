package com.lab4.demo.payment.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    private String name;
    private Long user_id;
    private Long track_id;
}
