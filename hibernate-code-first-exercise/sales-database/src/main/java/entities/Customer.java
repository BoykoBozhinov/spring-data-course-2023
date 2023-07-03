package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{

    @Column(length = 65)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(name = "credit_card_number", length = 50)
    private String creditCardNumber;

    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;

    public Customer() {
    }
}
