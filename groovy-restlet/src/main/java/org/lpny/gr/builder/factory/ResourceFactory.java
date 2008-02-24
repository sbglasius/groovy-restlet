/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.restlet.Finder;
import org.restlet.Router;
import org.restlet.resource.Resource;
import org.springframework.context.ApplicationContext;

/**
 * Factory to create <i>"resource"</i> of Restlet.
 * 
 * Actually the instance created by this factory is a {@link SpringFinder}
 * instance rather than an instance of {@link Resource}. See implementation of
 * {@link Router#attach(String, Class)}.
 * 
 * <h4>Usage:</h4>
 * 
 * 
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 * @see SpringFinder
 * @see Finder
 * @see Router
 */
public class ResourceFactory extends AbstractFactory {
    protected static final String ACCEPT    = "accept";
    protected static final String HEAD      = "head";
    protected static final String OPTIONS   = "options";
    protected static final String REMOVE    = "remove";
    protected static final String REPRESENT = "represent";
    protected static final String STORE     = "store";

    /**
     * Constructor.
     */
    public ResourceFactory() {
        super();
        addFilter(REMOVE).addFilter(REPRESENT).addFilter(OPTIONS).addFilter(
                ACCEPT).addFilter(STORE).addFilter(HEAD);
    }

    @Override
    public Object newInstance(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        Validate.notNull(name);
        filterAttributes(builder.getContext(), attributes);
        return newInstanceInner(builder, name, value, attributes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.lpny.gr.builder.factory.AbstractFactory#newInstanceInner(groovy.util.FactoryBuilderSupport,
     *      java.lang.Object, java.lang.Object, java.util.Map)
     */
    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        return new SpringFinder((ApplicationContext) builder
                .getVariable(SPRING_CONTEXT), new HashMap(builder.getContext()));
    }

}
