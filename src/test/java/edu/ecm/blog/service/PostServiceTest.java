package edu.ecm.blog.service;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.ecm.blog.domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PostServiceTest {

	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private PostService postService;

	@Test
	public void save() {
		// PostService postService = new PostService();
		// postService.setSessionFactory(sessionFactory);

		Post post = new Post();
		post.setTitle("un post");
		post.setDate(new Date());

		System.out.println(post.getId());
		postService.save(post);
		System.out.println(post.getId());
	}

	@Test
	public void delete() {
		// PostService postService = new PostService();
		// postService.setSessionFactory(sessionFactory);

		Post post = new Post();
		post.setTitle("un post");
		post.setDate(new Date());

		postService.save(post);

		postService.delete(post.getId());

		Session session = sessionFactory.openSession();

		Assert.assertEquals(0, session.createQuery("from Post").list().size());

		session.close();
	}

	@Test
	public void find() {
		// PostService postService = new PostService();
		// postService.setSessionFactory(sessionFactory);
		for (int i = 0; i < 20; i++) {
			Post post = new Post();
			post.setTitle("un post " + i);
			post.setDate(new Date());

			postService.save(post);

		}

		Assert.assertEquals(10, postService.find(0, 10).size());
		Assert.assertEquals(1, postService.find(0, 1).size());
		Assert.assertEquals(10, postService.find(1, 10).size());
	}

	@Test
	public void count() {
		// PostService postService = new PostService();
		// postService.setSessionFactory(sessionFactory);

		Post post = new Post();
		post.setTitle("un post");
		post.setDate(new Date());

		postService.save(post);

		post = new Post();
		post.setTitle("un post");
		post.setDate(new Date());

		postService.save(post);

		Assert.assertEquals(2, postService.count());
	}

	@After
	public void cleanDb() {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();

		session.createQuery("delete from Post").executeUpdate();

		transaction.commit();

	}
	
	@Test
	public void findBySlugTest() {
		Post post = new Post();
		post.setSlug("slug");
		
		postService.save(post);
		Post post2 = (Post) postService.findBySlug(post.getSlug());
		
		Assert.assertEquals(post.getSlug(),post2.getSlug());
		//Assert.assertEquals(null, postService.findBySlug(null));
	}

}
