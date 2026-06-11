package com.techtitans.infratrack.platform.fleet.application.internal.eventhandlers;

import com.techtitans.infratrack.platform.fleet.application.commandservices.FleetOperatorCommandService;
import com.techtitans.infratrack.platform.fleet.application.commandservices.IotNodeCommandService;
import com.techtitans.infratrack.platform.fleet.application.commandservices.MachineryCommandService;
import com.techtitans.infratrack.platform.fleet.application.commandservices.MaintenanceRecordCommandService;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateFleetOperatorCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateIotNodeCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateMaintenanceRecordCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.valueobjects.*;
import com.techtitans.infratrack.platform.fleet.domain.repositories.FleetOperatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Seeds demo fleet data when the database is empty (aligned with Angular demo payloads).
 */
@Service
@Slf4j
public class FleetApplicationReadyEventHandler {

    private final FleetOperatorRepository fleetOperatorRepository;
    private final FleetOperatorCommandService fleetOperatorCommandService;
    private final MachineryCommandService machineryCommandService;
    private final IotNodeCommandService iotNodeCommandService;
    private final MaintenanceRecordCommandService maintenanceRecordCommandService;

    public FleetApplicationReadyEventHandler(
            FleetOperatorRepository fleetOperatorRepository,
            FleetOperatorCommandService fleetOperatorCommandService,
            MachineryCommandService machineryCommandService,
            IotNodeCommandService iotNodeCommandService,
            MaintenanceRecordCommandService maintenanceRecordCommandService) {
        this.fleetOperatorRepository = fleetOperatorRepository;
        this.fleetOperatorCommandService = fleetOperatorCommandService;
        this.machineryCommandService = machineryCommandService;
        this.iotNodeCommandService = iotNodeCommandService;
        this.maintenanceRecordCommandService = maintenanceRecordCommandService;
    }

    @EventListener
    @Order(10)
    public void on(ApplicationReadyEvent event) {
        if (!fleetOperatorRepository.findAll().isEmpty()) {
            return;
        }
        log.info("Seeding InfraTrack fleet demo data...");
        seedOperators();
        seedMachineryAndNodes();
        log.info("Fleet demo data seeding completed.");
    }

    private void seedOperators() {
        createOperator(1L, "Carlos Vizcarra", "Q1-2045", "+51 999 111 222");
        createOperator(2L, "María Solís", "Q1-2088", "+51 999 333 444");
        createOperator(3L, "Jorge Paredes", "Q1-3012", "+51 999 555 666");
        createOperator(4L, "Ana Torres", "Q1-3150", "+51 999 777 888");
    }

    private void createOperator(Long userId, String fullName, String license, String phone) {
        fleetOperatorCommandService.handle(new CreateFleetOperatorCommand(
                userId, fullName, license, phone, OperatorStatus.ACTIVE));
    }

    private void seedMachineryAndNodes() {
        var m1 = machineryCommandService.handle(new CreateMachineryCommand(
                1L, "BCP-204", "FH16", "Volvo", FuelType.DIESEL, 400, MachineryStatus.ACTIVE, "")).toOptional().orElseThrow();
        var m2 = machineryCommandService.handle(new CreateMachineryCommand(
                2L, "XYZ-918", "320D", "Caterpillar", FuelType.DIESEL, 280, MachineryStatus.ACTIVE, "")).toOptional().orElseThrow();
        var m3 = machineryCommandService.handle(new CreateMachineryCommand(
                3L, "LIM-442", "Actros", "Mercedes-Benz", FuelType.DIESEL, 350, MachineryStatus.ACTIVE, "")).toOptional().orElseThrow();
        var m4 = machineryCommandService.handle(new CreateMachineryCommand(
                4L, "CAT-950", "950 GC", "Caterpillar", FuelType.DIESEL, 220, MachineryStatus.MAINTENANCE, "")).toOptional().orElseThrow();
        var m5 = machineryCommandService.handle(new CreateMachineryCommand(
                2L, "NOR-118", "Actros", "Mercedes-Benz", FuelType.DIESEL, 350, MachineryStatus.ACTIVE, "")).toOptional().orElseThrow();

        iotNodeCommandService.handle(new CreateIotNodeCommand(
                m1.getId(), "IOT-NODE-001", "2.4.1", 3.72, ConnectionStatus.ONLINE, Instant.now()));
        iotNodeCommandService.handle(new CreateIotNodeCommand(
                m2.getId(), "IOT-NODE-002", "2.4.0", 3.55, ConnectionStatus.ONLINE, Instant.now()));
        iotNodeCommandService.handle(new CreateIotNodeCommand(
                m3.getId(), "IOT-NODE-010", "2.3.8", 3.41, ConnectionStatus.OFFLINE, Instant.now()));
        iotNodeCommandService.handle(new CreateIotNodeCommand(
                m4.getId(), "IOT-NODE-021", "2.5.0", 3.88, ConnectionStatus.ONLINE, Instant.now()));
        iotNodeCommandService.handle(new CreateIotNodeCommand(
                m5.getId(), "IOT-NODE-022", "2.4.2", 3.61, ConnectionStatus.ONLINE, Instant.now()));

        maintenanceRecordCommandService.handle(new CreateMaintenanceRecordCommand(
                m4.getId(), MaintenanceServiceType.GENERAL, "Cambio de filtros y revisión hidráulica",
                850.0, 4200.0, LocalDate.now().minusDays(10), LocalDate.now().plusDays(20)));
        maintenanceRecordCommandService.handle(new CreateMaintenanceRecordCommand(
                m1.getId(), MaintenanceServiceType.OIL_CHANGE, "Cambio de aceite motor",
                320.0, 3100.0, LocalDate.now().minusDays(30), LocalDate.now().plusDays(60)));
    }
}
