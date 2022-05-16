package com.lab4.demo.payment;

import com.lab4.demo.payment.DTO.PaymentDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(PAYMENT)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public void checkout(@RequestBody PaymentDTO paymentDTO) throws StripeException {
        paymentService.createSession(paymentDTO);
//        System.out.println(session.getId());

//        response.redirect(session.getUrl(), 303);
//        Session session = paymentService.createSession(paymentDTO);
//        System.out.println(session.getId());
//        PaymentResponse paymentResponse = PaymentResponse.builder()
//                .sessionId(session.getId())
//                .build();
//        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
    }

//    @PostMapping(CHECKOUT)
//    public CreatePaymentResponse createPayment(@RequestBody CreatePayment createPayment) throws StripeException {
//        Stripe.apiKey = "sk_test_51KzStYBeCTO9xhNTcPx39YpKDBG9dKcAdb9FOec7lkJr7AuRmfTg2OsUujZoKglyvC1NDuVCnGbcjrSAlufSuK1l00F7OrmT96";
//
//        PaymentIntentCreateParams params =
//                    PaymentIntentCreateParams.builder()
//                            .setAmount(200L)
//                            .setCurrency("eur")
//                            .build();
//
//            // Create a PaymentIntent with the order amount and currency
//            PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//            return new CreatePaymentResponse(paymentIntent.getClientSecret());
//
//    }
//



}
