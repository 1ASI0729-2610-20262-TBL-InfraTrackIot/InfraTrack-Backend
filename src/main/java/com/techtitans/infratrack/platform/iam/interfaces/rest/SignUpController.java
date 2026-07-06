package com.techtitans.infratrack.platform.iam.interfaces.rest;

import com.techtitans.infratrack.platform.iam.application.commandservices.UserCommandService;
import com.techtitans.infratrack.platform.iam.interfaces.rest.resources.SignUpResource;
import com.techtitans.infratrack.platform.iam.interfaces.rest.resources.UserResource;
import com.techtitans.infratrack.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.techtitans.infratrack.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication and user registration endpoints")
public class SignUpController {

    private final UserCommandService userCommandService;

    public SignUpController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-up")
    @Operation(
            summary = "User registration",
            description = "Creates a new account. Does not return a JWT — sign in afterwards. "
                    + ApiDocumentation.PUBLIC_ENDPOINT
                    + " Example body: `{\"username\":\"owner@infratrack.com\",\"password\":\"Password123\","
                    + "\"roles\":[\"ROLE_OWNER\"]}`.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or username already exists"),
            @ApiResponse(responseCode = "409", description = "Conflict - username already taken")
    })
    public ResponseEntity<?> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var result = userCommandService.handle(signUpCommand);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
