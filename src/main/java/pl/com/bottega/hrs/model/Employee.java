package pl.com.bottega.hrs.model;

import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.bottega.hrs.infrastructure.StandardTimeProvider;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Transient
    @Autowired
    private TimeProvider timeProvider;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('M', 'F')")
    private Gender gender = Gender.M;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<Salary> salaries = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id.employee")
    private Collection<Title> titles = new LinkedList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<DepartmentAssignment> departmentAssignments = new LinkedList<>();

    @Version
    private int version;

    Employee() {
    }

    public Employee(Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, TimeProvider timeProvider) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.timeProvider = timeProvider;
        this.hireDate = timeProvider.today();
        this.address = address;
    }

    public void updateProfile(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void updateProfile(String firstName, String lastName, LocalDate birthDate, Address address, Gender gender) {
        updateProfile(firstName, lastName, birthDate);
        this.address = address;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<Salary> getSalaries() {
        return salaries;
    }

    public void changeSalary(Integer newSalary) {
        Optional<Salary> current = getCurrentSalary();
        if (current.isPresent()) {
            Salary currentSalary = current.get();
            if (currentSalary.startsToday())
                currentSalary.update(newSalary);
            else {
                currentSalary.terminate();
                addNewSalary(newSalary);
            }
        } else
            addNewSalary(newSalary);
    }

    private void addNewSalary(Integer newSalary) {
        salaries.add(new Salary(empNo, newSalary, timeProvider));
    }

    public void assignDepartment(Department department) {
        if (!isCurrentlyAssignedTo(department))
            departmentAssignments.add(new DepartmentAssignment(empNo, department, timeProvider));
    }

    private boolean isCurrentlyAssignedTo(Department department) {
        return getCurrentDepartments().contains(department);
    }

    public void unassignDepartment(Department department) {
        departmentAssignments.stream().
                filter((assignment) -> assignment.isAssigned(department)).
                findFirst().
                ifPresent(DepartmentAssignment::unassign);
    }

    public Collection<Department> getCurrentDepartments() {
        return departmentAssignments.stream().
                filter(DepartmentAssignment::isCurrent).
                map(DepartmentAssignment::getDepartment).
                collect(Collectors.toList());
    }

    public Optional<Salary> getCurrentSalary() {
        /*for(Salary salary : salaries) {
            if(salary.getToDate().isAfter(timeProvider.today()))
                return Optional.of(salary);
        }
        return Optional.empty();*/

        return salaries.stream().
                filter(Salary::isCurrent).
                findFirst();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", hireDate=" + hireDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", salaries= " + salaries.size() +
                '}';
    }

    public Collection<DepartmentAssignment> getDepartmentsHistory() {
        return departmentAssignments;
    }

    public Optional<Title> getCurrentTitle() {
        return titles.stream().filter(Title::isCurrent).findFirst();
    }

    public void changeTitle(String titleName) {
        getCurrentTitle().ifPresent((t) -> {
            if (t.startsToday())
                titles.remove(t);
            else
                t.terminate();
        });
        titles.add(new Title(this, titleName, timeProvider));
    }

    public Collection<Title> getTitleHistory() {
        return titles;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void fire() {
        terminateDepartmentAssignments();
        terminateTitle();
        terminateSalary();
    }

    private void terminateSalary() {
        salaries.stream().filter(Salary::isCurrent).forEach(Salary::terminate);
    }

    private void terminateTitle() {
        titles.stream().filter(Title::isCurrent).forEach(Title::terminate);
    }

    private void terminateDepartmentAssignments() {
        departmentAssignments.stream().
                filter(DepartmentAssignment::isCurrent).
                map(DepartmentAssignment::getDepartment).
                forEach(this::unassignDepartment);
    }

}
