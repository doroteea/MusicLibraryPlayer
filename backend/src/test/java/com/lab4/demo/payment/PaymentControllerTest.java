package com.lab4.demo.payment;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.payment.model.DTO.PaymentDTO;
import com.lab4.demo.report.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;

import static com.lab4.demo.UrlMapping.EXPORT_REPORT;
import static com.lab4.demo.UrlMapping.PAYMENT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PaymentControllerTest extends BaseControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private  PaymentService paymentService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void checkout() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .name("Name")
                .track_id(1L)
                .user_id(1L)
                .build();

//        paymentController.checkout(paymentDTO);

        ResultActions result = performPostWithRequestBody(PAYMENT, paymentDTO);
        result.andExpect(status().isOk());
    }

    @Test
    void export() throws Exception {
        when(paymentService.export(ReportType.CSV,1L)).thenReturn("src/main/resources/MyTracks.csv");
        FileInputStream file = new FileInputStream(paymentService.export(ReportType.CSV,1L));
        InputStreamResource inputStreamResource =  new InputStreamResource(file);

        ResultActions result = performGetWithPathVariable(PAYMENT + EXPORT_REPORT,ReportType.CSV,1L);
        result.andExpect(status().isOk());
    }
}