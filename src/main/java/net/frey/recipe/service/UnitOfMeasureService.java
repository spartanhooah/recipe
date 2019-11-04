package net.frey.recipe.service;

import net.frey.recipe.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasure();
}
