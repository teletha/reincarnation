/*
 * Copyright (C) 2024 The JAVADNG Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
import { Mimic as $ } from "./mimic.js"
import hljs from "./highlight.js"

// =====================================================
// User Settings
// =====================================================
const
	prefix = import.meta.url.substring(location.protocol.length + location.host.length + 2, import.meta.url.length - 7),
	user = JSON.parse(localStorage.getItem("user")) || {"theme": "light"},
	save = () => localStorage.setItem("user", JSON.stringify(user))
hljs.configure({ignoreUnescapedHTML: true})
history.scrollRestoration = "manual"
	
// =====================================================
// View Mode
// =====================================================
$("html").add(user.theme)
$("#theme").click(e => save($("html").reset(user.theme = user.theme == "light" ? "dark" : "light")))

// =====================================================
// Dynamic Navigation Indicator
// =====================================================
const navi = new IntersectionObserver(e => {
	e.forEach(i => {
		var x = $(`:is(nav,aside) a[href$='#${i.target.id}']`);
		if (i.isIntersecting) {
			x.add("now").each(n => n.scrollIntoView({block: "nearest"}))
		} else {
			x.remove("now")
		}
	})
}, {rootMargin: "-15% 0px -20% 0px", threshold: 0.1})

// =====================================================
// Lightning Fast Viewer
// =====================================================
function FlashMan({ paged, cacheSize = 20, preload = "mouseover", preview = "section", ...previews }) {
	var path = location.pathname, hash = location.hash;
	const cache = new Map(), loading = new Set(), observer = new IntersectionObserver(set => {
		set.filter(x => x.isIntersecting && !x.target.init && (x.target.init = true)).forEach(x => {
			for (let q in previews) x.target.querySelectorAll(q).forEach(e => previews[q](e))
		})
	}, { rootMargin: "60px 0px"});

	// This is the state immediately after a page change has been requested by a user operation.
	function changed(poped) {
		if (path == location.pathname) {
			if (hash != location.hash) {
				hash = location.hash;
				hashed(poped, true);
			}
		} else {
			path = location.pathname;
			hash = location.hash;
			load(path, poped, false);
		}
	}

	// Reads the contents of the specified path into the cache. If it is already cached or currently being read, it will be ignored.
	function load(p, poped, same) {
		if (cache.has(p)) {
			if (path == p) update(cache.get(p), poped, same)
		} else if (!loading.has(p)) {
			loading.add(p)
			fetch(p)
				.then(response => response.text())
				.then(html => {
					loading.delete(p)
					cache.set(p, html)
					if (path == p) update(html, poped, same)
					if (cacheSize < cache.size) cache.delete(cache.keys().next().value)
				})
		}
	}

	function update(text, poped, same) {
		if (poped !== undefined || same !== undefined) $("article").add("fadeout")
		setTimeout(() => {
			if (text) {
				$("article").html(text.substring(text.indexOf(">", text.indexOf("<article")) + 1, text.lastIndexOf("</article>")));
				$("aside").html(text.substring(text.indexOf(">", text.indexOf("<aside")) + 1, text.lastIndexOf("</aside>"))); 
			}
			paged();
			$(preview).each(e => observer.observe(e));
			hashed(poped, same)
			$("article").remove("fadeout")
		}, 300)
	}

	// Scroll into view automatically when hash is changed
	function hashed(poped, same) {
		let h = location.hash?.substring(1)
		window.scrollTo({
			top: !same && poped ? localStorage.getItem(location.pathname) || 0 : h ? document.getElementById(h).offsetTop : 0,
			left: 0,
			behavior: same ? "smooth":"instant"
		})
	}

	// Detect all URL changes
	window.addEventListener("popstate", v => changed(true))
	document.addEventListener("DOMContentLoaded", v => { update(); cache.set(location.pathname, document.documentElement.outerHTML) })
	document.addEventListener("click", v => {
		let e = v.target.closest("a");
		if (e != null && location.origin == e.origin) {
			if (location.href != e.href) {
				history.pushState(null, null, e.href)
				changed()
			}
			v.preventDefault()
		}
	})
	// Detect scroll position
	window.addEventListener("scroll", v => {
		localStorage.setItem(location.pathname, window.scrollY)
	})
	// Preloader
	document.addEventListener(preload, v => {
		let e = v.target, key = e.pathname;
		if (e.tagName === "A" && e.origin == location.origin && key != location.pathname && !cache.has(key)) load(key)
	})
}

FlashMan({
	paged: () => {
		$("#APINavi").each(e => e.dataset.hide = !location.pathname.startsWith(prefix + "api/"));
		$("#DocNavi").each(e => e.dataset.hide = !location.pathname.startsWith(prefix + "doc/"));
		$("#DocNavi>div").each(e => {
			const sub = e.lastElementChild;

			if (location.pathname.endsWith(e.id)) {
				sub.style.height = sub.scrollHeight + "px";
			} else {
				sub.style.height = 0;
			}
		});

		$("#Article section").each(e => navi.observe(e));
	},

	preview: "#Article section",
	/* Enahnce code highlight */
	"pre": e => {
		hljs.highlightElement(e)
		e.lang = e.classList[0].substring(5).toUpperCase()
		$(e).appendTo($("<code>").insertBefore(e)).make("a").attr("aria-label", "Copy this code").click(v => navigator.clipboard.writeText(e.textContent)).svg(prefix + "main.svg#copy")
	},
	/* Enahnce meta icons */
	".perp": e => {
		e.ariaLabel = "Copy the permanent link";
		e.onclick = () => navigator.clipboard.writeText(location.origin + location.pathname + "#" + e.closest("section").id);
	}, ".tweet": e => {
		e.ariaLabel = "Post this article to Twitter";
		e.href = "https://twitter.com/intent/tweet?url=" + encodeURIComponent(location.origin + location.pathname + "#" + e.closest("section").id) + "&text=" + encodeURIComponent(e.closest("header").firstElementChild.textContent);
		e.target = "_blank";
		e.rel = "noreferrer";
	}, ".edit": e => {
		e.ariaLabel = "Edit this article";
		e.target = "_blank";
		e.rel = "noreferrer";
	}
});


