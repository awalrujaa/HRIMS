package com.HRIMS.hrims_backend.entity;


import com.HRIMS.hrims_backend.common.audit.Auditable;
import com.HRIMS.hrims_backend.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "roles")
@Entity
@Getter @Setter
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(nullable = false)
    private String description;
}
