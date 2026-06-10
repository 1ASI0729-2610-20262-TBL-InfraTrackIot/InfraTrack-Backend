package com.techtitans.infratrack.platform.fleet.application.internal.queryservices;

import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMachineryQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineryQueryServiceImpl implements MachineryQueryService {

    private final MachineryRepository machineryRepository;

    public MachineryQueryServiceImpl(MachineryRepository machineryRepository) {
        this.machineryRepository = machineryRepository;
    }

    @Override
    public Optional<Machinery> handle(GetMachineryByIdQuery query) {
        return machineryRepository.findById(query.machineryId());
    }

    @Override
    public List<Machinery> handle(GetAllMachineryQuery query) {
        return machineryRepository.findAll();
    }
}