/**
 * Selection UI
 */
class Select extends $ {

	/** The assosiated model. */
	model = []

	/** The selected model */
	selected = new Set()

	/** The label builder for each model' */
	label = item => item.toString()

	/** The label builder for the selected values. */
	selectionLabel = selected => selected.map(this.label).join(", ")

	/** The flag to enable multiple selection. */
	multiple

	/** The default message */
	placeholder = "Select Item"

	/**
	 * Initialize by user configuration.
	 */
	constructor(config) {
		super("<o-select/>")
		Object.assign(this, config)

		this.set({ disabled: !this.model.length })
			.make("view").click(e => this.find("ol").has("active") ? this.close() : this.open())
			.make("now").text(this.placeholder).parent()
			.svg(prefix + "main.svg#chevron").parent().parent()
			.svg(prefix + "main.svg#x").click(e => this.deselect())
		this
			.make("ol").click(e => this.select(e.target.model, $(e.target)), { where: "li" })
			.make("li", this.model, (item, li) => li.text(this.label(item)))

		this.closer = e => {
			if (!this.nodes[0].contains(e.target)) this.close()
		}
	}

	/**
	 * Initialize by user configuration.
	 */
	select(item, dom) {
		if (this.multiple) {
			dom.toggle("select", () => this.selected.add(item), () => this.selected.delete(item))
		} else {
			if (this.selected.has(item)) {
				this.close()
				return
			}
			this.deselect(true)
			dom.add("select")
			this.selected.add(item)
		}
		this.update()
	}

	/**
	 * Initialize by user configuration.
	 */
	deselect(skipUpdate) {
		this.find("li").remove("select")
		this.selected.clear()
		this.close()

		if (!skipUpdate) this.update()
	}

	/**
	 * Initialize by user configuration.
	 */
	update() {
		this.find("now").set({ select: this.selected.size }).text(this.selectionLabel([...this.selected.keys()]) || this.placeholder)
		this.find(".x").set({ active: this.selected.size })
		this.dispatch("change")
	}

	/**
	 * Initialize by user configuration.
	 */
	open() {
		this.find("ol, .chevron").add("active")
		$(document).click(this.closer)
	}

	/**
	 * Initialize by user configuration.
	 */
	close() {
		this.find("ol, .chevron").remove("active")
		$(document).off("click", this.closer)
	}
}

/**
 * Selection UI
 */
class APITree extends $ {

	/**
	 * Initialize by user configuration.
	 */
	constructor(items) {
		super("<o-tree>")

		this.moduleFilter = new Select({ placeholder: "Select Module", model: root.modules })
		this.packageFilter = new Select({ placeholder: "Select Package", model: root.packages })
		this.typeFilter = new Select({ placeholder: "Select Type", multiple: true, model: ['Interface', 'Functional', 'AbstractClass', 'Class', 'Enum', 'Record', 'Annotation', 'Exception'] })
		this.nameFilter = $("<input>").id("NameFilter").placeholder("Search by Name")

		this.id("APINavi").change(e => this.update()).input(e => this.update())
			.append(this.moduleFilter)
			.append(this.packageFilter)
			.append(this.typeFilter)
			.append(this.nameFilter)
			.make("div").add("tree")
			.make("dl", items.packages, (pack, dl) => {
				dl.id(pack).make("dt").click(e => dl.toggle("show"))
					.make("code").text(pack)
					
				dl.make("dd", items.types.filter(type => type.packageName == pack), (type, dd) => {
					dd.add(type.type)
						.make("code").make("a").href(prefix + "api/" + type.packageName + "." + type.name + ".html").text(type.name)
				})
			})
	}

	update() {
		let filter = item => {
			if (this.typeFilter.selected.size != 0 && !this.typeFilter.selected.has(item.type)) return false
			if (this.packageFilter.selected.size != 0 && !this.packageFilter.selected.has(item.packageName)) return false
			if (this.nameFilter.value() != "" && (item.packageName + "." + item.name).toLowerCase().indexOf(this.nameFilter.value().toLowerCase()) == -1) return false
			return true
		}

		this.find("dl").set({ expand: this.typeFilter.selected.size != 0 || this.packageFilter.selected.size != 0 || this.nameFilter.value() != "" })
		this.find("dd").each(e => {
			$(e).show(filter(e.model))
		})
	}
}

$("body>nav")
	.append(new APITree(root))
	.make("div").id("DocNavi")
	.make("div", root.docs, (doc, div) => {
		div.add("doc").id(doc.path)
			.make("a").href(doc.path).text(doc.title).parent()
			.make("div").add("sub")
			.make("a", doc.subs, (sub, li) => {
				li.href(sub.path).text(sub.title).parent()
				.make("a", sub.subs, (foot, a) => {
					a.href(foot.path).add("foot").text(foot.title)
				})
			})
	})

// =====================================================
// Live Reload
// =====================================================
if (location.hostname == "localhost") setInterval(() => fetch("http://localhost:9321/live").then(res => {
	if (res.status == 200) location.reload();
}), 3000);