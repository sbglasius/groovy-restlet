/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.List;
import java.util.Map;

import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shortcut factory to create {@link Server}
 * 
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class ServerFactory extends RestletFactory {
    private static final Logger   LOG     = LoggerFactory
                                                  .getLogger(ServerFactory.class);
    protected static final String ADDRESS = "address";

    protected static final String PORT    = "port";

    /**
     * 
     */
    public ServerFactory() {
        super();
    }

    @Override
    public void setChild(final FactoryBuilderSupport builder,
            final Object parent, final Object child) {
        if (child == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("To set child {}", child);
        }
        if (child instanceof Restlet) {
            ((Server) parent).setTarget((Restlet) child);
        }
    }

    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        final List<Protocol> protocols = ClientFactory.extractProtocols(value,
                attributes);
        final String address = (String) attributes.remove(ADDRESS);
        final Integer port = (Integer) attributes.remove(PORT);
        return new Server(getRestletContext(builder), protocols, address, port,
                null);
    }

}
