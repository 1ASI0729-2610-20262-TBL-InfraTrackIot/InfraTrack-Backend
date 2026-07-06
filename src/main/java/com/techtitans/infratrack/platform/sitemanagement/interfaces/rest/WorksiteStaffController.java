package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest;

import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteStaffCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteStaffQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksiteStaffQuery;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.CreateWorksiteStaffResource;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.WorksiteStaffResource;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteStaffResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/staff", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Staff", description = "Worksite staff members (global registry)")
public class WorksiteStaffController {

    private final WorksiteStaffCommandService worksiteStaffCommandService;
    private final WorksiteStaffQueryService worksiteStaffQueryService;

    public WorksiteStaffController(
            WorksiteStaffCommandService worksiteStaffCommandService,
            WorksiteStaffQueryService worksiteStaffQueryService) {
        this.worksiteStaffCommandService = worksiteStaffCommandService;
        this.worksiteStaffQueryService = worksiteStaffQueryService;
    }

    @GetMapping
    @Operation(
            summary = "List all staff members",
            description = "Returns every staff member in the organization registry. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff list retrieved",
                    content = @Content(schema = @Schema(implementation = WorksiteStaffResource.class))),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT")
    })
    public ResponseEntity<List<WorksiteStaffResource>> getAllStaff() {
        var items = worksiteStaffQueryService.handle(new GetAllWorksiteStaffQuery()).stream()
                .map(staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PostMapping
    @Operation(
            summary = "Register a staff member",
            description = "Creates a staff member in the global registry. "
                    + "Assign them to a worksite with `PUT /api/v1/worksites/{worksiteId}/staff/{staffId}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff member created",
                    content = @Content(schema = @Schema(implementation = WorksiteStaffResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid JWT")
    })
    public ResponseEntity<?> createStaff(@RequestBody CreateWorksiteStaffResource resource) {
        var command = WorksiteStaffResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = worksiteStaffCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService),
                HttpStatus.CREATED
        );
    }
}
