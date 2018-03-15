package pl.com.bottega.hrs.model.events;

import java.time.LocalDateTime;

public class SalaryChangedEvent {

    private Integer empNo;
    private Integer newSalary;
    private LocalDateTime dateTime = LocalDateTime.now();

    public SalaryChangedEvent(Integer empNo, Integer newSalary) {
        this.empNo = empNo;
        this.newSalary = newSalary;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public Integer getNewSalary() {
        return newSalary;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
