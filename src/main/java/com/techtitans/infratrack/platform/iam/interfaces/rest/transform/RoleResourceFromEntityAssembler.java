package com.techtitans.infratrack.platform.iam.interfaces.rest.transform;

import com.techtitans.infratrack.platform.iam.domain.model.entities.Role;
import com.techtitans.infratrack.platform.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler that converts IAM {@link Role} entities into REST {@link RoleResource} objects.
 */
public class RoleResourceFromEntityAssembler {
    /**
     * Converts a role entity to its REST representation.
     *
     * @param role role domain entity
     * @return role resource
     */
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
