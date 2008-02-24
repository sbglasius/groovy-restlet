/**
 * 
 */
package org.lpny.gr.builder.factory;

import java.util.HashMap;

import org.restlet.Application;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * 
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class ApplicationFactoryTest extends AbstractFactoryTest {
    private ApplicationFactory fixture;

    @BeforeTest(groups = { "unittest" })
    @Test(groups = { "unittest" })
    public void construct() {
        fixture = new ApplicationFactory();
    }

    @Test(groups = { "unittest" })
    public void testNewInstance() throws InstantiationException,
            IllegalAccessException {
        final Application app = (Application) fixture.newInstance(
                createMockBuilder(), "application", null, new HashMap());
        assert null != app;
    }

}
