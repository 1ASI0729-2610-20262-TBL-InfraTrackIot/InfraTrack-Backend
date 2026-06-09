package com.techtitans.infratrack.platform.iam.application.internal.commandservices;

import com.techtitans.infratrack.platform.iam.application.commandservices.UserCommandService;
import com.techtitans.infratrack.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.techtitans.infratrack.platform.iam.domain.model.aggregates.User;
import com.techtitans.infratrack.platform.iam.domain.model.commands.SignUpCommand;
import com.techtitans.infratrack.platform.iam.domain.repositories.RoleRepository;
import com.techtitans.infratrack.platform.iam.domain.repositories.UserRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * User command service implementation.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.roleRepository = roleRepository;
    }

    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict("User", "Username already exists"));
        }
        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName()))
                .toList();

        if (roles.stream().anyMatch(java.util.Optional::isEmpty)) {
            return Result.failure(ApplicationError.notFound("Role", "one or more role names"));
        }

        var resolvedRoles = roles.stream()
                .map(java.util.Optional::get)
                .toList();

        var user = new User(command.username(), hashingService.encode(command.password()), resolvedRoles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username())
                .<Result<User, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.unexpected("sign-up", "Created user could not be reloaded")));
    }
}
