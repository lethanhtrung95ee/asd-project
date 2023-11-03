package asd.ecommercenew.dto.response;

import asd.ecommercenew.entity.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoResponse {

    private int id;

    private List<OrderLineDtoResponse> orderLines;

}
