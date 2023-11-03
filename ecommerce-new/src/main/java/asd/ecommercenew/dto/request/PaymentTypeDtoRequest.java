package asd.ecommercenew.dto.request;

import asd.ecommercenew.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeDtoRequest {

    private String numberOnCard;

    private String dateOnCard;

    private int cvv;

    private String billingAddress;
}
