package com.techtitans.infratrack.platform.fleet.application.commandservices;

import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.CreateIotNodeCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.LinkIotNodeToMachineryCommand;
import com.techtitans.infratrack.platform.shared.application.result.ApplicationError;
import com.techtitans.infratrack.platform.shared.application.result.Result;

public interface IotNodeCommandService {
    Result<IotNode, ApplicationError> handle(CreateIotNodeCommand command);
    Result<IotNode, ApplicationError> handle(LinkIotNodeToMachineryCommand command);
}
