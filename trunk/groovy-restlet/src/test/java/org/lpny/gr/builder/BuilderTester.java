/**
 * 
 */
package org.lpny.gr.builder;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

/**
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class BuilderTester {
    public static final String TEST_SCRIPT_ROOT = "./src/test/groovy";

    private final Builder      builder;

    public BuilderTester(final Builder builder) {
        super();
        this.builder = builder;
    }

    public final Object runScript(final String scriptPath)
            throws CompilationFailedException, IOException {
        final GroovyShell shell = new GroovyShell(Thread.currentThread()
                .getContextClassLoader());
        shell.getContext().setVariable("builder", builder);
        return shell.evaluate(new File(scriptPath));
    }
}
