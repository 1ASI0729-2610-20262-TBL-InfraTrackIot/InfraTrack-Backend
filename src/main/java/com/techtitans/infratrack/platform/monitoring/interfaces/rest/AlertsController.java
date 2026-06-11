package com.techtitans.infratrack.platform.monitoring.interfaces.rest;

import com.techtitans.infratrack.platform.monitoring.application.commandservices.FleetAlertCommandService;
import com.techtitans.infratrack.platform.monitoring.application.queryservices.FleetAlertQueryService;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllFleetAlertsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetFleetAlertByIdQuery;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateAlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.transform.MonitoringResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Fleet alerts center and control panel notifications")
public class AlertsController {

private final FleetAlertCommandService fleetAlertCommandService;
    private final FleetAlertQueryService fleetAlertQueryService;

    public AlertsController(
            FleetAlertCommandService fleetAlertCommandService,
            FleetAlertQueryService fleetAlertQueryService) {
        this.fleetAlertCommandService = fleetAlertCommandService;
        this.fleetAlertQueryService = fleetAlertQueryService;
    }
    }

    @GetMapping
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var items = fleetAlertQueryService.handle(new GetAllFleetAlertsQuery()).stream()
                .map(MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long alertId) {
        return fleetAlertQueryService.handle(new GetFleetAlertByIdQuery(alertId))
                .map(MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAlert(@RequestBody CreateAlertResource resource) {
        var command = MonitoringResourceFromEntityAssembler.toCreateAlertCommandFromResource(resource);
        var result = fleetAlertCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
