package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String comments;

    @ManyToOne
    private Patient patient;

    @ManyToMany(mappedBy = "diagnose")
    private Set<Medicament> medicament;
}
