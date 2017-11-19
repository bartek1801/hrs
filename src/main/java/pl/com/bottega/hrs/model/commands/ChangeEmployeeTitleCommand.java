package pl.com.bottega.hrs.model.commands;

public class ChangeEmployeeTitleCommand implements Command  {
    private Integer empNo;
    private String title;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void validate(ValidationErrors errors){
        validatePresence(errors,"empNo", empNo);
        validatePresence(errors,"title", title);
    }
}
