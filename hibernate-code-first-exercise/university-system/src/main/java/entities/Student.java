package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "average_grade")
    private Double averageGrade;

    @Column
    private int attendance;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    public Student() {
    }
}
