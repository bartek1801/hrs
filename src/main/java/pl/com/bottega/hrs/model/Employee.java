package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "employees")
public class Employee {

    public static final LocalDate MAX_DATE = LocalDate.parse("9999-01-01");

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Transient
    private TimeProvider timeProvider;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('M', 'F')")
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<Salary> salaries = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<Title> titles = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<DepartmentAssignment> departmentAssignments = new LinkedList<>();


    public Employee() {
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

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<Salary> getSalaries() {
        return salaries;
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
                ", address=" + address +
                ", salaries=" + salaries.size() +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void addSalary(Salary salary) {
        salaries.add(salary);
    }

    public void addTitle(Title title) {
        titles.add(title);
    }

    public void addDepartmentAssignment(DepartmentAssignment departmentAssignment) {
        departmentAssignments.add(departmentAssignment);
    }

    public void changeSalary(Integer newSalary) {
        Optional<Salary> optionalSalary = getCurrentSalary();
        if (optionalSalary.isPresent()){
            Salary currentSalary = optionalSalary.get();
            removeOrTerminateSalary(newSalary, currentSalary);
        }
        else {
            addNewSalary(newSalary);
        }
    }

    private void addNewSalary(Integer newSalary) {
        salaries.add(new Salary(empNo, newSalary, timeProvider));
    }

    private void removeOrTerminateSalary(Integer newSalary, Salary currentSalary) {
        if (currentSalary.startsToday()){
            currentSalary.change(newSalary);
        }
        else {
            currentSalary.terminate();
            addNewSalary(newSalary);
        }
    }

    public Optional<Salary> getCurrentSalary(){
        return salaries.stream()
                .filter(Salary :: isCurrent) // .filter((salary) -> salary.isCurrent())
                .findFirst();
    }


    public void changeTitle(String newTitle) {
        Optional<Title> optionalTitle = getCurrentTitle();
        if (optionalTitle.isPresent()){
            Title currentTitle = optionalTitle.get();
            removeOrTerminateTitle(newTitle, currentTitle);
        }
        else {
            addNewTitle(newTitle);
        }
    }

    private void addNewTitle(String newTitle) {
        titles.add(new Title(empNo, newTitle, timeProvider));
    }

    private void removeOrTerminateTitle(String newTitle, Title currentTitle) {
        if (currentTitle.startsToday()){
            currentTitle.change(newTitle);
        }
        else {
            currentTitle.terminate();
            addNewTitle(newTitle);
        }
    }

    public Optional<Title> getCurrentTitle() {
        return titles.stream().filter(title -> title.isCurrent()).findFirst();
    }

    public void assignDepartment(Department department) {
        if (!isCurrentlyAssignedTo(department))
            departmentAssignments.add(new DepartmentAssignment(empNo, department, timeProvider));
    }

    private boolean isCurrentlyAssignedTo(Department department) {
        return getCurrentDepartments().contains(department);
    }

    public void unAssignDepartment(Department department) {
        departmentAssignments.stream().
                filter((assignment) -> assignment.isAssigned(department)).
                findFirst().
                ifPresent(DepartmentAssignment :: unassign);
                //ifPresent((assignment) -> assignment.unassign());
    }



    public Collection<Department> getCurrentDepartments() {
       return departmentAssignments.stream().
               filter(DepartmentAssignment :: isCurrent).
               map( DepartmentAssignment :: getDepartment).collect(Collectors.toList());
//                filter((assignment) -> assignment.isCurrent()).
//                map( (assignment) -> assignment.getDepartment()).collect(Collectors.toList());
    }


    public Collection<DepartmentAssignment> getDepartmentsHistory() {
        return departmentAssignments;
    }

    public Collection<Title> getTitles() {
        return titles;
    }
}
