package com.techtitans.infratrack.platform.fleet.application.internal.commandservices;

import com.techtitans.infratrack.platform.fleet.application.commandservices.IotNodeCommandService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateIotNodeCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.LinkIotNodeToMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.repositories.IotNodeRepository;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class IotNodeCommandServiceImpl implements IotNodeCommandService {

    private final IotNodeRepository iotNodeRepository;
    private final MachineryRepository machineryRepository;

    public IotNodeCommandServiceImpl(IotNodeRepository iotNodeRepository, MachineryRepository machineryRepository) {
        this.iotNodeRepository = iotNodeRepository;
        this.machineryRepository = machineryRepository;
    }

    @Override
    public Result<IotNode, ApplicationError> handle(CreateIotNodeCommand command) {
        if (iotNodeRepository.existsByNodeIdentifier(command.nodeIdentifier())) {
            return Result.failure(ApplicationError.conflict("IotNode", "Node identifier '%s' already exists".formatted(command.nodeIdentifier())));
        }
        if (!machineryRepository.existsById(command.machineryId())) {
            return Result.failure(ApplicationError.notFound("Machinery", command.machineryId().toString()));
        }
        try {
            return Result.success(iotNodeRepository.save(new IotNode(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-iot-node", e.getMessage()));
        }
    }

    @Override
    public Result<IotNode, ApplicationError> handle(LinkIotNodeToMachineryCommand command) {
        var node = iotNodeRepository.findById(command.iotNodeId());
        if (node.isEmpty()) {
            return Result.failure(ApplicationError.notFound("IotNode", command.iotNodeId().toString()));
        }
        if (!machineryRepository.existsById(command.machineryId())) {
            return Result.failure(ApplicationError.notFound("Machinery", command.machineryId().toString()));
        }
        try {
            var linked = node.get().linkToMachinery(command.machineryId());
            return Result.success(iotNodeRepository.save(linked));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("link-iot-node", e.getMessage()));
        }
    }
}
