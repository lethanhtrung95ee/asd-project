package asd.ecommercenew.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    private String numberOnCard;

    private String dateOnCard;

    private int cvv;

    private String billingAddress;
}
