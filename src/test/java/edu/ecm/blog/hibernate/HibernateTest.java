package edu.ecm.blog.hibernate;

import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ecm.blog.domain.Author;
import edu.ecm.blog.domain.Post;

public class HibernateTest {

	private SessionFactory sessionFactory;

	@Before
	public void createSessionFactory() {
		Configuration configuration = new Configuration();

		configuration.setProperty("hibernate.dialect",
				"org.hibernate.dialect.DerbyDialect");
		configuration.setProperty("hibernate.connection.url",
				"jdbc:derby:target/testdb;create=true");
		configuration.setProperty("hibernate.connection.driver_class",
				"org.apache.derby.jdbc.EmbeddedDriver");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

		configuration.addAnnotatedClass(Author.class);
		configuration.addAnnotatedClass(Post.class);

		this.sessionFactory = configuration.buildSessionFactory();

	}

	@Test
	public void saveAuthor() {
		Author author = new Author();

		author.setName("Harpo Marx");
		author.setEmail("harpo.marx@gmail.com");

		Session session = sessionFactory.openSession();

		session.save(author);

		session.close();
	}
	
	@After
	public void cleanDb() {
	    Session session = sessionFactory.openSession();

	    Transaction transaction = session.beginTransaction();

	    session.createQuery("delete from Author").executeUpdate();

	    transaction.commit();

	    session.close();

	    sessionFactory.close();
	}

}
