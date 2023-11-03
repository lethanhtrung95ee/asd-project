package asd.ecommercenew.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoResponse {

    private int id;

    private String description;

    private String categories;

    private String model;

    private double price;

    private int remainQuantity;

    private String productName;

    private CouponDtoResponse coupon;

}
