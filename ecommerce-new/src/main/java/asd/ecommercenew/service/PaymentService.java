package asd.ecommercenew.service;


import asd.ecommercenew.dto.request.PaymentDtoRequest;
import asd.ecommercenew.dto.response.PaymentDtoResponse;

import java.util.List;

public interface PaymentService {

    void payment(PaymentDtoRequest paymentDtoRequest);

    List<PaymentDtoResponse> getAllPaymentHistory();
}
