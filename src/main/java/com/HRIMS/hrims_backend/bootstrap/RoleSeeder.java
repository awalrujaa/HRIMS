package com.HRIMS.hrims_backend.bootstrap;

import com.HRIMS.hrims_backend.entity.Role;
import com.HRIMS.hrims_backend.enums.RoleType;
import com.HRIMS.hrims_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();

    }

    private void loadRoles() {
        RoleType[] roleNames = new RoleType[] { RoleType.USER, RoleType.ADMIN, RoleType.SUPER_ADMIN };
        Map<RoleType, String> roleDescriptionMap = Map.of(
                RoleType.USER, "Default user role",
                RoleType.ADMIN, "Administrator role",
                RoleType.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();

                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
