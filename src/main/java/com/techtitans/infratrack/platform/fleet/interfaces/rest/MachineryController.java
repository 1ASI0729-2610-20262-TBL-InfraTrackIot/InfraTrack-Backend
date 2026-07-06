package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.MachineryCommandService;
import com.techtitans.infratrack.platform.fleet.application.commandservices.MaintenanceRecordCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.MaintenanceRecordQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMachineryQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordsByMachineryIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.UpdateMachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MachineryResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MaintenanceRecordResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/machinery", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Machinery", description = "Fleet machinery (transports) and nested maintenance records")
public class MachineryController {

    private final MachineryCommandService machineryCommandService;
    private final MachineryQueryService machineryQueryService;
    private final MaintenanceRecordCommandService maintenanceRecordCommandService;
    private final MaintenanceRecordQueryService maintenanceRecordQueryService;

    public MachineryController(
            MachineryCommandService machineryCommandService,
            MachineryQueryService machineryQueryService,
            MaintenanceRecordCommandService maintenanceRecordCommandService,
            MaintenanceRecordQueryService maintenanceRecordQueryService) {
        this.machineryCommandService = machineryCommandService;
        this.machineryQueryService = machineryQueryService;
        this.maintenanceRecordCommandService = maintenanceRecordCommandService;
        this.maintenanceRecordQueryService = maintenanceRecordQueryService;
    }

    @GetMapping
    @Operation(
            summary = "List machinery",
            description = "Returns all fleet transports/machinery. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Machinery list retrieved",
            content = @Content(schema = @Schema(implementation = MachineryResource.class)))
    public ResponseEntity<List<MachineryResource>> getAllMachinery() {
        var items = machineryQueryService.handle(new GetAllMachineryQuery()).stream()
                .map(MachineryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{machineryId}")
    @Operation(
            summary = "Get machinery by ID",
            description = "Example: `GET /api/v1/machinery/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Machinery found",
                    content = @Content(schema = @Schema(implementation = MachineryResource.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found")
    })
    public ResponseEntity<MachineryResource> getMachineryById(
            @Parameter(example = "1") @PathVariable Long machineryId) {
        return machineryQueryService.handle(new GetMachineryByIdQuery(machineryId))
                .map(MachineryResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Create machinery",
            description = "Example body: `{\"plateNumber\":\"ABC-123\",\"brand\":\"Caterpillar\","
                    + "\"model\":\"320D\",\"currentStatus\":\"active\"}`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "201", description = "Machinery created",
            content = @Content(schema = @Schema(implementation = MachineryResource.class)))
    public ResponseEntity<?> createMachinery(@RequestBody CreateMachineryResource resource) {
        var command = MachineryResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = machineryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MachineryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{machineryId}")
    @Operation(
            summary = "Update machinery",
            description = "Example: `PUT /api/v1/machinery/1` with partial fields in body. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Machinery updated",
            content = @Content(schema = @Schema(implementation = MachineryResource.class)))
    public ResponseEntity<?> updateMachinery(
            @Parameter(example = "1") @PathVariable Long machineryId,
            @RequestBody UpdateMachineryResource resource) {
        var command = MachineryResourceFromEntityAssembler.toUpdateCommandFromResource(machineryId, resource);
        var result = machineryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MachineryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/{machineryId}/maintenance-records")
    @Operation(
            summary = "List maintenance records for machinery",
            description = "REST nested collection: `/machinery/{machineryId}/maintenance-records`. "
                    + "Example: `GET /api/v1/machinery/1/maintenance-records`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Records retrieved",
                    content = @Content(schema = @Schema(implementation = MaintenanceRecordResource.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found")
    })
    public ResponseEntity<List<MaintenanceRecordResource>> getMaintenanceRecordsForMachinery(
            @Parameter(example = "1") @PathVariable Long machineryId) {
        if (machineryQueryService.handle(new GetMachineryByIdQuery(machineryId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var items = maintenanceRecordQueryService.handle(new GetMaintenanceRecordsByMachineryIdQuery(machineryId))
                .stream()
                .map(MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{machineryId}/maintenance-records")
    @Operation(
            summary = "Create maintenance record for machinery",
            description = "REST nested create: `/machinery/{machineryId}/maintenance-records`. "
                    + "Example: `POST /api/v1/machinery/1/maintenance-records` with body "
                    + "`{\"serviceType\":\"preventive\",\"description\":\"Oil change\","
                    + "\"costPen\":450.0,\"engineHoursAtService\":1200,"
                    + "\"serviceDate\":\"2026-06-01\",\"nextServiceDate\":\"2026-09-01\"}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance record created",
                    content = @Content(schema = @Schema(implementation = MaintenanceRecordResource.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found")
    })
    public ResponseEntity<?> createMaintenanceRecordForMachinery(
            @Parameter(example = "1") @PathVariable Long machineryId,
            @RequestBody CreateMaintenanceRecordResource resource) {
        if (machineryQueryService.handle(new GetMachineryByIdQuery(machineryId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var command = MaintenanceRecordResourceFromEntityAssembler.toCreateCommandFromResource(machineryId, resource);
        var result = maintenanceRecordCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
