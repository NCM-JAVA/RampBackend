package com.ramp.utils;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ramp.entity.RoleEntity;
import com.ramp.entity.Users;
import com.ramp.enums.RoleType;
import com.ramp.repo.RoleRepository;
import com.ramp.repo.UserRepo;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserRepo userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if super admin already exists
        if (userRepository.findByUserName("superadmin").isPresent()) {
            System.out.println("Super Admin already exists. Skipping initialization.");
            return;
        }

        // Create or get SUPER_ADMIN role
        RoleEntity superAdminRole = roleRepository.findByRoleName(RoleType.SUPER_ADMIN);
        if (superAdminRole == null) {
            superAdminRole = new RoleEntity();
            superAdminRole.setRoleName(RoleType.SUPER_ADMIN);
            superAdminRole = roleRepository.save(superAdminRole);
            System.out.println("SUPER_ADMIN role created.");
        }

        // Create default super admin user
        Users superAdmin = new Users();
        superAdmin.setUserName("superadmin");
        superAdmin.setEmail("superadmin@cms.com");
        superAdmin.setPassword(passwordEncoder.encode("admin123"));
        superAdmin.setFullName("Super Administrator");
        superAdmin.setRole(superAdminRole);
        superAdmin.setCreatedDate(LocalDateTime.now());
        superAdmin.setActive(true);
        superAdmin.setStatus("ACTIVE");

        userRepository.save(superAdmin);

        System.out.println("========================================");
        System.out.println("DEFAULT SUPER ADMIN CREATED");
        System.out.println("========================================");
        System.out.println("Username: superadmin");
        System.out.println("Email: superadmin@cms.com");
        System.out.println("Password: admin123");
        System.out.println("========================================");
        System.out.println("Please change the password after first login!");
        System.out.println("========================================");
    }
}
