package com.techtitans.infratrack.platform.sitemanagement.interfaces.rest;

import com.techtitans.infratrack.platform.shared.interfaces.rest.resources.MessageResource;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.commandservices.WorksiteStaffCommandService;
import com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl.SiteManagementExternalFleetService;
import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteQueryService;
import com.techtitans.infratrack.platform.sitemanagement.application.queryservices.WorksiteStaffQueryService;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignStaffToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.commands.AssignTransportToWorksiteCommand;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksitesQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetStaffForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetTransportsForWorksiteQuery;
import com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetWorksiteByIdQuery;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.resources.*;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteStaffResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.sitemanagement.interfaces.rest.transform.WorksiteTransportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/worksites", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Worksites", description = "Construction sites and asset assignments")
public class WorksitesController {

    private final WorksiteCommandService worksiteCommandService;
    private final WorksiteStaffCommandService worksiteStaffCommandService;
    private final WorksiteQueryService worksiteQueryService;
    private final WorksiteStaffQueryService worksiteStaffQueryService;
    private final SiteManagementExternalFleetService externalFleetService;

    public WorksitesController(
            WorksiteCommandService worksiteCommandService,
            WorksiteStaffCommandService worksiteStaffCommandService,
            WorksiteQueryService worksiteQueryService,
            WorksiteStaffQueryService worksiteStaffQueryService,
            SiteManagementExternalFleetService externalFleetService) {
        this.worksiteCommandService = worksiteCommandService;
        this.worksiteStaffCommandService = worksiteStaffCommandService;
        this.worksiteQueryService = worksiteQueryService;
        this.worksiteStaffQueryService = worksiteStaffQueryService;
        this.externalFleetService = externalFleetService;
    }

    @GetMapping
    public ResponseEntity<List<WorksiteResource>> getAllWorksites() {
        var items = worksiteQueryService.handle(new GetAllWorksitesQuery()).stream()
                .map(worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/staff")
    public ResponseEntity<?> createStaff(@RequestBody CreateWorksiteStaffResource resource) {
        var command = WorksiteStaffResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = worksiteStaffCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/staff")
    public ResponseEntity<List<WorksiteStaffResource>> getAllStaff() {
        var items = worksiteStaffQueryService.handle(new com.techtitans.infratrack.platform.sitemanagement.domain.model.queries.GetAllWorksiteStaffQuery())
                .stream()
                .map(staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{worksiteId}")
    public ResponseEntity<WorksiteResource> getWorksiteById(@PathVariable Long worksiteId) {
        return worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId))
                .map(worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createWorksite(@RequestBody CreateWorksiteResource resource) {
        var command = WorksiteResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = worksiteCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                worksite -> WorksiteResourceFromEntityAssembler.toResourceFromEntity(worksite, worksiteQueryService),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{worksiteId}/transports")
    public ResponseEntity<List<WorksiteTransportResource>> getTransportsForWorksite(@PathVariable Long worksiteId) {
        if (worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var items = worksiteQueryService.handle(new GetTransportsForWorksiteQuery(worksiteId)).stream()
                .map(assignment -> WorksiteTransportResourceFromEntityAssembler.toResourceFromEntity(assignment, externalFleetService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{worksiteId}/staff")
    public ResponseEntity<List<WorksiteStaffResource>> getStaffForWorksite(@PathVariable Long worksiteId) {
        if (worksiteQueryService.handle(new GetWorksiteByIdQuery(worksiteId)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var items = worksiteStaffQueryService.handle(new GetStaffForWorksiteQuery(worksiteId)).stream()
                .map(staff -> WorksiteStaffResourceFromEntityAssembler.toResourceFromEntity(staff, worksiteStaffQueryService))
                .toList();
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{worksiteId}/transports/{transportId}")
    public ResponseEntity<?> assignTransport(
            @PathVariable Long worksiteId,
            @PathVariable Long transportId) {
        var command = new AssignTransportToWorksiteCommand(worksiteId, transportId, null);
        var result = worksiteCommandService.handle(command)
                .map(assignment -> WorksiteTransportResourceFromEntityAssembler.toResourceFromEntity(assignment, externalFleetService));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, resource -> resource, HttpStatus.OK);
    }

    @PutMapping("/{worksiteId}/staff/{staffId}")
    public ResponseEntity<?> assignStaff(@PathVariable Long worksiteId, @PathVariable Long staffId) {
        var result = worksiteStaffCommandService.handle(new AssignStaffToWorksiteCommand(worksiteId, staffId))
                .map(id -> new MessageResource("Staff assigned to worksite successfully"));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, message -> message, HttpStatus.OK);
    }
}
