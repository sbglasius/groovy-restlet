/**
 * 
 */
package org.lpny.gr.example.tutorial;

import java.io.File;

import org.lpny.gr.Constructor;
import org.restlet.Client;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class ExampleRunner {
    private static final String ROOT = "./src/test/groovy/org/lpny/gr/examples/tutorials/";

    /**
     * @param args
     */
    public static void main(final String[] args) {
        // TODO Auto-generated method stub

    }

    private Constructor fixture;

    @Test(groups = { "examples" })
    public void runPart02() {
        final Client client = (Client) fixture.build(new File(ROOT,
                "Part02.groovy").toURI());
        assert client != null;
    }

    @Test(groups = { "examples" })
    public void runPart03() {
        fixture.build(new File(ROOT, "Part03.groovy").toURI());

    }

    @BeforeClass(groups = { "examples" })
    public void setup() {
        fixture = new Constructor();
    }

}
