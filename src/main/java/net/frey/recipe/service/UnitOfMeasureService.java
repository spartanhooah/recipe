package net.frey.recipe.service;

import net.frey.recipe.command.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUnitsOfMeasure();
}
