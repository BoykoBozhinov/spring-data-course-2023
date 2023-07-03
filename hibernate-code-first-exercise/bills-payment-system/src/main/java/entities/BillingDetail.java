package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetail extends BaseEntity{

    @Column(length = 100, nullable = false)
    private String number;

    @ManyToOne
    private User owner;

    public BillingDetail() {
    }
}
