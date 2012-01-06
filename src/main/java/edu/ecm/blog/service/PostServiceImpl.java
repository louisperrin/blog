package edu.ecm.blog.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ecm.blog.domain.Post;

@Service
public class PostServiceImpl implements PostService {

	@Inject
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ecm.blog.service.PostService#save(edu.ecm.blog.domain.Post)
	 */
	@Override
	@Transactional
	public void save(Post post) {

		Session session = sessionFactory.getCurrentSession();

		session.save(post);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ecm.blog.service.PostService#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();

		Post post = (Post) session.get(Post.class, id);

		session.delete(post);

	}

	/*
	 * public SessionFactory getSessionFactory() { return sessionFactory; }
	 * 
	 * public void setSessionFactory(SessionFactory sessionFactory) {
	 * this.sessionFactory = sessionFactory; }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ecm.blog.service.PostService#find(int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List find(int pageIndex, int pageSize) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Post.class);
		criteria.setFirstResult(pageIndex * pageSize);
		criteria.setMaxResults(pageSize);
		List<Post> posts = criteria.list();
		return posts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ecm.blog.service.PostService#count()
	 */
	@Override
	@Transactional(readOnly = true)
	public int count() {
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("select count(*) from Post");

		long l = (Long) query.list().get(0);
		return (int) l;
	}

	@Override
	@Transactional
	public Object findBySlug(String slug) {
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("from Post where slug = :slug");
		query.setString("slug", slug);

		return (Post) query.uniqueResult();
	}

	@Override
	@Transactional
	public void clear() {
		sessionFactory.getCurrentSession().createQuery("delete from Post")
				.executeUpdate();
	}

}
