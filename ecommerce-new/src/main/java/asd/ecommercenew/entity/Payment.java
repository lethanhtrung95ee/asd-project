package asd.ecommercenew.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @OneToMany
    private List<OrderLine> orderLines;

    @OneToOne(mappedBy = "payment")
    private Shipping shipping;

    @OneToOne(mappedBy = "payment")
    private PaymentType paymentType;

    @ManyToOne
    private User user;
}
