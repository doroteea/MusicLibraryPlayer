package com.lab4.demo.payment;

import com.lab4.demo.payment.DTO.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class PaymentServiceTest {

    @InjectMocks
    private  PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService();
    }

    @Test
    void createSession() {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .name("Name")
                .track_id(1L)
                .user_id(1L)
                .build();

        paymentService.createSession(paymentDTO);
    }
}