/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.lang.Closure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.LazyLoader;

import org.restlet.Finder;
import org.restlet.Handler;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author keke
 * @reversion $Revision$
 * @version
 */
public class SpringFinder extends Finder {
    private static final Logger   LOG     = LoggerFactory
                                                  .getLogger(SpringFinder.class);
    private static final String[] METHODS = { ResourceFactory.REMOVE,
            ResourceFactory.STORE, ResourceFactory.HEAD,
            ResourceFactory.ACCEPT, ResourceFactory.OPTIONS,
            ResourceFactory.REPRESENT    };

    @SuppressWarnings("unchecked")
    static Object createFromSpringContext(
            final ApplicationContext springContext, final Map context)
            throws InstantiationException, IllegalAccessException {
        if (context.containsKey(AbstractFactory.OF_BEAN)) {
            if (springContext == null) {
                throw new RuntimeException(
                        "No Spring Context was specified, \"ofBean\" is not supported! ");
            }
            final String beanName = (String) context
                    .get(AbstractFactory.OF_BEAN);
            if (LOG.isDebugEnabled()) {
                LOG.debug("To create instance by beanName {}", beanName);
            }
            return springContext.getBean(beanName);
        }
        // if user defines ofClass attribute, factory will first try to
        // check
        // whether user specifies springContext, if so factory will try
        // to
        // create bean using spring autowire bean factory
        // otherwise will use class.newInstance
        if (context.containsKey(AbstractFactory.OF_CLASS)) {
            final Object ofClazz = context.get(AbstractFactory.OF_CLASS);
            Class<?> beanClass;
            if (ofClazz.getClass().equals(String.class)) {
                try {
                    beanClass = Class.forName((String) ofClazz);
                } catch (final ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (ofClazz.getClass().equals(Class.class)) {
                beanClass = (Class<?>) ofClazz;
            } else {
                throw new IllegalArgumentException("Illegal ofClass type "
                        + ofClazz);
            }

            return springContext == null ? beanClass.newInstance()
                    : springContext.getAutowireCapableBeanFactory().createBean(
                            beanClass);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private final Map                context;

    private final ApplicationContext springContext;

    @SuppressWarnings("unchecked")
    public SpringFinder(final ApplicationContext springContext,
            final Map context) {
        this.springContext = springContext;
        this.context = context;
    }

    private Handler postCreate(final Handler handler) {
        final Map<String, Closure> methodHandlers = new HashMap<String, Closure>();
        for (final String method : METHODS) {
            final Closure methodHandler = (Closure) context.get(method);
            if (methodHandler != null) {
                String methodName = method;
                if (method.equals(ResourceFactory.REMOVE)
                        || method.equals(ResourceFactory.ACCEPT)
                        || method.equals(ResourceFactory.STORE)) {
                    methodName = method + "Representation";
                }
                methodHandlers.put(methodName, methodHandler);
            }
        }
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Resource.class);
        enhancer.setCallbackFilter(new CallbackFilter() {

            @Override
            public int accept(final Method method) {
                if (methodHandlers.containsKey(method.getName())) {
                    return 1;
                }
                return 0;
            }
        });
        enhancer.setCallbacks(new Callback[] { new LazyLoader() {

            @Override
            public Object loadObject() throws Exception {
                return handler;
            }

        }, new InvocationHandler() {

            @Override
            public Object invoke(final Object proxy, final Method method,
                    final Object[] args) throws Throwable {
                return methodHandlers.get(method.getName()).call(args);
            }
        } });
        final Handler newHandler = (Handler) enhancer.create();
        newHandler.init(handler.getContext(), handler.getRequest(), handler
                .getResponse());
        return newHandler;
    }

    @Override
    protected Handler createTarget(final Request request,
            final Response response) {
        try {
            final Handler handler = (Handler) createFromSpringContext(
                    springContext, context);
            handler.init(getContext(), request, response);
            return postCreate(handler);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
