package com.HRIMS.hrims_backend.repository.specification;

import com.HRIMS.hrims_backend.entity.Department;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import static com.HRIMS.hrims_backend.repository.specification.GenericSpecification.andLikeIfNotBlank;


public class DepartmentSpecification {
    public static Specification<Department> filterDepartment(String searchText, String name, String code, String createdBy, String updatedBy) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "name", searchText);
            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "code", code);
            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "createdBy", createdBy);
            predicate = andLikeIfNotBlank(predicate, criteriaBuilder, root, "updatedBy", updatedBy);
            return predicate;
        };
    }

}
