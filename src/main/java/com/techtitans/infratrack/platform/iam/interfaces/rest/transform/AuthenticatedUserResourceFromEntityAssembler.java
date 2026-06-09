package com.techtitans.infratrack.platform.iam.interfaces.rest.transform;

import com.techtitans.infratrack.platform.iam.domain.model.aggregates.User;
import com.techtitans.infratrack.platform.iam.domain.model.valueobjects.Roles;
import com.techtitans.infratrack.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler that translates IAM authentication results into {@link AuthenticatedUserResource}.
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    /**
     * Creates a resource from the authenticated {@link User} aggregate and issued bearer token.
     *
     * @param user authenticated user aggregate
     * @param token generated bearer token
     * @return resource used by the authentication endpoint response
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getId(),
                user.getUsername(),
                token,
                toClientRole(user));
    }

    private static String toClientRole(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return "admin";
        }
        var roleName = user.getRoles().iterator().next().getName();
        return switch (roleName) {
            case ROLE_OWNER -> "owner";
            case ROLE_ADMIN -> "admin";
            case ROLE_TECHNICIAN -> "technician";
        };
    }
}
