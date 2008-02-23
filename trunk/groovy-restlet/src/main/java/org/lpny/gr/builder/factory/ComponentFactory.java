/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.restlet.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ComponentFactory to create {@link Component}. <br/>
 * 
 * If user does not specify {@link AbstractFactory#OF_BEAN} or
 * {@link AbstractFactory#OF_CLASS} attributes, this factory will create an
 * instance of {@link Component}. If {@link AbstractFactory#OF_CLASS} was
 * specified but not Spring Context was defined, this factory will create a new
 * component instance using {@code Class.newInstance()}. Otherwise this factory
 * will consult spring context to create a new instance.
 * 
 * 
 * 
 * 
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class ComponentFactory extends AbstractFactory {
    static final Logger LOG = LoggerFactory.getLogger(ComponentFactory.class);

    public ComponentFactory() {
        super();
    }

    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("To create an instance, value={}",
                            new Object[] { value });
        }
        return new Component();
    }
}
