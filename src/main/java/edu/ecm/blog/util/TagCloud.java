package edu.ecm.blog.util;

import java.util.ArrayList;
import java.util.List;

public class TagCloud {

	private List<String> tags = new ArrayList<String>();

	/***********************************************************/

	public void add(String tag) {
		if (isValid(tag)) {
			return;
		}
		this.tags.add(tag);
	}

	public int size() {
		return this.tags.size();
	}

	public void add(String... tags) {
		if (tags == null) {
			return;
		}
		for (String tag : tags) {
			this.add(tag);

		}
	}

	private boolean isValid(String tag) {
		return (tag == null || this.tags.contains(tag) || tag == "");
	}

	public boolean contains(String tag) {
		return this.tags.contains(tag);
	}

	public void top(int i) {
		this.tags = this.tags.subList(i-1, this.tags.size());
	}
}
