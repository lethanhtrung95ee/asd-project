package asd.ecommercenew.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoRequest {

    private String description;

    private String categories;

    private String model;

    private double price;

    private int remainQuantity;

    private String productName;

    private List<FileDtoRequest> images;
}
