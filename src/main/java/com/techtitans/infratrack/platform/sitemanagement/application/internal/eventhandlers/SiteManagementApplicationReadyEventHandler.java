package com.techtitans.infratrack.platform.sitemanagement.application.internal.eventhandlers;

import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteStaffCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl.SiteManagementExternalFleetService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.aggregates.WorksiteStaff;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignStaffToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.CreateWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.StaffStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteStatus;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.valueobjects.WorksiteType;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteRepository;
import com.techtitans.infratrack.platform.sitemanagement.domain.repositories.WorksiteStaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * Seeds demo worksites aligned with the Angular {@code site-management.store.ts} payloads.
 */
@Service
@Slf4j
public class SiteManagementApplicationReadyEventHandler {

    private final WorksiteRepository worksiteRepository;
    private final WorksiteStaffRepository worksiteStaffRepository;
    private final WorksiteCommandService worksiteCommandService;
    private final WorksiteStaffCommandService worksiteStaffCommandService;
    private final SiteManagementExternalFleetService externalFleetService;

    public SiteManagementApplicationReadyEventHandler(
            WorksiteRepository worksiteRepository,
            WorksiteStaffRepository worksiteStaffRepository,
            WorksiteCommandService worksiteCommandService,
            WorksiteStaffCommandService worksiteStaffCommandService,
            SiteManagementExternalFleetService externalFleetService) {
        this.worksiteRepository = worksiteRepository;
        this.worksiteStaffRepository = worksiteStaffRepository;
        this.worksiteCommandService = worksiteCommandService;
        this.worksiteStaffCommandService = worksiteStaffCommandService;
        this.externalFleetService = externalFleetService;
    }

    @EventListener
    @Order(15)
    public void on(ApplicationReadyEvent event) {
        if (!worksiteRepository.findAll().isEmpty()) {
            return;
        }
        log.info("Seeding InfraTrack site-management demo data...");
        seedWorksites();
        seedStaff();
        seedTransportAssignments();
        log.info("Site-management demo data seeding completed.");
    }

    private void seedWorksites() {
        createWorksite("Obra Vía Sur — Tramo 3", "Lima", WorksiteType.ROAD, WorksiteStatus.ACTIVE,
                "Av. Los Incas 1240, Villa El Salvador", "Ing. Patricia Rojas", -12.189, -76.982);
        createWorksite("Torre Nexus — Edificación", "San Isidro", WorksiteType.BUILDING, WorksiteStatus.ACTIVE,
                "Calle Las Begonias 480", "Ing. Luis Mendoza", -12.098, -77.034);
        createWorksite("Almacén Central Ferretería Norte", "Los Olivos", WorksiteType.WAREHOUSE, WorksiteStatus.ACTIVE,
                "Av. Alfredo Mendiola 6120", "Ing. Carla Vargas", -11.991, -77.074);
        createWorksite("Ampliación Ruta 18", "Ica", WorksiteType.ROAD, WorksiteStatus.FINISHED,
                "Panamericana Sur km 312", "Ing. Jorge Salas", -14.068, -75.728);
    }

    private void createWorksite(
            String name,
            String city,
            WorksiteType type,
            WorksiteStatus status,
            String address,
            String leadEngineer,
            double latitude,
            double longitude) {
        worksiteCommandService.handle(new CreateWorksiteCommand(
                name, city, type, status, address, leadEngineer, latitude, longitude)).toOptional().orElseThrow();
    }

    private void seedStaff() {
        saveStaff("Carlos Vizcarra", "carlos.vizcarra@infratrack.demo", "+51 999 111 222",
                "Q1-2045", StaffStatus.ACTIVE, 2, 38, "Volquete BCP-204");
        saveStaff("María Solís", "maria.solis@infratrack.demo", "+51 999 333 444",
                "Q1-2088", StaffStatus.ACTIVE, 0, 42, "Cargador frontal CAT-950");
        saveStaff("Jorge Paredes", "jorge.paredes@infratrack.demo", "+51 999 555 666",
                "Q1-3012", StaffStatus.ACTIVE, 1, 35, null);
        saveStaff("Ana Torres", "ana.torres@infratrack.demo", "+51 999 777 888",
                "Q1-3150", StaffStatus.INACTIVE, 0, 0, null);

        assignStaff(1L, 1L);
        assignStaff(2L, 1L);
        assignStaff(3L, 2L);
        assignStaff(1L, 3L);
        assignStaff(3L, 3L);
    }

    private void saveStaff(
            String fullName,
            String email,
            String phone,
            String license,
            StaffStatus status,
            int alerts,
            int drivingHours,
            String currentVehicle) {
        var staff = new WorksiteStaff();
        staff.setFullName(fullName);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setLicenseNumber(license);
        staff.setStatus(status);
        staff.setAlertsLast30Days(alerts);
        staff.setDrivingHoursWeek(drivingHours);
        staff.setCurrentVehicle(currentVehicle);
        worksiteStaffRepository.save(staff);
    }

    private void assignStaff(Long worksiteId, Long staffId) {
        worksiteStaffCommandService.handle(new AssignStaffToWorksiteCommand(worksiteId, staffId));
    }

    private void seedTransportAssignments() {
        assignTransport(1L, "BCP-204", "Villa El Salvador · en ruta");
        assignTransport(1L, "XYZ-918", "Tramo 3 · zona sur");
        assignTransport(2L, "LIM-442", "Torre Nexus · base");
        assignTransport(3L, "CAT-950", "Almacén Norte · patio");
        assignTransport(3L, "NOR-118", "Ruta despacho Mz. C");
    }

    private void assignTransport(Long worksiteId, String plateNumber, String gpsLabel) {
        var machineryId = externalFleetService.fetchMachineryIdByPlateNumber(plateNumber);
        if (machineryId == null || machineryId <= 0) {
            return;
        }
        worksiteCommandService.handle(new AssignTransportToWorksiteCommand(worksiteId, machineryId, gpsLabel));
    }
}
