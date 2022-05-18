package com.lab4.demo.payment;

import com.lab4.demo.payment.DTO.PaymentDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.IOException;

import static com.lab4.demo.UrlMapping.*;
import static spark.Spark.port;
import static spark.Spark.post;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final ReportServiceFactory reportServiceFactory;

    public void createSession(PaymentDTO paymentDTO) {
        port(4242);
        Stripe.apiKey = "sk_test_51KzStYBeCTO9xhNTcPx39YpKDBG9dKcAdb9FOec7lkJr7AuRmfTg2OsUujZoKglyvC1NDuVCnGbcjrSAlufSuK1l00F7OrmT96";

        post(PAYMENT, (request, response) -> {

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(SUCCESS_PAYMENT)
                            .setCancelUrl(FAIL_PAYMENT)
                            .addLineItem(SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount(2000L)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(paymentDTO.getName())
                                                            .build())
                                            .build())
                                    .build())
                            .build();

            Session session = Session.create(params);

            response.redirect(session.getUrl(), 303);
            return "";
        });
    }

    public String export(ReportType type, Long id) throws IOException {
        return reportServiceFactory.getReportService(type).export(id);
    }
}
