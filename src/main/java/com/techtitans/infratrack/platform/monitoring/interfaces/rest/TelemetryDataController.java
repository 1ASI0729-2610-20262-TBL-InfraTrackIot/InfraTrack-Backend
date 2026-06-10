package com.techtitans.infratrack.platform.monitoring.interfaces.rest;

import com.techtitans.infratrack.platform.monitoring.application.commandservices.TelemetryReadingCommandService;
import com.techtitans.infratrack.platform.monitoring.application.queryservices.TelemetryReadingQueryService;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllTelemetryReadingsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetTelemetryReadingByIdQuery;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateTelemetryDataResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.TelemetryDataResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.transform.MonitoringResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/telemetryData", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Telemetry Data", description = "IoT telemetry readings for GPS map and dashboards")
public class TelemetryDataController {

    private final TelemetryReadingCommandService telemetryReadingCommandService;
    private final TelemetryReadingQueryService telemetryReadingQueryService;

    public TelemetryDataController(
            TelemetryReadingCommandService telemetryReadingCommandService,
            TelemetryReadingQueryService telemetryReadingQueryService) {
        this.telemetryReadingCommandService = telemetryReadingCommandService;
        this.telemetryReadingQueryService = telemetryReadingQueryService;
    }

    @GetMapping
    public ResponseEntity<List<TelemetryDataResource>> getAllTelemetryData() {
        var items = telemetryReadingQueryService.handle(new GetAllTelemetryReadingsQuery()).stream()
                .map(MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{telemetryId}")
    public ResponseEntity<TelemetryDataResource> getTelemetryById(@PathVariable Long telemetryId) {
        return telemetryReadingQueryService.handle(new GetTelemetryReadingByIdQuery(telemetryId))
                .map(MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTelemetryData(@RequestBody CreateTelemetryDataResource resource) {
        var command = MonitoringResourceFromEntityAssembler.toCreateTelemetryCommandFromResource(resource);
        var result = telemetryReadingCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
