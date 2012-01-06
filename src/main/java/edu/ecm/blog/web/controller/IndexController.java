package edu.ecm.blog.web.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ecm.blog.domain.Author;
import edu.ecm.blog.domain.Post;
import edu.ecm.blog.service.PostService;

@Controller
public class IndexController {

	@Inject
	private PostService postService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("posts", postService.find(0, 10));
		return "index";
	}

	@RequestMapping("/billet/{slug}")
	public String post(@PathVariable String slug, Model model) {
		model.addAttribute("post", postService.findBySlug(slug));

		return "post";
	}

	public String index() {
		return "index";
	}

	@PostConstruct
	public void bootStrapt() {
		postService.clear();
		if (postService.count() != 0) {
			return;
		}
		for (int i = 0; i < 5; i++) {
			Post post1 = new Post();
			Date date1 = new Date();
			Author auteur1 = new Author();
			auteur1.setName("moi" + i);
			auteur1.setEmail("no@gmauk.foe" + i);
			post1.setDate(date1);
			post1.setTags("tags1, átags42" + i);
			post1.setText("texte" + i + "ou+" + i * 987
					+ "pas");
			post1.setTitle("titreoupas" + i);
			post1.setSlug(post1.getTitle().toLowerCase().replace(" ", "-"));
			postService.save(post1);
		}
	}
}
