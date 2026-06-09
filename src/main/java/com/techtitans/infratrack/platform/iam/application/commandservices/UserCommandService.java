package com.techtitans.infratrack.platform.iam.application.commandservices;

import com.techtitans.infratrack.platform.iam.domain.model.aggregates.User;
import com.techtitans.infratrack.platform.iam.domain.model.commands.SignUpCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

/**
 * Application service contract for IAM user commands.
 */
public interface UserCommandService {

    /**
     * Handles user sign-up.
     *
     * @param command sign-up command
     * @return created user aggregate, or an application error
     */
    Result<User, ApplicationError> handle(SignUpCommand command);
}
