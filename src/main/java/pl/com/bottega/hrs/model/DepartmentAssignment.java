package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
public class DepartmentAssignment {

    private TimeProvider timeProvider;

    @EmbeddedId
    private DepartmentAssignmentId id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DepartmentAssignment() {
    }

    public DepartmentAssignment(Integer empNo, Department department, TimeProvider timeProvider) {
        id = new DepartmentAssignmentId(empNo, department);
        this.timeProvider = timeProvider;
        fromDate = timeProvider.today();
        toDate = Constants.MAX_DATE;
    }


    public Department getDepartment() {
        return id.department;
    }

    public boolean isAssigned(Department department) {
        return toDate.isAfter(timeProvider.today()) && department.equals(id.department);
    }

    public void unassign() {
        toDate = timeProvider.today();
    }

    public boolean isCurrent() {
        return toDate.isAfter(timeProvider.today());
    }


    @Embeddable
    public static class DepartmentAssignmentId implements Serializable{

        @Column(name = "emp_no")
        private Integer empNo;

        @ManyToOne
        @JoinColumn(name = "dept_no")
        private Department department;


        public DepartmentAssignmentId(Integer empNo, Department department) {
            this.empNo = empNo;
            this.department = department;
        }

        public DepartmentAssignmentId() {
        }
    }
}
