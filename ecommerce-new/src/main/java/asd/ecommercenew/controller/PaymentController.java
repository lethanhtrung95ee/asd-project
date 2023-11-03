package asd.ecommercenew.controller;

import asd.ecommercenew.dto.request.PaymentDtoRequest;
import asd.ecommercenew.dto.response.PaymentDtoResponse;
import asd.ecommercenew.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
@CrossOrigin
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    ResponseEntity<String> payment(@RequestBody PaymentDtoRequest paymentRequest) {
        paymentService.payment(paymentRequest);
        return new ResponseEntity<>("Payment successfully", HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<PaymentDtoResponse>> getAllPaymentHistory() {
        return new ResponseEntity<>(paymentService.getAllPaymentHistory(), HttpStatus.OK);
    }
}
