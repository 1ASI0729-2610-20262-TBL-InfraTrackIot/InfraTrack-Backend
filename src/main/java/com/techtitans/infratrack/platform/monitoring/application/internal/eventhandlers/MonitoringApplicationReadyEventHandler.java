package com.techtitans.infratrack.platform.monitoring.application.internal.eventhandlers;

import com.techtitans.infratrack.platform.fleet.interfaces.acl.FleetContextFacade;
import com.techtitans.infratrack.platform.monitoring.application.commandservices.FleetAlertCommandService;
import com.techtitans.infratrack.platform.monitoring.application.commandservices.TelemetryReadingCommandService;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateFleetAlertCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.commands.CreateTelemetryReadingCommand;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertSeverity;
import com.techtitans.infratrack.platform.monitoring.domain.model.valueobjects.AlertType;
import com.techtitans.infratrack.platform.monitoring.domain.repositories.TelemetryReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class MonitoringApplicationReadyEventHandler {

    private final TelemetryReadingRepository telemetryReadingRepository;
    private final TelemetryReadingCommandService telemetryReadingCommandService;
    private final FleetAlertCommandService fleetAlertCommandService;
    private final FleetContextFacade fleetContextFacade;

    public MonitoringApplicationReadyEventHandler(
            TelemetryReadingRepository telemetryReadingRepository,
            TelemetryReadingCommandService telemetryReadingCommandService,
            FleetAlertCommandService fleetAlertCommandService,
            FleetContextFacade fleetContextFacade) {
        this.telemetryReadingRepository = telemetryReadingRepository;
        this.telemetryReadingCommandService = telemetryReadingCommandService;
        this.fleetAlertCommandService = fleetAlertCommandService;
        this.fleetContextFacade = fleetContextFacade;
    }

    @EventListener
    @Order(20)
    public void on(ApplicationReadyEvent event) {
        if (!telemetryReadingRepository.findAll().isEmpty()) {
            return;
        }
        log.info("Seeding InfraTrack monitoring demo data...");
        seedTelemetry();
        seedAlerts();
        log.info("Monitoring demo data seeding completed.");
    }

    private void seedTelemetry() {
        createTelemetry("IOT-NODE-001", 288.0, 72, -12.189, -76.982, 1240.0, 38.0, true, 0);
        createTelemetry("IOT-NODE-002", 162.0, 58, -12.195, -76.975, 980.0, 0.0, true, 1);
        createTelemetry("IOT-NODE-010", 284.0, 81, -12.098, -77.034, 560.0, 22.0, true, 2);
        createTelemetry("IOT-NODE-021", 120.0, 55, -12.120, -76.950, 2100.0, 0.0, false, 6);
    }

    private void createTelemetry(
            String nodeIdentifier,
            double fuelLevel,
            int fuelLevelPct,
            double lat,
            double lng,
            double engineHours,
            double speedKmh,
            boolean engineOn,
            long hoursAgo) {
        var nodeId = fleetContextFacade.fetchIotNodeIdByNodeIdentifier(nodeIdentifier);
        if (nodeId == null || nodeId <= 0) {
            return;
        }
        telemetryReadingCommandService.handle(new CreateTelemetryReadingCommand(
                nodeId,
                fuelLevel,
                fuelLevelPct,
                lat,
                lng,
                engineHours,
                speedKmh,
                engineOn,
                Instant.now().minus(hoursAgo, ChronoUnit.HOURS)));
    }

    private void seedAlerts() {
        createAlert("XYZ-918", AlertType.FUEL_THEFT, AlertSeverity.CRITICAL,
                "Caída de combustible del 9% en menos de 3 minutos — posible ordeño.", false, 2);
        createAlert("CAT-950", AlertType.MAINTENANCE, AlertSeverity.WARNING,
                "Temperatura de motor elevada — unidad en taller.", true, 8);
        createAlert("BCP-204", AlertType.GEOFENCE, AlertSeverity.WARNING,
                "Volquete salió del perímetro de Obra Vía Sur.", false, 12);
    }

    private void createAlert(
            String plateNumber,
            AlertType type,
            AlertSeverity severity,
            String description,
            boolean acknowledged,
            long hoursAgo) {
        var machineryId = fleetContextFacade.fetchMachineryIdByPlateNumber(plateNumber);
        if (machineryId == null || machineryId <= 0) {
            return;
        }
        fleetAlertCommandService.handle(new CreateFleetAlertCommand(
                machineryId,
                type,
                severity,
                description,
                acknowledged,
                Instant.now().minus(hoursAgo, ChronoUnit.HOURS)));
    }
}
