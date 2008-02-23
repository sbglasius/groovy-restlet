/**
 * 
 */
package org.lpny.gr.builder;

import groovy.util.FactoryBuilderSupport;

import org.restlet.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Restlet Builder
 * 
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 * @see <a href="http://www.restlet.org">Restlet</a>
 * @see <a href="http://groovy.codehaus.org/GroovyMarkup">Groovy Markup</a>
 */
public class Builder extends FactoryBuilderSupport {
    private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

    public Builder() {
        super();
        registerFactories();
    }

    private void registerFactories() {
        registerBeanFactory("component", Component.class);
    }
}
