package pl.com.bottega.hrs.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class HrsConfiguration {

    @Value("${hrs.departmentRepository}")
    private String departmentRepositoryStrategy;

    @Bean
    public DepartmentRepository departmentRepository(EntityManager entityManager) {
        if (departmentRepositoryStrategy.equals("csv"))
            return new CSVDepartmentRepository();
        else if (departmentRepositoryStrategy.equals("jpa"))
            return new JPADepartmentRepository(entityManager);
        else
            throw new IllegalArgumentException("I don't know ;(");
    }


    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));
        return builder;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("HRS-Async");
        executor.initialize();
        return executor;
    }

}
