package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
public class DepartmentAssignment {
    //połącz z Employee i z Department

    @EmbeddedId
    private DepartmentAssignmentId id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public LocalDate getToDate() {
        return toDate;
    }

    public DepartmentAssignmentId getId() {
        return id;
    }

    @Embeddable
    public static class DepartmentAssignmentId implements Serializable{

        @Column(name = "emp_no")
        private Integer empNo;

        @Column(name = "dept_no", columnDefinition = "char(4)")
        private String departmentNumber;

        public Integer getEmpNo() {
            return empNo;
        }

        public String getDepartmentNumber() {
            return departmentNumber;
        }

    }
}
