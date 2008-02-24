/**
 * 
 */
package org.lpny.gr;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;
import java.net.URI;

import org.lpny.gr.builder.Builder;
import org.restlet.Router;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 */
public class Constructor {
    private static final Logger      LOG = LoggerFactory
                                                 .getLogger(Constructor.class);
    private final Builder            builder;
    private final GroovyShell        shell;
    private final ApplicationContext springContext;

    public Constructor() {
        this(null);
    }

    public Constructor(final ApplicationContext springContext) {
        super();
        this.springContext = springContext;
        shell = new GroovyShell(Thread.currentThread().getContextClassLoader());
        builder = new Builder();
        builder.setVariable("springContext", springContext);
        // expose shell variables
        declareShellContext();
    }

    /**
     * To build from script specified by <code>scriptURI</code>
     * 
     * @param scriptURI
     * @return
     */
    public Object build(final URI scriptURI) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("To build from {}", scriptURI);
            }
            return shell.evaluate(new File(scriptURI));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void declareShellContext() {
        final Binding context = shell.getContext();
        context.setVariable("builder", builder);
        context.setVariable("protocol", Protocol.class);
        context.setVariable("mediaType", MediaType.class);
        context.setVariable("springContext", springContext);
        // router modes
        context.setVariable("routingMode", Router.class);
    }
}
