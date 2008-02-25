/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.restlet.Redirector;

/**
 * @author keke
 * @version
 * @since
 * @revision $Revision$
 */
public class RedirectorFactory extends RestletFactory {
    protected static final String TARGET = "target";

    public RedirectorFactory() {
        super();
    }

    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        return new Redirector(getRestletContext(builder), "");
    }

}
