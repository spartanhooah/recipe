package net.frey.recipe.converters;

import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    private static final String DESCRIPTION = "unit";
    private static final long ID = 1L;
    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void returnsNullWhenNullPassed() {
        assertThat(converter.convert(null), is(nullValue()));
    }

    @Test
    public void convertsEmptyObject() {
        assertThat(converter.convert(new UnitOfMeasureCommand()), is(notNullValue()));
    }

    @Test
    public void convertsPopulatedObject() {
        UnitOfMeasureCommand commandUnit = new UnitOfMeasureCommand();
        commandUnit.setId(ID);
        commandUnit.setDescription(DESCRIPTION);

        UnitOfMeasure resultUnit = converter.convert(commandUnit);

        assertThat(resultUnit.getId(), is(ID));
        assertThat(resultUnit.getDescription(), is(DESCRIPTION));
    }
}