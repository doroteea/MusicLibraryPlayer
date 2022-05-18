package com.lab4.demo.payment;

import com.lab4.demo.payment.DTO.PaymentDTO;
import com.lab4.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
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

    @RequestMapping(EXPORT_REPORT)
    public ResponseEntity<InputStreamResource> exportReport(@PathVariable ReportType type, @PathVariable Long id) throws IOException {
        FileInputStream file = new FileInputStream(paymentService.export(type,id));
        InputStreamResource inputStreamResource =  new InputStreamResource(file);
        return ResponseEntity.ok()
                .body(inputStreamResource);
    }

}
