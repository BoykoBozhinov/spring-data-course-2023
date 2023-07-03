package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail{

    @Column(length = 60, nullable = false)
    private String name;

    @Column(name = "swift_code", nullable = false)
    private String swiftCode;

    public BankAccount() {
    }
}
