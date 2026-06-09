package com.techtitans.infratrack.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource returned after successful authentication.
 *
 * <p>Contains the authenticated user identifier, username, and the bearer token to be used in
 * subsequent API calls.</p>
 */
@Schema(
    name = "AuthenticatedUserResponse",
    description = "Authenticated user information with JWT token",
    example = "{\"id\": 1, \"username\": \"owner.demo\", \"token\": \"eyJhbGciOiJIUzI1NiIs...\", \"role\": \"owner\"}"
)
public record AuthenticatedUserResource(
    @Schema(description = "User unique identifier", example = "1")
    Long id,

    @Schema(description = "User username", example = "owner.demo")
    String username,

    @Schema(description = "JWT Bearer token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token,

    @Schema(description = "Primary application role for the Angular client", example = "owner")
    String role
) {
}
