package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicament")
public class Medicament extends BaseEntity{

    @Column
    private String name;

    @ManyToOne
    private Diagnose diagnose;
}
