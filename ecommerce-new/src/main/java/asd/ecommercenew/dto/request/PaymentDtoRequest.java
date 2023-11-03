package asd.ecommercenew.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDtoRequest {

    private PaymentTypeDtoRequest paymentTypeDtoRequest;
    private String shippingAddress;
}
