package pl.com.bottega.hrs.model;

import pl.com.bottega.hrs.infrastructure.StandardTimeProvider;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
public class DepartmentAssignment {

    @Embeddable
    public static class DepartmentAssignmentId implements Serializable {

        @Column(name = "emp_no")
        private Integer empNo;

        @ManyToOne
        @JoinColumn(name = "dept_no")
        private Department department;

        DepartmentAssignmentId() {}

        public DepartmentAssignmentId(Integer empNo, Department department) {
            this.empNo = empNo;
            this.department = department;
        }

        public Integer getEmpNo() {
            return empNo;
        }

        public void setEmpNo(Integer empNo) {
            this.empNo = empNo;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }
    }

    @Transient
    private TimeProvider timeProvider = new StandardTimeProvider();

    @EmbeddedId
    private DepartmentAssignmentId id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    DepartmentAssignment() {}

    public DepartmentAssignment(Integer empNo, Department department, TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        id = new DepartmentAssignmentId(empNo, department);
        fromDate = timeProvider.today();
        toDate = TimeProvider.MAX_DATE;
    }

    public Department getDepartment() {
        return id.department;
    }

    public boolean isAssigned(Department department) {
        return isCurrent() &&
                department.equals(id.department);
    }

    public boolean isCurrent() {
        return toDate.isAfter(timeProvider.today());
    }

    public void unassign() {
        toDate = timeProvider.today();
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

}
