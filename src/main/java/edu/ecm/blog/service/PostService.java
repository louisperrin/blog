package edu.ecm.blog.service;

import java.util.List;

import edu.ecm.blog.domain.Post;

public interface PostService {

	public abstract void save(Post post);

	public abstract void delete(Long id);

	public abstract List find(int pageIndex, int pageSize);

	public abstract int count();

	public abstract Object findBySlug(String slug);
	
	public abstract void clear();

}