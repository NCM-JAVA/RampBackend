package com.ramp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ramp.entity.RoleEntity;
import com.ramp.entity.Users;
import com.ramp.enums.RoleType;
import com.ramp.repo.RoleRepository;
import com.ramp.repo.UserRepo;
import com.ramp.req.AdminRegistrationReq;
import com.ramp.req.CMSRegistrationReq;
import com.ramp.req.UserReq;
import com.ramp.res.LoginResponse;
import com.ramp.res.StatusResponse;
import com.ramp.res.UserResponse;
import com.ramp.utils.JwtUtil;
import com.ramp.utils.StatusCode;
import com.ramp.utils.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapperr;

    public UserServiceImpl(UserRepo userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           UserMapper userMapperr) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapperr = userMapperr;
    }

    // ================= REGISTER USER =================
    @Override
    public StatusResponse<UserResponse> registerUser(UserReq req) {

        if (userRepository.existsByUserName(req.getLoginUsername())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Username already exists");
        }

        if (userRepository.existsByEmail(req.getAuthorizedEmail())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Email already exists");
        }

        RoleEntity role = roleRepository.findByRoleName(req.getRole());
        if (role == null) {
            role = new RoleEntity();
            role.setRoleName(req.getRole());
            role = roleRepository.save(role);
        }

        Users user = userMapperr.toEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);
        user.setCreatedDate(LocalDateTime.now());
        user.setActive(true);
        user.setStatus("ACTIVE");

        Users savedUser = userRepository.save(user);

        UserResponse responseDto = userMapperr.toResponse(savedUser);

        return new StatusResponse<>(
                StatusCode.CREATED,
                responseDto,
                "User registered successfully"
        );
    }

    // ================= LOGIN (USERNAME OR EMAIL) =================
    @Override
    public StatusResponse<LoginResponse> login(
            String username,
            String password,
            JwtUtil jwtUtil) {

        Users user = userRepository.findByUserNameIgnoreCaseOrEmailIgnoreCase(username, username)
                .orElseThrow(() ->
                        new RuntimeException("Invalid username/email or password")
                );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username/email or password");
        }

        String token = jwtUtil.generateToken(
                user.getUserName(),
                user.getRole().getRoleName().name()
        );

        UserResponse userResponse = userMapperr.toResponse(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setUser(userResponse);

        return new StatusResponse<>(
                StatusCode.SUCCESS,
                loginResponse,
                "Login successful"
        );
    }

    // ================= REGISTER CMS =================
    @Override
    public StatusResponse<UserResponse> registerCMS(CMSRegistrationReq req) {

        if (userRepository.existsByUserName(req.getUsername())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Username already exists");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Email already exists");
        }

        RoleEntity role = roleRepository.findByRoleName(RoleType.CONTENT_MANAGER);
        if (role == null) {
            role = new RoleEntity();
            role.setRoleName(RoleType.CONTENT_MANAGER);
            role = roleRepository.save(role);
        }

        Users user = new Users();
        user.setUserName(req.getUsername());
        user.setEmail(req.getEmail());
        user.setMobileNumber(req.getMobileNumber());
        user.setFullName(req.getFullName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);
        user.setAcceptTerms(req.getAcceptTerms());
        user.setCreatedDate(LocalDateTime.now());
        user.setActive(true);
        user.setStatus("ACTIVE");

        Users savedUser = userRepository.save(user);

        UserResponse responseDto = new UserResponse();
        responseDto.setLoginUsername(savedUser.getUserName());
        responseDto.setAuthorizedEmail(savedUser.getEmail());
        responseDto.setAuthorizedMobile(savedUser.getMobileNumber());
        responseDto.setAuthorizedPerson(savedUser.getFullName());
        responseDto.setAcceptTerms(savedUser.getAcceptTerms());
        responseDto.setRole(savedUser.getRole().getRoleName());

        return new StatusResponse<>(
                StatusCode.CREATED,
                responseDto,
                "CMS user registered successfully"
        );
    }

    // ================= REGISTER ADMIN =================
    @Override
    public StatusResponse<UserResponse> registerAdmin(AdminRegistrationReq req) {

        if (userRepository.existsByUserName(req.getUsername())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Username already exists");
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            return new StatusResponse<>(StatusCode.CONFLICT, null, "Email already exists");
        }

        RoleEntity role = roleRepository.findByRoleName(RoleType.ADMIN);
        if (role == null) {
            role = new RoleEntity();
            role.setRoleName(RoleType.ADMIN);
            role = roleRepository.save(role);
        }

        Users user = new Users();
        user.setUserName(req.getUsername());
        user.setEmail(req.getEmail());
        user.setMobileNumber(req.getMobileNumber());
        user.setFullName(req.getFullName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);
        user.setCreatedDate(LocalDateTime.now());
        user.setActive(true);
        user.setStatus("ACTIVE");

        Users savedUser = userRepository.save(user);

        UserResponse responseDto = new UserResponse();
        responseDto.setLoginUsername(savedUser.getUserName());
        responseDto.setAuthorizedEmail(savedUser.getEmail());
        responseDto.setAuthorizedMobile(savedUser.getMobileNumber());
        responseDto.setAuthorizedPerson(savedUser.getFullName());
        responseDto.setRole(savedUser.getRole().getRoleName());

        return new StatusResponse<>(
                StatusCode.CREATED,
                responseDto,
                "Admin user registered successfully"
        );
    }

    // ================= GET USERS =================
    @Override
    public StatusResponse<List<UserResponse>> getAllUsers() {

        List<UserResponse> responseList = userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponse dto = new UserResponse();
                    dto.setLoginUsername(user.getUserName());
                    dto.setAuthorizedEmail(user.getEmail());
                    dto.setAuthorizedMobile(user.getMobileNumber());
                    dto.setAuthorizedPerson(user.getFullName());
                    dto.setRole(user.getRole() != null ? user.getRole().getRoleName() : null);
                    return dto;
                })
                .collect(Collectors.toList());

        return new StatusResponse<>(
                StatusCode.SUCCESS,
                responseList,
                "Users retrieved successfully"
        );
    }

    @Override
    public StatusResponse<List<UserResponse>> getUsersByRole(String roleName) {

        try {
            RoleType roleType = RoleType.valueOf(roleName.toUpperCase());
            RoleEntity role = roleRepository.findByRoleName(roleType);

            if (role == null) {
                return new StatusResponse<>(StatusCode.NOT_FOUND, null, "Role not found");
            }

            List<UserResponse> responseList = userRepository.findByRole(role)
                    .stream()
                    .map(user -> {
                        UserResponse dto = new UserResponse();
                        dto.setLoginUsername(user.getUserName());
                        dto.setAuthorizedEmail(user.getEmail());
                        dto.setAuthorizedMobile(user.getMobileNumber());
                        dto.setAuthorizedPerson(user.getFullName());
                        dto.setRole(user.getRole().getRoleName());
                        return dto;
                    })
                    .collect(Collectors.toList());

            return new StatusResponse<>(
                    StatusCode.SUCCESS,
                    responseList,
                    "Users retrieved successfully"
            );

        } catch (IllegalArgumentException e) {
            return new StatusResponse<>(
                    StatusCode.BAD_REQUEST,
                    null,
                    "Invalid role name"
            );
        }
    }
}
