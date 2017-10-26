package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "dept_no", columnDefinition = "char(4)")
    private String departmentNumber;

    @Column(name = "dept_name")
    private String departmentName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_no")
    private Collection<DepartmentAssignment> departmentAssignments = new LinkedList<>();


}
