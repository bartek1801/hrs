package pl.com.bottega.hrs.model.commands;

import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;

import java.time.LocalDate;

public class AddEmployeeCommand implements Command {

    private String firstName, lastName;

    private LocalDate birthDate;

    private Address address;

    private Gender gender;

    private Integer salary;

    private String title;

    private String deptNo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "firstName", firstName);
        validatePresence(errors, "lastName", lastName);
        validatePresence(errors, "birthDate", birthDate);
        validatePresence(errors, "address.street", address.getStreet());
        validatePresence(errors, "address.city", address.getCity());
        validatePresence(errors, "gender", gender);
        validatePresence(errors, "salary", salary);
        validatePresence(errors, "title", title);
        validatePresence(errors, "deptNo", deptNo);


        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            errors.add("birthDate", "mast be in the past");
        }
    }
}
