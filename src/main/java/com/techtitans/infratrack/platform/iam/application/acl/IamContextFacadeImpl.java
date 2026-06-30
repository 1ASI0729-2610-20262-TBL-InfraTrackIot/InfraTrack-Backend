package com.techtitans.infratrack.platform.iam.application.acl;

import com.techtitans.infratrack.platform.iam.application.commandservices.UserCommandService;
import com.techtitans.infratrack.platform.iam.application.queryservices.UserQueryService;
import com.techtitans.infratrack.platform.iam.domain.model.commands.SignUpCommand;
import com.techtitans.infratrack.platform.iam.domain.model.entities.Role;
import com.techtitans.infratrack.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.techtitans.infratrack.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import com.techtitans.infratrack.platform.iam.interfaces.acl.IamContextFacade;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Override
    public Long createUser(String username, String password) {
        var signUpCommand = new SignUpCommand(username, password, List.of(Role.getDefaultRole()));
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }

    @Override
    public Long createUser(String username, String password, List<String> roleNames) {
        var roles = roleNames != null ? roleNames.stream().map(Role::toRoleFromName).toList() : new ArrayList<Role>();
        var signUpCommand = new SignUpCommand(username, password, roles);
        var result = userCommandService.handle(signUpCommand);
        if (result instanceof Result.Success(var user)) {
            return user.getId();
        }
        return 0L;
    }

    @Override
    public Long fetchUserIdByUsername(String username) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var result = userQueryService.handle(getUserByUsernameQuery);
        if (result.isEmpty()) {
            return 0L;
        }
        return result.get().getId();
    }

    @Override
    public String fetchUsernameByUserId(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) {
            return Strings.EMPTY;
        }
        return result.get().getUsername();
    }
}
