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

    @Transient
    private TimeProvider timeProvider;

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

    public boolean isCurrent() {
        return toDate.isAfter(timeProvider.today());
    }

    public void terminate() {
        toDate = timeProvider.today();
    }

    public boolean startsToday() {
        return id.startsToday();
    }

    public void change(Integer newSalary) {
        this.salary = newSalary;
    }

    public int getValue() {
        return salary;
    }

    public LocalDate getFromDate() {
        return id.fromDate;
    }

    @Embeddable
    public static class SalaryId implements Serializable {
        @Column(name = "emp_no")
        private Integer empNo;
        @Transient
        private TimeProvider timeProvider;
        @Column(name = "from_Date")
        private LocalDate fromDate;

        public SalaryId() {
        }

        public SalaryId(Integer empNo, TimeProvider timeProvider) {
            this.empNo = empNo;
            this.timeProvider = timeProvider;
            this.fromDate = timeProvider.today();
        }

        public boolean startsToday() {
            return fromDate.isEqual(timeProvider.today());
        }

    }

    public Salary() {
    }

    public Salary(Integer empNo, Integer salary, TimeProvider timeProvider) {
        id = new SalaryId(empNo, timeProvider);
        this.salary = salary;
        this.timeProvider = timeProvider;
        toDate = Constants.MAX_DATE;
    }



}
