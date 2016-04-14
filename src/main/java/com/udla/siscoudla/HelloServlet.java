package com.udla.siscoudla;

import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws IOException {
    res.setContentType("text/plain");

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

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
        "Demo", properties);

    // Insert a few rows.
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    em.persist(new Greeting("user", new Date(), "Hello!"));
    em.persist(new Greeting("user", new Date(), "Hi!"));
    em.getTransaction().commit();
    em.close();

    // List all the rows.
    em = emf.createEntityManager();
    em.getTransaction().begin();
    List<Greeting> result = em
        .createQuery("SELECT g FROM Greeting g")
        .getResultList();
    for (Greeting g : result) {
      res.getWriter().println(
          g.getId() + " " +
          g.getAuthor() + "(" + g.getDate() + "): " +
          g.getContent());
    }
    em.getTransaction().commit();
    em.close();

  }
}