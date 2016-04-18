package com.udla.siscoudla.entitymanagerfactory;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.google.appengine.api.utils.SystemProperty;

public abstract class EntityManagerFactoryDAO {

	@PersistenceContext
	static EntityManagerFactory entityManagerFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static EntityManagerFactory obtenerEntityManagerFactory() {
		
	    Map<String, String> properties = new HashMap();
	    if (SystemProperty.environment.value() ==
	          SystemProperty.Environment.Value.Production) {
	      properties.put("javax.persistence.jdbc.driver",
	          "com.mysql.jdbc.GoogleDriver");
	      properties.put("javax.persistence.jdbc.url",
	          System.getProperty("cloudsql.url"));
	    } else {
	      properties.put("javax.persistence.jdbc.driver",
	          "com.mysql.jdbc.Driver");
	      properties.put("javax.persistence.jdbc.url",
	          System.getProperty("cloudsql.url.dev"));
	    }
	    
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory(
					        "siscoudla", properties);
		}
		return entityManagerFactory;
	}
}
