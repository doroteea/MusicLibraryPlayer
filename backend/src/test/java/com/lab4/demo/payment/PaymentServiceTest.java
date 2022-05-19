package com.lab4.demo.payment;

import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

class PaymentServiceTest {

    @InjectMocks
    private  PaymentService paymentService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private  PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(reportServiceFactory,paymentMapper,paymentRepository);
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

    @Test
    void report() throws IOException {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().username("ana").build()));
//        when(reportServiceFactory.getReportService(ReportType.CSV).export(1L)).thenReturn("CSV");
//
//        String report = paymentService.export(ReportType.CSV, 1L);
//        Assertions.assertEquals("CSV",report);
//
//        when(reportServiceFactory.getReportService(ReportType.PDF).export(1L)).thenReturn("PDF");
//
//        report = paymentService.export(ReportType.PDF, 1L);
//        Assertions.assertEquals("PDF",report);
    }
}