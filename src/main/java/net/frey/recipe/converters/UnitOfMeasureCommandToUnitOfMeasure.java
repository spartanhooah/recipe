package net.frey.recipe.converters;

import lombok.Synchronized;
import net.frey.recipe.command.UnitOfMeasureCommand;
import net.frey.recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure unit = new UnitOfMeasure();
        unit.setId(source.getId());
        unit.setDescription(source.getDescription());

        return unit;
    }
}
