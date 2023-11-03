package asd.ecommercenew.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "`user`")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<Role> roles;

    private String externalId;
    private String externalType;

    private boolean active;

    private boolean isDeleted;

    @OneToOne
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<PaymentType> paymentTypes;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}