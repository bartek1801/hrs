package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void changeSalary(Integer newSalary) {
        Optional<Salary> optionalSalary = getCurrentSalary();
        if (optionalSalary.isPresent()){
            Salary currentSalary = optionalSalary.get();
            removeOrTerminateSalary(newSalary, currentSalary);
        }
        else {
            addNewSalary(newSalary);
        }
//
//        Salary newSalary = new Salary(new Salary.SalaryId(empNo, LocalDate.now()), salary, MAX_DATE);
//        if (getCurrentSalary() != null) {
//            salaries.stream().forEach(tmpSalary -> {
//                if (tmpSalary.getToDate().equals(MAX_DATE))
//                    tmpSalary.setToDate(LocalDate.now());
//            });
//        }
//        salaries.add(newSalary);
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
                .filter((salary) -> salary.isCurrent())
                .findFirst();
    }

//    public Optional<Integer> getCurrentSalary() {
//        for (Salary salary : salaries) {
//            if (salary.getToDate().equals(MAX_DATE))
//                return Optional.of(salary.getSalary());
//        }
//        return Optional.empty();
//    }

    public void changeTitle(String title) {
        Title newTitle = new Title(new Title.TitleId(empNo, title, timeProvider.today()), MAX_DATE);
        if (getCurrentTitle() != null) {
            titles.stream().forEach(tmpTitle -> {
                if (tmpTitle.getToDate().equals(MAX_DATE))
                    tmpTitle.setToDate(timeProvider.today());
            });
        }
        titles.add(newTitle);
    }

    public String getCurrentTitle() {
        for (Title title : titles) {
            if (title.getToDate().equals(MAX_DATE))
                return title.getId().getTitle();
        }
        return null;
    }

    public void changeDepartment(String departmentNumber) {

    }

    public String getCurrentDepartmentNumber() {
        for (DepartmentAssignment departmentAssignment : departmentAssignments) {
            if (departmentAssignment.getToDate().equals(MAX_DATE)) {
                return departmentAssignment.getId().getDepartmentNumber();
            }
        }
        return null;
    }

    public Department getCurrentDepartment(EntityManager em) {
        String departmentNumber = getCurrentDepartmentNumber();
        Query query = em.createQuery("SELECT d FROM Department d WHERE d.departmentNumber = :departmentNumber")
                .setParameter("departmentNumber", departmentNumber);
        return (Department) query.getSingleResult();
    }


    public void addDepartmentAssignment(DepartmentAssignment departmentAssignment) {
        departmentAssignments.add(departmentAssignment);
    }
}
