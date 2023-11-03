package asd.ecommercenew.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String categories;

    private String model;

    private double price;

    private int remainQuantity;

    private String productName;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLine;

    @ManyToOne
    private Coupon coupon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Image> images;
}
