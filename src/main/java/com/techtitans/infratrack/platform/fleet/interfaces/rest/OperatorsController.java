package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.FleetOperatorCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.FleetOperatorQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllFleetOperatorsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetFleetOperatorByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateOperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.OperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.OperatorResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
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
@RequestMapping(value = "/api/v1/operators", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Operators", description = "Fleet operator (driver) management")
public class OperatorsController {

    private final FleetOperatorCommandService fleetOperatorCommandService;
    private final FleetOperatorQueryService fleetOperatorQueryService;

    public OperatorsController(
            FleetOperatorCommandService fleetOperatorCommandService,
            FleetOperatorQueryService fleetOperatorQueryService) {
        this.fleetOperatorCommandService = fleetOperatorCommandService;
        this.fleetOperatorQueryService = fleetOperatorQueryService;
    }

    @GetMapping
    @Operation(
            summary = "List operators",
            description = "Returns all fleet drivers/operators. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Operators retrieved",
            content = @Content(schema = @Schema(implementation = OperatorResource.class)))
    public ResponseEntity<List<OperatorResource>> getAllOperators() {
        var items = fleetOperatorQueryService.handle(new GetAllFleetOperatorsQuery()).stream()
                .map(OperatorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{operatorId}")
    @Operation(
            summary = "Get operator by ID",
            description = "Example: `GET /api/v1/operators/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operator found",
                    content = @Content(schema = @Schema(implementation = OperatorResource.class))),
            @ApiResponse(responseCode = "404", description = "Operator not found")
    })
    public ResponseEntity<OperatorResource> getOperatorById(
            @Parameter(example = "1") @PathVariable Long operatorId) {
        return fleetOperatorQueryService.handle(new GetFleetOperatorByIdQuery(operatorId))
                .map(OperatorResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Create operator",
            description = "Example body: `{\"userId\":2,\"fullName\":\"Juan Pérez\",\"email\":\"juan@obra.com\","
                    + "\"phone\":\"999888777\",\"licenseNumber\":\"LIC-2\",\"status\":\"active\"}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "201", description = "Operator created",
            content = @Content(schema = @Schema(implementation = OperatorResource.class)))
    public ResponseEntity<?> createOperator(@RequestBody CreateOperatorResource resource) {
        var command = OperatorResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = fleetOperatorCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OperatorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
