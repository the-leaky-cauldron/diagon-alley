package org.theleakycauldron.diagonalley.commons.generators;

import com.fasterxml.uuid.impl.TimeBasedReorderedGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Component
public class SortedUUIDGenerator implements IdentifierGenerator {

    private final TimeBasedReorderedGenerator timeBasedReorderedGenerator;

    public SortedUUIDGenerator(TimeBasedReorderedGenerator timeBasedReorderedGenerator){
        this.timeBasedReorderedGenerator = timeBasedReorderedGenerator;
    }
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return timeBasedReorderedGenerator.generate();
    }
}
