package com.techtitans.infratrack.platform.iam.interfaces.acl;

import java.util.List;

/**
 * Anti-corruption facade for other bounded contexts that need IAM capabilities.
 */
public interface IamContextFacade {

    /**
     * Creates a new user assigning the default role.
     *
     * @param username username to register
     * @param password raw password
     * @return created user identifier, or {@code 0L} when creation fails
     */
    Long createUser(String username, String password);

    /**
     * Creates a new user with explicit role names.
     *
     * @param username username to register
     * @param password raw password
     * @param roleNames role names to assign; unknown names are ignored
     * @return created user identifier, or {@code 0L} when creation fails
     */
    Long createUser(String username, String password, List<String> roleNames);

    /**
     * Fetches the identifier for a username.
     *
     * @param username username to search
     * @return user identifier, or {@code 0L} when user is not found
     */
    Long fetchUserIdByUsername(String username);

    /**
     * Fetches the username for a user identifier.
     *
     * @param userId user identifier
     * @return username, or an empty string when user is not found
     */
    String fetchUsernameByUserId(Long userId);
}
