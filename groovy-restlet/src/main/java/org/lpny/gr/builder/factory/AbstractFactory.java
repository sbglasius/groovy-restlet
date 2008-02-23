/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 */
public abstract class AbstractFactory extends groovy.util.AbstractFactory {
    private static final Logger   LOG            = LoggerFactory
                                                         .getLogger(AbstractFactory.class);
    protected static final String OF_BEAN        = "ofBean";
    protected static final String OF_CLASS       = "ofClass";
    protected static final String SPRING_CONTEXT = "springContext";
    protected static final String URI            = "uri";
    private final List<String>    filters        = new ArrayList<String>();

    public AbstractFactory() {
        super();
        addFilter(OF_BEAN).addFilter(OF_CLASS).addFilter(URI);
    }

    /*
     * (non-Javadoc)
     * 
     * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport,
     *      java.lang.Object, java.lang.Object, java.util.Map)
     */
    @Override
    public Object newInstance(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        Validate.notNull(name);
        filterAttributes(builder.getContext(), attributes);
        Object result = SpringFinder.createFromSpringContext(
                (ApplicationContext) builder.getVariable("springContext"),
                builder.getContext());
        if (result == null) {
            result = newInstanceInner(builder, name, value, attributes);
        }
        return postNewInstance(result, builder, name, value, attributes);
    }

    @Override
    public void setChild(final FactoryBuilderSupport builder,
            final Object parent, final Object child) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("TO set Child {} on Parent {}", new Object[] { child,
                    parent });
        }
        super.setChild(builder, parent, child);
    }

    @Override
    public void setParent(final FactoryBuilderSupport builder,
            final Object parent, final Object child) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("TO set Parent {} on Child {}", new Object[] { parent,
                    child });
        }
        super.setParent(builder, parent, child);
    }

    /**
     * 
     * @param keyName
     * @return this object, in order to build a method chain.
     */
    protected AbstractFactory addFilter(final String keyName) {
        filters.add(keyName);
        return this;
    }

    @SuppressWarnings("unchecked")
    protected void filterAttributes(final Map context, final Map attributes) {
        for (final String key : filters) {
            if (attributes.containsKey(key)) {
                context.put(key, attributes.remove(key));
            }
        }
    }

    protected abstract Object newInstanceInner(
            final FactoryBuilderSupport builder, final Object name,
            final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException;

    protected Object postNewInstance(final Object instance,
            final FactoryBuilderSupport builder, final Object name,
            final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        return instance;
    }

}
