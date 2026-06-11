package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.MaintenanceRecordCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.MaintenanceRecordQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMaintenanceRecordsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MaintenanceRecordResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MaintenanceRecordResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/maintenanceRecords", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Maintenance Records", description = "Preventive maintenance records for machinery")
public class MaintenanceRecordsController {

    private final MaintenanceRecordCommandService maintenanceRecordCommandService;
    private final MaintenanceRecordQueryService maintenanceRecordQueryService;

    public MaintenanceRecordsController(
            MaintenanceRecordCommandService maintenanceRecordCommandService,
            MaintenanceRecordQueryService maintenanceRecordQueryService) {
        this.maintenanceRecordCommandService = maintenanceRecordCommandService;
        this.maintenanceRecordQueryService = maintenanceRecordQueryService;
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecordResource>> getAllMaintenanceRecords() {
        var items = maintenanceRecordQueryService.handle(new GetAllMaintenanceRecordsQuery()).stream()
                .map(MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{maintenanceRecordId}")
    public ResponseEntity<MaintenanceRecordResource> getMaintenanceRecordById(@PathVariable Long maintenanceRecordId) {
        return maintenanceRecordQueryService.handle(new GetMaintenanceRecordByIdQuery(maintenanceRecordId))
                .map(MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMaintenanceRecord(@RequestBody CreateMaintenanceRecordResource resource) {
        var command = MaintenanceRecordResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = maintenanceRecordCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MaintenanceRecordResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
