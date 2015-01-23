/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import org.lpny.gr.builder.mock.MockFactoryBuilderSupport;

/**
 * @author keke
 * @reversion $Revision$
 * @version
 */
public abstract class AbstractFactoryTest {

    protected FactoryBuilderSupport createMockBuilder() {
        final FactoryBuilderSupport mockBuilder = new MockFactoryBuilderSupport();
        mockBuilder.setVariable("springContext", null);
        return mockBuilder;
    }

}
