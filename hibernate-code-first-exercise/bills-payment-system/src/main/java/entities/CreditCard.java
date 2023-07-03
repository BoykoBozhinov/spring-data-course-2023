package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetail{

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "expiration_month", nullable = false)
    private LocalDate expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private LocalDate expirationYear;

    public CreditCard() {
    }
}
