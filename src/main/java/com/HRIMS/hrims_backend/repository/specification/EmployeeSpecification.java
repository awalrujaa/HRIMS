package com.HRIMS.hrims_backend.repository.specification;

import com.HRIMS.hrims_backend.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import static com.HRIMS.hrims_backend.repository.specification.GenericSpecification.andLikeIfNotBlank;

public class EmployeeSpecification {
    public static Specification<Employee> filterEmployee(String email, String middleName) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "email", email);
            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "middle_name", middleName);
//            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "last_name", lastName);

            return predicate;
        };
    }
}
