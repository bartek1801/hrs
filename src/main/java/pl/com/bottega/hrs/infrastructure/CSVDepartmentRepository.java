package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;

//@Component
public class CSVDepartmentRepository implements DepartmentRepository {
    @Override
    public Department get(String number) {
        return null;
    }

    @Override
    public void save(Department department) {

    }
}
