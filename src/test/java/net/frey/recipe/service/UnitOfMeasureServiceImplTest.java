package net.frey.recipe.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import net.frey.recipe.domain.UnitOfMeasure;
import net.frey.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

@RunWith(MockitoJUnitRunner.class)
public class UnitOfMeasureServiceImplTest {
    private UnitOfMeasureToUnitOfMeasureCommand toCommandConverter =
            new UnitOfMeasureToUnitOfMeasureCommand();

    private UnitOfMeasureService service;

    @Mock UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, toCommandConverter);
    }

    @Test
    public void listAllUoms() {
        // given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");

        when(unitOfMeasureRepository.findAll()).thenReturn(Flux.just(uom1, uom2));

        // when
        List<UnitOfMeasureCommand> commands = service.listAllUnitsOfMeasure().collectList().block();

        // then
        assertThat(commands.size(), is(2));
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}
