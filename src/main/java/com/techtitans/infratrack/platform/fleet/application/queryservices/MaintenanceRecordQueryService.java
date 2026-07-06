package com.techtitans.infratrack.platform.fleet.application.queryservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMaintenanceRecordsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordsByMachineryIdQuery;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRecordQueryService {
    Optional<MaintenanceRecord> handle(GetMaintenanceRecordByIdQuery query);
    List<MaintenanceRecord> handle(GetAllMaintenanceRecordsQuery query);
    List<MaintenanceRecord> handle(GetMaintenanceRecordsByMachineryIdQuery query);
}
