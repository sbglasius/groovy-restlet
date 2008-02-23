/**
 * 
 */
package org.lpny.gr.builder;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.lpny.gr.Constructor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 */
public class BasicBuilderTest {
    private Constructor constructor;

    /**
     * @throws IOException
     * @throws CompilationFailedException
     * 
     */
    @Test(groups = { "unittest", "builder" })
    public final void createComponent() throws CompilationFailedException,
            IOException {
        constructor
                .build(new File(BuilderTester.TEST_SCRIPT_ROOT
                        + "/org/lpny/gr/builder/BuildComponent.groovy").toURI()
                        .toURL());
    }

    @BeforeClass(groups = { "unittest" })
    public void setUp() {
        constructor = new Constructor();
    }
}