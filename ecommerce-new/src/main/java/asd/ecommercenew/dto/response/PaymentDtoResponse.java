package asd.ecommercenew.dto.response;

import asd.ecommercenew.entity.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDtoResponse {

    private int id;

    private LocalDateTime creationTime;

    private List<OrderLineDtoResponse> orderLines;
}
