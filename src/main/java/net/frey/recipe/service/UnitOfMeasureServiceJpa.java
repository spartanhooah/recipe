package net.frey.recipe.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.frey.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceJpa implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand toCommandConverter;

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasure() {
        Set<UnitOfMeasureCommand> unitsOfMeasure = new HashSet<>();
        unitOfMeasureRepository
                .findAll()
                .iterator()
                .forEachRemaining(
                        unitOfMeasure ->
                                unitsOfMeasure.add(toCommandConverter.convert(unitOfMeasure)));

        return unitsOfMeasure;
    }
}
