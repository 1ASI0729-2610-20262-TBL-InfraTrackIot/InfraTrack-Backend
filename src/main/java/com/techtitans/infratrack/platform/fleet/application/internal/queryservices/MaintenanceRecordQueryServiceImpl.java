package com.techtitans.infratrack.platform.fleet.application.internal.queryservices;

import com.techtitans.infratrack.platform.fleet.application.queryservices.MaintenanceRecordQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.MaintenanceRecord;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMaintenanceRecordsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMaintenanceRecordByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MaintenanceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRecordQueryServiceImpl implements MaintenanceRecordQueryService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;

    public MaintenanceRecordQueryServiceImpl(MaintenanceRecordRepository maintenanceRecordRepository) {
        this.maintenanceRecordRepository = maintenanceRecordRepository;
    }

    @Override
    public Optional<MaintenanceRecord> handle(GetMaintenanceRecordByIdQuery query) {
        return maintenanceRecordRepository.findById(query.maintenanceRecordId());
    }

    @Override
    public List<MaintenanceRecord> handle(GetAllMaintenanceRecordsQuery query) {
        return maintenanceRecordRepository.findAll();
    }
}
