package net.frey.recipe.service;

import java.util.Set;
import net.frey.recipe.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasure();
}
