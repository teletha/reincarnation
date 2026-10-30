/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The JavaScript {@code Element}.
 */
@Native
public final class NativeElement {

    /** The tag name. */
    private String tagName;

    /** The id. */
    private String id = "";

    /** The class name. */
    private String className = "";

    /** The text content. */
    private String textContent = "";

    /** The inner HTML. */
    private String innerHTML = "";

    /** The inner text. */
    private String innerText = "";

    /** The attributes. */
    private final Map<String, String> attributes = new LinkedHashMap();

    /** The children. */
    private final List<Object> children = new ArrayList();

    /** The class list. */
    private final NativeDOMTokenList classList = new NativeDOMTokenList();

    /**
     * Create the element.
     * 
     * @param tagName A tag name.
     */
    @Native("typeof document === 'undefined' ? ({tagName: $0, id: '', className: '', textContent: '', innerHTML: '', innerText: '', classList: (() => { const values = []; return {get length() { return values.length; }, item: function(index) { return values[index] || null; }, contains: function(token) { return values.indexOf(token) >= 0; }, add: function() { for (let i = 0; i < arguments.length; i++) if (values.indexOf(arguments[i]) < 0) values.push(arguments[i]); }, remove: function() { for (let i = 0; i < arguments.length; i++) { const index = values.indexOf(arguments[i]); if (index >= 0) values.splice(index, 1); } }, toggle: function(token, force) { const has = values.indexOf(token) >= 0; const add = force === undefined ? !has : force; if (add && !has) values.push(token); else if (!add && has) values.splice(values.indexOf(token), 1); return add; }, replace: function(oldToken, newToken) { const index = values.indexOf(oldToken); if (index < 0 || values.indexOf(newToken) >= 0) return false; values[index] = newToken; return true; }, get value() { return values.join(' '); }, set value(text) { values.length = 0; const parts = String(text).split(' '); for (const token of parts) { if (token && values.indexOf(token) < 0) values.push(token); } }, toString: function() { return values.join(' '); }}; })(), _attributes: {}, getAttribute: function(name) { return this._attributes[name] === undefined ? null : this._attributes[name]; }, setAttribute: function(name, value) { this._attributes[name] = String(value); }, removeAttribute: function(name) { delete this._attributes[name]; }, hasAttribute: function(name) { return this._attributes[name] !== undefined; }, getAttributeNames: function() { return Object.keys(this._attributes); }, appendChild: function(child) { return child; }, removeChild: function(child) { return child; }, remove: function() {}, replaceChildren: function() {}, querySelector: function(selector) { return null; }, querySelectorAll: function(selector) { return []; }, closest: function(selector) { return null; }, matches: function(selector) { return false; }, addEventListener: function() {}, removeEventListener: function() {}, dispatchEvent: function(event) { return false; }, focus: function() {}, blur: function() {}, click: function() {}, scrollIntoView: function() {}, getBoundingClientRect: function() { return {x: 0, y: 0, width: 0, height: 0, top: 0, right: 0, bottom: 0, left: 0}; }}) : document.createElement($0)")
    public NativeElement(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Get the tag name.
     * 
     * @return A tag name.
     */
    @Native("$this.tagName")
    public String tagName() {
        return tagName;
    }

    /**
     * Get the id.
     * 
     * @return An id.
     */
    @Native("$this.id")
    public String id() {
        return id;
    }

    /**
     * Set the id.
     * 
     * @param id An id.
     */
    @Native("$this.id = $0")
    public void id(String id) {
        this.id = id;
    }

    /**
     * Get the class name.
     * 
     * @return A class name.
     */
    @Native("$this.className")
    public String className() {
        return className;
    }

    /**
     * Set the class name.
     * 
     * @param className A class name.
     */
    @Native("$this.className = $0")
    public void className(String className) {
        this.className = className;
    }

    /**
     * Get the text content.
     * 
     * @return A text content.
     */
    @Native("$this.textContent")
    public String textContent() {
        return textContent;
    }

    /**
     * Set the text content.
     * 
     * @param textContent A text content.
     */
    @Native("$this.textContent = $0")
    public void textContent(String textContent) {
        this.textContent = textContent;
    }

    /**
     * Get the inner HTML.
     * 
     * @return An inner HTML.
     */
    @Native("$this.innerHTML")
    public String innerHTML() {
        return innerHTML;
    }

    /**
     * Set the inner HTML.
     * 
     * @param innerHTML An inner HTML.
     */
    @Native("$this.innerHTML = $0")
    public void innerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    /**
     * Get the inner text.
     * 
     * @return An inner text.
     */
    @Native("$this.innerText")
    public String innerText() {
        return innerText;
    }

    /**
     * Set the inner text.
     * 
     * @param innerText An inner text.
     */
    @Native("$this.innerText = $0")
    public void innerText(String innerText) {
        this.innerText = innerText;
    }

    /**
     * Get the outer HTML.
     * 
     * @return An outer HTML.
     */
    @Native("$this.outerHTML")
    public String outerHTML() {
        return innerHTML;
    }

    /**
     * Get the attribute.
     * 
     * @param name A name.
     * @return A value or null.
     */
    @Native("$this.getAttribute($0)")
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Set the attribute.
     * 
     * @param name A name.
     * @param value A value.
     */
    @Native("$this.setAttribute($0, $1)")
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    /**
     * Remove the attribute.
     * 
     * @param name A name.
     */
    @Native("$this.removeAttribute($0)")
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    /**
     * Check whether the attribute exists.
     * 
     * @param name A name.
     * @return A result.
     */
    @Native("$this.hasAttribute($0)")
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    /**
     * Get the attribute names.
     * 
     * @return Names.
     */
    @Native("$this.getAttributeNames()")
    public NativeArray getAttributeNames() {
        NativeArray array = new NativeArray();
        for (String name : attributes.keySet()) {
            array.push(name);
        }
        return array;
    }

    /**
     * Get the dataset.
     * 
     * @return A dataset.
     */
    @Native("$this.dataset")
    public Object dataset() {
        return null;
    }

    /**
     * Get the style.
     * 
     * @return A style.
     */
    @Native("$this.style")
    public Object style() {
        return null;
    }

    /**
     * Get the class list.
     * 
     * @return A class list.
     */
    @Native("$this.classList")
    public NativeDOMTokenList classList() {
        return classList;
    }

    /**
     * Get the parent element.
     * 
     * @return A parent element or null.
     */
    @Native("$this.parentElement")
    public NativeElement parentElement() {
        return null;
    }

    /**
     * Get the first element child.
     * 
     * @return An element or null.
     */
    @Native("$this.firstElementChild")
    public NativeElement firstElementChild() {
        return null;
    }

    /**
     * Get the last element child.
     * 
     * @return An element or null.
     */
    @Native("$this.lastElementChild")
    public NativeElement lastElementChild() {
        return null;
    }

    /**
     * Get the next element sibling.
     * 
     * @return An element or null.
     */
    @Native("$this.nextElementSibling")
    public NativeElement nextElementSibling() {
        return null;
    }

    /**
     * Get the previous element sibling.
     * 
     * @return An element or null.
     */
    @Native("$this.previousElementSibling")
    public NativeElement previousElementSibling() {
        return null;
    }

    /**
     * Get the children.
     * 
     * @return Children.
     */
    @Native("$this.children")
    public NativeNodeList children() {
        return new NativeNodeList(children.toArray());
    }

    /**
     * Get the child nodes.
     * 
     * @return Child nodes.
     */
    @Native("$this.childNodes")
    public NativeNodeList childNodes() {
        return new NativeNodeList(children.toArray());
    }

    /**
     * Append the child.
     * 
     * @param child A child.
     * @return A child.
     */
    @Native("$this.appendChild($0)")
    public Object appendChild(Object child) {
        children.add(child);
        return child;
    }

    /**
     * Remove the child.
     * 
     * @param child A child.
     * @return A child.
     */
    @Native("$this.removeChild($0)")
    public Object removeChild(Object child) {
        children.remove(child);
        return child;
    }

    /**
     * Remove this element.
     */
    @Native("$this.remove()")
    public void remove() {
    }

    /**
     * Replace the children.
     * 
     * @param children Children.
     */
    @Native("$this.replaceChildren(...$0)")
    public void replaceChildren(Object... children) {
        this.children.clear();
        for (Object child : children) {
            this.children.add(child);
        }
    }

    /**
     * Get the first matched element.
     * 
     * @param selector A selector.
     * @return An element or null.
     */
    @Native("$this.querySelector($0)")
    public NativeElement querySelector(String selector) {
        return null;
    }

    /**
     * Get the matched elements.
     * 
     * @param selector A selector.
     * @return Elements.
     */
    @Native("$this.querySelectorAll($0)")
    public NativeNodeList querySelectorAll(String selector) {
        return new NativeNodeList();
    }

    /**
     * Get the closest element.
     * 
     * @param selector A selector.
     * @return An element or null.
     */
    @Native("$this.closest($0)")
    public NativeElement closest(String selector) {
        return null;
    }

    /**
     * Check whether this element matches the selector.
     * 
     * @param selector A selector.
     * @return A result.
     */
    @Native("$this.matches($0)")
    public boolean matches(String selector) {
        return false;
    }

    /**
     * Add the event listener.
     * 
     * @param type A type.
     * @param listener A listener.
     */
    @Native("$this.addEventListener($0, $1)")
    public void addEventListener(String type, Object listener) {
    }

    /**
     * Add the event listener.
     * 
     * @param type A type.
     * @param listener A listener.
     * @param options Options.
     */
    @Native("$this.addEventListener($0, $1, $2)")
    public void addEventListener(String type, Object listener, boolean options) {
    }

    /**
     * Remove the event listener.
     * 
     * @param type A type.
     * @param listener A listener.
     */
    @Native("$this.removeEventListener($0, $1)")
    public void removeEventListener(String type, Object listener) {
    }

    /**
     * Dispatch the event.
     * 
     * @param event An event.
     * @return A result.
     */
    @Native("$this.dispatchEvent($0)")
    public boolean dispatchEvent(NativeEvent event) {
        return false;
    }

    /**
     * Focus this element.
     */
    @Native("$this.focus()")
    public void focus() {
    }

    /**
     * Blur this element.
     */
    @Native("$this.blur()")
    public void blur() {
    }

    /**
     * Click this element.
     */
    @Native("$this.click()")
    public void click() {
    }

    /**
     * Scroll this element into view.
     */
    @Native("$this.scrollIntoView()")
    public void scrollIntoView() {
    }

    /**
     * Get the bounding client rectangle.
     * 
     * @return A rectangle.
     */
    @Native("$this.getBoundingClientRect()")
    public Object getBoundingClientRect() {
        return null;
    }
}
