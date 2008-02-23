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
        Object result = tryCreatingBySpringContext(builder);
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

    private void filterAttributes(final Map context, final Map attributes) {
        for (final String key : filters) {
            if (attributes.containsKey(key)) {
                context.put(key, attributes.remove(key));
            }
        }
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

    protected Object tryCreatingBySpringContext(
            final FactoryBuilderSupport builder) throws InstantiationException,
            IllegalAccessException {
        final ApplicationContext springContext = (ApplicationContext) builder
                .getVariable("springContext");
        // if user defines ofBean attribute
        final Map context = builder.getContext();
        if (context.containsKey(OF_BEAN)) {
            if (springContext == null) {
                throw new RuntimeException(
                        "No Spring Context was specified, \"ofBean\" is not supported! ");
            }
            final String beanName = (String) context.get(OF_BEAN);
            if (LOG.isDebugEnabled()) {
                LOG.debug("To create instance by beanName {}", beanName);
            }
            return springContext.getBean(beanName);
        }
        // if user defines ofClass attribute, factory will first try to
        // check
        // whether user specifies springContext, if so factory will try
        // to
        // create bean using spring autowire bean factory
        // otherwise will use class.newInstance
        if (context.containsKey(OF_CLASS)) {
            final Object ofClazz = context.get(OF_CLASS);
            Class<?> beanClass;
            if (ofClazz.getClass().equals(String.class)) {
                try {
                    beanClass = Class.forName((String) ofClazz);
                } catch (final ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (ofClazz.getClass().equals(Class.class)) {
                beanClass = (Class<?>) ofClazz;
            } else {
                throw new IllegalArgumentException("Illegal ofClass type "
                        + ofClazz);
            }

            return springContext == null ? beanClass.newInstance()
                    : springContext.getAutowireCapableBeanFactory().createBean(
                            beanClass);
        }
        return null;
    }

}
