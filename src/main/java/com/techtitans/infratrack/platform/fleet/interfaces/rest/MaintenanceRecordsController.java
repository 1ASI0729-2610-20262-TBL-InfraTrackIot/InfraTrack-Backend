package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.queryservices.MaintenanceRecordQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMaintenanceRecordsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MaintenanceRecordResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/maintenance-records", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Maintenance Records", description = "Global maintenance record collection (create via machinery nested route)")
public class MaintenanceRecordsController {

    private final MaintenanceRecordQueryService maintenanceRecordQueryService;

    public MaintenanceRecordsController(MaintenanceRecordQueryService maintenanceRecordQueryService) {
        this.maintenanceRecordQueryService = maintenanceRecordQueryService;
    }

    @GetMapping
    @Operation(
            summary = "List all maintenance records",
            description = "Returns every maintenance record across the fleet. "
                    + "To create a record use `POST /api/v1/machinery/{machineryId}/maintenance-records`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Records retrieved",
            content = @Content(schema = @Schema(implementation = MaintenanceRecordResource.class)))
    public ResponseEntity<List<MaintenanceRecordResource>> getAllMaintenanceRecords() {
        var items = maintenanceRecordQueryService.handle(new GetAllMaintenanceRecordsQuery()).stream()
                .map(MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{maintenanceRecordId}")
    @Operation(
            summary = "Get maintenance record by ID",
            description = "Example: `GET /api/v1/maintenance-records/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Record found",
                    content = @Content(schema = @Schema(implementation = MaintenanceRecordResource.class))),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    public ResponseEntity<MaintenanceRecordResource> getMaintenanceRecordById(
            @Parameter(example = "1") @PathVariable Long maintenanceRecordId) {
        return maintenanceRecordQueryService.handle(new GetMaintenanceRecordByIdQuery(maintenanceRecordId))
                .map(MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
