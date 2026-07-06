package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest;

import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import com.techtitans.infratrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteStaffCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl.SiteManagementExternalFleetService;
import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteQueryService;
import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteStaffQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignStaffToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksitesQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetStaffForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetTransportsForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetWorksiteByIdQuery;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.*;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteStaffResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteTransportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(value = "/api/v1/worksites", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Worksites", description = "Construction sites and nested resource assignments")
public class WorksitesController {

    private final WorksiteCommandService worksiteCommandService;
    private final WorksiteStaffCommandService worksiteStaffCommandService;
    private final WorksiteQueryService worksiteQueryService;
    private final WorksiteStaffQueryService worksiteStaffQueryService;
    private final SiteManagementExternalFleetService externalFleetService;

    public WorksitesController(
            WorksiteCommandService worksiteCommandService,
            WorksiteStaffCommandService worksiteStaffCommandService,
            WorksiteQueryService worksiteQueryService,
            WorksiteStaffQueryService worksiteStaffQueryService,
            SiteManagementExternalFleetService externalFleetService) {
        this.worksiteCommandService = worksiteCommandService;
        this.worksiteStaffCommandService = worksiteStaffCommandService;
        this.worksiteQueryService = worksiteQueryService;
        this.worksiteStaffQueryService = worksiteStaffQueryService;
        this.externalFleetService = externalFleetService;
    }

    @GetMapping
    @Operation(
            summary = "List worksites",
            description = "Returns all construction sites. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Worksites retrieved",
            content = @Content(schema = @Schema(implementation = WorksiteResource.class)))
    public ResponseEntity<List<WorksiteResource>> getAllWorksites() {
        var items = worksiteQueryService.handle(new GetAllWorksitesQuery()).stream()
                .map(worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{worksiteId}")
    @Operation(
            summary = "Get worksite by ID",
            description = "Example: `GET /api/v1/worksites/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Worksite found",
                    content = @Content(schema = @Schema(implementation = WorksiteResource.class))),
            @ApiResponse(responseCode = "404", description = "Worksite not found")
    })
    public ResponseEntity<WorksiteResource> getWorksiteById(
            @Parameter(description = "Worksite identifier", example = "1") @PathVariable Long worksiteId) {
        return worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId))
                .map(worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Create worksite",
            description = "Registers a new construction site. "
                    + "Example body: `{\"name\":\"Obra Norte\",\"address\":\"Av. Principal 100\","
                    + "\"latitude\":-12.05,\"longitude\":-77.04,\"status\":\"active\"}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "201", description = "Worksite created",
            content = @Content(schema = @Schema(implementation = WorksiteResource.class)))
    public ResponseEntity<?> createWorksite(@RequestBody CreateWorksiteResource resource) {
        var command = WorksiteResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = worksiteCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{worksiteId}/transports")
    @Operation(
            summary = "List transports assigned to a worksite",
            description = "REST nested collection: `/worksites/{worksiteId}/transports`. "
                    + "Example: `GET /api/v1/worksites/1/transports`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transports retrieved",
                    content = @Content(schema = @Schema(implementation = WorksiteTransportResource.class))),
            @ApiResponse(responseCode = "404", description = "Worksite not found")
    })
    public ResponseEntity<List<WorksiteTransportResource>> getTransportsForWorksite(
            @Parameter(example = "1") @PathVariable Long worksiteId) {
        if (worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var items = worksiteQueryService.handle(new GetTransportsForWorksiteQuery(worksiteId)).stream()
                .map(assignment -> WorksiteTransportResourceFromEntityAssembler.toResourceFromEntity(assignment, externalFleetService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{worksiteId}/transports/{transportId}")
    @Operation(
            summary = "Assign transport to worksite",
            description = "REST nested assignment. Example: `PUT /api/v1/worksites/1/transports/3` with empty body. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transport assigned",
                    content = @Content(schema = @Schema(implementation = WorksiteTransportResource.class))),
            @ApiResponse(responseCode = "404", description = "Worksite or transport not found")
    })
    public ResponseEntity<?> assignTransport(
            @Parameter(example = "1") @PathVariable Long worksiteId,
            @Parameter(description = "Machinery/transport ID", example = "3") @PathVariable Long transportId) {
        var command = new AssignTransportToWorksiteCommand(worksiteId, transportId, null);
        var result = worksiteCommandService.handle(command)
                .map(assignment -> WorksiteTransportResourceFromEntityAssembler.toResourceFromEntity(assignment, externalFleetService));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, resource -> resource, HttpStatus.OK);
    }

    @GetMapping("/{worksiteId}/staff")
    @Operation(
            summary = "List staff assigned to a worksite",
            description = "REST nested collection: `/worksites/{worksiteId}/staff`. "
                    + "Example: `GET /api/v1/worksites/1/staff`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff retrieved",
                    content = @Content(schema = @Schema(implementation = WorksiteStaffResource.class))),
            @ApiResponse(responseCode = "404", description = "Worksite not found")
    })
    public ResponseEntity<List<WorksiteStaffResource>> getStaffForWorksite(
            @Parameter(example = "1") @PathVariable Long worksiteId) {
        if (worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var items = worksiteStaffQueryService.handle(new GetStaffForWorksiteQuery(worksiteId)).stream()
                .map(staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{worksiteId}/staff/{staffId}")
    @Operation(
            summary = "Assign staff member to worksite",
            description = "REST nested assignment. First create staff via `POST /api/v1/staff`, "
                    + "then assign with `PUT /api/v1/worksites/1/staff/2`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff assigned",
                    content = @Content(schema = @Schema(implementation = MessageResource.class))),
            @ApiResponse(responseCode = "404", description = "Worksite or staff not found")
    })
    public ResponseEntity<?> assignStaff(
            @Parameter(example = "1") @PathVariable Long worksiteId,
            @Parameter(example = "2") @PathVariable Long staffId) {
        var result = worksiteStaffCommandService.handle(new AssignStaffToWorksiteCommand(worksiteId, staffId))
                .map(id -> new MessageResource("Staff assigned to worksite successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.OK);
    }
}
