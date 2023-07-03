package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity{

    @Column(name = "visitation_date")
    private LocalDate date;

    @Column(length = 1000)
    private String comments;

    @ManyToOne
    private Patient patient;
}
