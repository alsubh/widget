package com.configration.utilities;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.maven.properties.internal.SystemProperties;
import org.jboss.util.property.Property;

import com.arjuna.common.util.propertyservice.PropertiesFactory;
 
public class JNDILookupClass 
{
 
    private static Context initialContext;
 
    private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
 
    public static Context getInitialContext() throws NamingException {
        if (initialContext == null) 
        {
        	PropertiesFactory p =new PropertiesFactory();
    		p.getDefaultProperties();
    		
            Properties properties = new Properties(p.getDefaultProperties());
            properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
           
            initialContext = new InitialContext(properties);
        }
        return initialContext;
    }
    
    public static Context doBeanLookup() {
    	
		Properties jndiProps = new Properties();
    	jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
    	jndiProps.put(Context.PROVIDER_URL,"remote://localhost:4447");
    	// username
    	jndiProps.put(Context.SECURITY_PRINCIPAL, "admin");
    	// password
    	jndiProps.put(Context.SECURITY_CREDENTIALS, "a01575591~");
    	
    	// This is an important property to set if you want to do EJB invocations via the remote-naming project
    	jndiProps.put("jboss.naming.client.ejb.context", true);
    	// create a context passing these properties
    	Context ctx = null;
		try {
			ctx = new InitialContext(jndiProps);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ctx;
    	}
    
	public static Context initializeContext() throws NamingException 
	{
		if (initialContext == null) {
			Properties properties = new Properties();
			properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.put(Context.PROVIDER_URL,"jnp://127.0.0.1:1099");
			properties.put(Context.SECURITY_PRINCIPAL, "anas");
			properties.put(Context.SECURITY_CREDENTIALS, "a01575591~");
			properties.put("jboss.naming.client.ejb.context", true);
			// create a context passing these properties
			initialContext = new InitialContext(properties);
		}
		return initialContext;
	}
	

    public static InitialContext createContext() throws NamingException {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://localhost:1099");
        InitialContext context = new InitialContext(env);
        return context;
    }
}