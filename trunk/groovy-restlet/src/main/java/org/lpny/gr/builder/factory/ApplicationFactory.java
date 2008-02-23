/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 */
public class ApplicationFactory extends AbstractFactory {
    private static final Logger   LOG    = LoggerFactory
                                                 .getLogger(ApplicationFactory.class);

    protected static final String HANDLE = "handle";

    public ApplicationFactory() {
        super();
        // addFilter(HANDLE);
    }

    @Override
    public void setParent(final FactoryBuilderSupport builder,
            final Object parent, final Object child) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("SetParent: parent={}", parent);
        }

        if (parent == null) {
            return;
        }
        final String uri = (String) builder.getContext().get(URI);
        if (uri == null) {
            return;
        }
        if (parent instanceof Component) {
            ((Component) parent).getDefaultHost().attach(uri, (Restlet) child);
        }
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
        final Object parent = builder.getParentNode();
        Context context = null;
        if (parent != null) {
            if (parent instanceof Restlet) {
                context = ((Restlet) parent).getContext();
            }
        }
        return new Application(context);
    }

}
