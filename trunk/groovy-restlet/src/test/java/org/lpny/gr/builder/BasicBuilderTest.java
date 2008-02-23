/**
 * 
 */
package org.lpny.gr.builder;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.restlet.Component;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 */
public class BasicBuilderTest {
    private BuilderTester tester;

    /**
     * @throws IOException
     * @throws CompilationFailedException
     * 
     */
    @Test(groups = { "unittest", "builder" })
    public final void createComponent() throws CompilationFailedException,
            IOException {
        final Component result = (Component) tester
                .runScript(BuilderTester.TEST_SCRIPT_ROOT
                        + "/org/lpny/gr/builder/BuildComponent.groovy");
        assert result != null;
    }

    @BeforeClass(groups = { "unittest" })
    public void setUp() {
        tester = new BuilderTester(new Builder());
    }
}