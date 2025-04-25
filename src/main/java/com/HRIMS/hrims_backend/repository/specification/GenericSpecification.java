package com.HRIMS.hrims_backend.repository.specification;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericSpecification {
    public static <T> Predicate andLikeIfNotBlank(Predicate base, CriteriaBuilder cb, Root<T> root,
                                                            String field, String value) {
        if (StringUtils.isNotBlank(value)) {
            return cb.and(base, cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
        }
        return base;
    }
}
