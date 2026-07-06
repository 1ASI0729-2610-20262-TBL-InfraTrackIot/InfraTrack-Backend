package com.techtitans.infratrack.platform.shared.interfaces.rest.documentation;

/**
 * Reusable OpenAPI notes for Swagger UI "Try it out" flows.
 */
public final class ApiDocumentation {

    public static final String SECURITY_SCHEME = "bearerAuth";

    public static final String AUTH_STEPS =
            "**How to test:** 1) Call `POST /api/v1/authentication/sign-in` with body "
                    + "`{\"username\":\"owner@infratrack.com\",\"password\":\"Password123\"}`. "
                    + "2) Copy the `token` from the response. 3) Click **Authorize** and paste `Bearer <token>`.";

    public static final String PUBLIC_ENDPOINT =
            "**How to test:** No authentication required. Use **Try it out** and **Execute**.";

    private ApiDocumentation() {
    }
}
