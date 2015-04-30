package com.configration.utilities;

import javax.naming.Context;
import javax.naming.NamingException;

import com.entity.busniess.PileRemote;
import com.widget.businesslogic.GadgetPile;
import com.widget.businesslogic.GooPile;
import com.widget.businesslogic.WidgetPile;

public class LookingUp {
	
	public LookingUp() {}
	
	 public static PileRemote doLookup(String widgetName) {
	        Context context = null;
	        PileRemote bean = null;
	        try {
	            // 1. Obtaining Context
	            //context = JNDILookupClass.getInitialContext();
	        	context= JNDILookupClass.doBeanLookup();
	        	
				System.out.println("---------Context-------------");
		    	for(Object o : context.getEnvironment().entrySet())
		    	{
		    		System.out.println(o);
		    	}
		    	System.out.println("---------Context-------------");
	        	
	            // 2. Generate JNDI Lookup name
	            String lookupName = getLookupName(widgetName);
	            // 3. Lookup and cast
	            bean = (PileRemote) context.lookup(lookupName);
	            System.out.println("The bean is " + bean);
		            
	        } catch (NamingException e) {
	            System.out.println( "Err Message : "+ e.getMessage());
	            System.out.println("Err Cause " + e.getCause());
	            System.out.println("Err Explaination "+e.getExplanation());
	        	e.printStackTrace();
	        }
	        return bean;
	    }
	 
	    public static String getLookupName(String widgetTypeBean) {
	        /*The app name is the EAR name of the deployed EJB without .ear 
	        suffix. Since we haven't deployed the application as a .ear, the app 
	        name for us will be an empty string */
	        String appName = "";
	 
	        /* The module name is the JAR name of the deployed EJB without the 
	        .jar suffix.*/
	        String moduleName = "widgetEJB";
	 
	        /* AS7 allows each deployment to have an (optional) distinct name. 
	        This can be an empty string if distinct name is not specified.*/
	        String distinctName = "";
	 
	        // The EJB bean implementation class name
	        String beanName = "";
	        if(widgetTypeBean == "goo")
	        {
	        	beanName = GooPile.class.getSimpleName();
	        }
	        else if(widgetTypeBean == "widget")
	        {
	        	beanName = WidgetPile.class.getSimpleName();
	        }
	        else if(widgetTypeBean == "gadget")
	        {
	        	beanName = GadgetPile.class.getSimpleName();	
	        }
	 
	        // Fully qualified remote interface name
	        final String interfaceName = PileRemote.class.getName();
	        
	        String name = lookupName(appName, moduleName, distinctName, beanName, interfaceName);
	        System.out.println("Name of the bean is  " + name);
	        return name;
	    }

		private static String lookupName(String appName, String moduleName, String distinctName, String beanName, final String interfaceName) 
		{
			//java:jboss/exported/MyBankLogicEAR/MyBankLogic/Account!main.IAccount
			
			// Create a look up string name
	        String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName    + "/" + beanName + "!" + interfaceName;
	        System.out.println("lookup Name = " + name);
			return name;
		}

}
