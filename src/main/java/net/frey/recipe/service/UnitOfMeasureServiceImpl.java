package net.frey.recipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.frey.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand toCommandConverter;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUnitsOfMeasure() {
        return unitOfMeasureReactiveRepository.findAll().map(toCommandConverter::convert);
        //        Set<UnitOfMeasureCommand> unitsOfMeasure = new HashSet<>();
        //        unitOfMeasureRepository
        //                .findAll()
        //                .iterator()
        //                .forEachRemaining(
        //                        unitOfMeasure ->
        //
        // unitsOfMeasure.add(toCommandConverter.convert(unitOfMeasure)));
        //
        //        return unitsOfMeasure;
    }
}
