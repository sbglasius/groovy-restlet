/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;

/**
 * @author keke
 * @version
 * @since
 * @revision $Revision$
 */
public class FactoryUtils {
    /**
     * 
     * @param value
     * @param attributes
     * @return
     */
    @SuppressWarnings("unchecked")
    protected static List<Protocol> extractProtocols(final Object value,
            final Map attributes) {
        final List<Protocol> protocols = new ArrayList<Protocol>();
        Protocol protocol = (Protocol) attributes
                .remove(ClientFactory.PROTOCOL);
        if (protocol == null) {
            if (value != null && value instanceof Protocol) {
                protocol = (Protocol) value;
            }
        }
        if (protocol != null) {
            protocols.add(protocol);
        }
        final List<Protocol> list = (List<Protocol>) attributes
                .remove(ClientFactory.PROTOCOLS);
        if (list != null) {
            protocols.addAll(list);
        }
        return protocols;
    }

    protected static Context getParentRestletContext(
            final FactoryBuilderSupport builder) {
        Context context = null;
        if (builder.getParentNode() instanceof Restlet) {
            context = ((Restlet) builder.getParentNode()).getContext();
        }
        return context;
    }

}
