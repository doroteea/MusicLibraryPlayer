package com.lab4.demo.payment;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.payment.DTO.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.lab4.demo.UrlMapping.PAYMENT;
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

        paymentController.checkout(paymentDTO);

        ResultActions result = performPostWithRequestBody(PAYMENT, paymentDTO);
        result.andExpect(status().isOk());
    }
}