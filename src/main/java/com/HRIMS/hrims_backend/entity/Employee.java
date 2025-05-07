package com.HRIMS.hrims_backend.entity;

import com.HRIMS.hrims_backend.common.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Employee extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String userName;
    @Column(nullable = false)
    private String password;
    private String role;
    private double salary;
    private String mobileNumber;

    @Column(unique = true, length = 100, nullable = false)
    private String email;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate dateOfJoining;

    @Column(name = "department_id")
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Leave> leaves = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Payroll> payrolls = new ArrayList<>();

    // Automatically set full name before saving
    @PrePersist
    @PreUpdate
    public void setFullName() {
        this.fullName = String.join(" ",
                firstName != null ? firstName : "",
                middleName != null && !middleName.isBlank() ? middleName : "",
                lastName != null ? lastName : ""
        ).trim().replaceAll(" +", " "); // remove extra spaces
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
