package com.techtitans.infratrack.platform.iam.interfaces.acl;

import com.techtitans.infratrack.platform.iam.application.commandservices.UserCommandService;
import com.techtitans.infratrack.platform.iam.domain.model.commands.SignUpCommand;
import com.techtitans.infratrack.platform.iam.domain.model.entities.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * ACL facade — v0.2.0 slice (sign-up only).
 */
public class IamContextFacade {
    private final UserCommandService userCommandService;

    public IamContextFacade(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    public Long createUser(String username, String password) {
        var signUpCommand = new SignUpCommand(username, password, List.of(Role.getDefaultRole()));
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof com.techtitans.infratrack.platform.shared.application.result.Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }

    public Long createUser(String username, String password, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(username, password, roles);
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof com.techtitans.infratrack.platform.shared.application.result.Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }
}
