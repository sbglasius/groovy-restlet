/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.restlet.Guard;
import org.restlet.data.ChallengeScheme;

/**
 * Shortcut factory to create {@link Guard}.
 * 
 * <h4>Important attributes</h4>
 * <ul>
 * <li><code>scheme</code>: specify the {@link ChallengeScheme} used to
 * create this {@link Guard}</li>
 * <li><code>realm</code>: specify the realm used to create this
 * {@link Guard}</li>
 * </ul>
 * 
 * @author keke
 * @reversion $Revision$
 * @since 0.1.0
 * @see Guard#Guard(org.restlet.Context, ChallengeScheme, String)
 */
public class GuardFactory extends RestletFactory {
    protected static final String REALM  = "realm";
    protected static final String SCHEME = "scheme";

    /*
     * (non-Javadoc)
     * 
     * @see org.lpny.gr.builder.factory.AbstractFactory#newInstanceInner(groovy.util.FactoryBuilderSupport,
     *      java.lang.Object, java.lang.Object, java.util.Map)
     */
    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        return new Guard(getRestletContext(builder),
                (ChallengeScheme) attributes.remove(SCHEME),
                (String) attributes.remove(REALM));
    }

}
