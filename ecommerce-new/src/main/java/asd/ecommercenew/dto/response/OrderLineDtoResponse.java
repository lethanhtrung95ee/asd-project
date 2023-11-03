package asd.ecommercenew.dto.response;

import asd.ecommercenew.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDtoResponse {

    private int id;

    private ProductDtoResponse product;

    private int quantity;
}
