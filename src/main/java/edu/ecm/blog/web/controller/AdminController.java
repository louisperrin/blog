package edu.ecm.blog.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ecm.blog.domain.Post;
import edu.ecm.blog.service.PostService;

@Controller
public class AdminController {

	@Inject
	private PostService postService;

	@RequestMapping("/admin/posts")
	public String index(Model model) {
		model.addAttribute("posts", postService.find(0, postService.count()));
		return "admin/posts";
	}

	@RequestMapping("/admin/post/{slug}")
	public String post(@PathVariable String slug, Model model) {
		model.addAttribute("post", postService.findBySlug(slug));
		return "admin/post";
	}

	@RequestMapping("/admin/post")
	public String post(Model model) {
		// Injection d'un post vierge dans le modele:
		model.addAttribute("post", new Post());
		return "admin/postNew";
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	@RequestMapping(value = "/admin/post", method = RequestMethod.POST)
	public String post(@ModelAttribute("post") Post post,
			BindingResult bindingResult, Model model) {
		if (StringUtils.isEmpty(post.getTitle())) {
			bindingResult.rejectValue("title", "field.empty",
					"Le titre est obligatoire");

			return "admin/post";
		}

		postService.save(post);

		return "redirect:/admin/posts";
	}

}
