package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salary {


    @EmbeddedId
    private SalaryId id;

    private Integer salary;

    @Column(name = "to_date")
    private LocalDate toDate;




    public LocalDate getToDate() {
        return toDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Embeddable
    public static class SalaryId implements Serializable{
        @Column(name = "emp_no")
        private Integer empNo;
        @Column(name = "from_Date")
        private LocalDate fromDate;

        public SalaryId() {}

        public SalaryId(Integer empNo, LocalDate fromDate) {
            this.empNo = empNo;
            this.fromDate = fromDate;
        }
    }

    public Salary(){}

    public Salary(SalaryId id, Integer salary, LocalDate toDate) {
        this.id = id;
        this.salary = salary;
        this.toDate = toDate;
    }

//    public Salary(Integer empNo, Integer salary, LocalDate fromDate, LocalDate toDate) {
//        this.id.empNo = empNo;
//        this.id.fromDate = fromDate;
//        this.salary = salary;
//        this.toDate = toDate;
//    }
}
