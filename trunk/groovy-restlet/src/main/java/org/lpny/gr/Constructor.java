/**
 * 
 */
package org.lpny.gr;

import groovy.lang.GroovyShell;

import java.io.File;
import java.net.URL;

import org.lpny.gr.builder.Builder;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 */
public class Constructor {
    private static final Logger LOG = LoggerFactory
                                            .getLogger(Constructor.class);
    private final GroovyShell   shell;

    public Constructor() {
        super();
        shell = new GroovyShell(Thread.currentThread().getContextClassLoader());
        shell.getContext().setVariable("builder", new Builder());
        shell.getContext().setVariable("protocol", Protocol.class);
    }

    public Component build(final URL mainScript) {
        try {
            return (Component) shell.evaluate(new File(mainScript.toURI()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
