package com.techtitans.infratrack.platform.monitoring.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.monitoring.application.commandservices.FleetAlertCommandService;
import com.techtitans.infratrack.platform.monitoring.application.commandservices.TelemetryReadingCommandService;
import com.techtitans.infratrack.platform.monitoring.application.queryservices.FleetAlertQueryService;
import com.techtitans.infratrack.platform.monitoring.application.queryservices.TelemetryReadingQueryService;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.AcknowledgeFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllFleetAlertsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetFleetAlertByIdQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetAllTelemetryReadingsQuery;
import com.techtitans.infratrack.platform.monitoring.domain.model.queries.GetTelemetryReadingByIdQuery;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateAlertResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.CreateTelemetryDataResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.resources.TelemetryDataResource;
import com.techtitans.infratrack.platform.monitoring.interfaces.rest.transform.MonitoringResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import com.techtitans.infratrack.platform.shared.interfaces.rest.resources.MessageResource;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Monitoring", description = "Telemetry readings and fleet alerts")
public class MonitoringController {

    private final TelemetryReadingCommandService telemetryReadingCommandService;
    private final TelemetryReadingQueryService telemetryReadingQueryService;
    private final FleetAlertCommandService fleetAlertCommandService;
    private final FleetAlertQueryService fleetAlertQueryService;
    private final MachineryQueryService machineryQueryService;

    public MonitoringController(
            TelemetryReadingCommandService telemetryReadingCommandService,
            TelemetryReadingQueryService telemetryReadingQueryService,
            FleetAlertCommandService fleetAlertCommandService,
            FleetAlertQueryService fleetAlertQueryService,
            MachineryQueryService machineryQueryService) {
        this.telemetryReadingCommandService = telemetryReadingCommandService;
        this.telemetryReadingQueryService = telemetryReadingQueryService;
        this.fleetAlertCommandService = fleetAlertCommandService;
        this.fleetAlertQueryService = fleetAlertQueryService;
        this.machineryQueryService = machineryQueryService;
    }

    @GetMapping("/api/v1/telemetry-data")
    @Operation(
            summary = "List telemetry readings",
            description = "Returns all IoT telemetry samples for maps and dashboards. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Telemetry retrieved",
            content = @Content(schema = @Schema(implementation = TelemetryDataResource.class)))
    public ResponseEntity<List<TelemetryDataResource>> getAllTelemetryData() {
        var items = telemetryReadingQueryService.handle(new GetAllTelemetryReadingsQuery()).stream()
                .map(MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/v1/telemetry-data/{telemetryId}")
    @Operation(
            summary = "Get telemetry reading by ID",
            description = "Example: `GET /api/v1/telemetry-data/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reading found",
                    content = @Content(schema = @Schema(implementation = TelemetryDataResource.class))),
            @ApiResponse(responseCode = "404", description = "Reading not found")
    })
    public ResponseEntity<TelemetryDataResource> getTelemetryById(
            @Parameter(example = "1") @PathVariable Long telemetryId) {
        return telemetryReadingQueryService.handle(new GetTelemetryReadingByIdQuery(telemetryId))
                .map(MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/telemetry-data")
    @Operation(
            summary = "Ingest telemetry reading",
            description = "Example body: `{\"machineryId\":1,\"latitude\":-12.05,\"longitude\":-77.04,"
                    + "\"fuelLevelPercent\":72.5,\"engineTemperatureC\":85.0,\"timestamp\":\"2026-06-30T12:00:00\"}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "201", description = "Telemetry ingested",
            content = @Content(schema = @Schema(implementation = TelemetryDataResource.class)))
    public ResponseEntity<?> createTelemetryData(@RequestBody CreateTelemetryDataResource resource) {
        var command = MonitoringResourceFromEntityAssembler.toCreateTelemetryCommandFromResource(resource);
        var result = telemetryReadingCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MonitoringResourceFromEntityAssembler::toTelemetryResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/api/v1/alerts")
    @Operation(
            summary = "List alerts",
            description = "Returns all fleet alerts for control panel and reports. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Alerts retrieved",
            content = @Content(schema = @Schema(implementation = AlertResource.class)))
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var items = fleetAlertQueryService.handle(new GetAllFleetAlertsQuery()).stream()
                .map(MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/v1/alerts/{alertId}")
    @Operation(
            summary = "Get alert by ID",
            description = "Example: `GET /api/v1/alerts/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alert found",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert not found")
    })
    public ResponseEntity<AlertResource> getAlertById(
            @Parameter(example = "1") @PathVariable Long alertId) {
        return fleetAlertQueryService.handle(new GetFleetAlertByIdQuery(alertId))
                .map(MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/v1/machinery/{machineryId}/alerts")
    @Operation(
            summary = "Create alert for machinery",
            description = "REST nested create: `/machinery/{machineryId}/alerts`. "
                    + "Example: `POST /api/v1/machinery/1/alerts` with body "
                    + "`{\"type\":\"fuel_theft\",\"severity\":\"critical\",\"description\":\"Sudden fuel drop\","
                    + "\"isAcknowledged\":false,\"timestamp\":\"2026-06-30T14:00:00\"}`. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alert created",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Machinery not found")
    })
    public ResponseEntity<?> createAlertForMachinery(
            @Parameter(example = "1") @PathVariable Long machineryId,
            @RequestBody CreateAlertResource resource) {
        if (machineryQueryService.handle(new GetMachineryByIdQuery(machineryId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var command = MonitoringResourceFromEntityAssembler.toCreateAlertCommandFromResource(machineryId, resource);
        var result = fleetAlertCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MonitoringResourceFromEntityAssembler::toAlertResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/api/v1/alerts/{alertId}/acknowledgements")
    @Operation(
            summary = "Acknowledge alert",
            description = "REST sub-resource action. Example: `POST /api/v1/alerts/1/acknowledgements` with empty body. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "Alert acknowledged",
            content = @Content(schema = @Schema(implementation = MessageResource.class)))
    public ResponseEntity<?> acknowledgeAlert(
            @Parameter(example = "1") @PathVariable Long alertId) {
        var result = fleetAlertCommandService.handle(new AcknowledgeFleetAlertCommand(alertId))
                .map(alert -> new MessageResource("Alert acknowledged successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                message -> message,
                HttpStatus.OK
        );
    }
}
