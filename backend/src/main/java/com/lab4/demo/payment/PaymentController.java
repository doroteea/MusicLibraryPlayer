package com.lab4.demo.payment;

import com.lab4.demo.payment.DTO.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lab4.demo.UrlMapping.PAYMENT;

@RestController
@RequestMapping(PAYMENT)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public void checkout(@RequestBody PaymentDTO paymentDTO) {
        paymentService.createSession(paymentDTO);
    }
}
