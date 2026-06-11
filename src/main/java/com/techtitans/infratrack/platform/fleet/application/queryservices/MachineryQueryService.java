package com.techtitans.infratrack.platform.fleet.application.queryservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.Machinery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMachineryQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MachineryQueryService {
    Optional<Machinery> handle(GetMachineryByIdQuery query);
    List<Machinery> handle(GetAllMachineryQuery query);
}
