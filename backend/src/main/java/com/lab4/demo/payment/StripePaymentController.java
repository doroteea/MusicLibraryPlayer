package com.lab4.demo.payment;

import org.springframework.beans.factory.annotation.Value;

public class StripePaymentController {

    @Value("${stripe.apiKey}")
    String stripeKey;

}
