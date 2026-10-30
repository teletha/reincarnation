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

/**
 * The JavaScript {@code Document}.
 */
@Native
public final class NativeDocument {

    /** The title. */
    private String title = "";

    /** The cookie. */
    private String cookie = "";

    /**
     * Hide constructor.
     */
    private NativeDocument() {
    }

    /**
     * Get the element by the id.
     * 
     * @param id An id.
     * @return An element or null.
     */
    @Native("$this.getElementById($0)")
    public NativeElement getElementById(String id) {
        return null;
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
     * Create the element.
     * 
     * @param tagName A tag name.
     * @return An element.
     */
    @Native("$this.createElement($0)")
    public NativeElement createElement(String tagName) {
        return new NativeElement(tagName);
    }

    /**
     * Create the text node.
     * 
     * @param text A text.
     * @return A text node.
     */
    @Native("$this.createTextNode($0)")
    public Object createTextNode(String text) {
        return null;
    }

    /**
     * Get the body.
     * 
     * @return A body or null.
     */
    @Native("$this.body")
    public NativeElement body() {
        return null;
    }

    /**
     * Get the head.
     * 
     * @return A head or null.
     */
    @Native("$this.head")
    public NativeElement head() {
        return null;
    }

    /**
     * Get the document element.
     * 
     * @return A document element or null.
     */
    @Native("$this.documentElement")
    public NativeElement documentElement() {
        return null;
    }

    /**
     * Get the title.
     * 
     * @return A title.
     */
    @Native("$this.title")
    public String title() {
        return title;
    }

    /**
     * Set the title.
     * 
     * @param title A title.
     */
    @Native("$this.title = $0")
    public void title(String title) {
        this.title = title;
    }

    /**
     * Get the cookie.
     * 
     * @return A cookie.
     */
    @Native("$this.cookie")
    public String cookie() {
        return cookie;
    }

    /**
     * Set the cookie.
     * 
     * @param cookie A cookie.
     */
    @Native("$this.cookie = $0")
    public void cookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * Get the ready state.
     * 
     * @return A ready state.
     */
    @Native("$this.readyState")
    public String readyState() {
        return "complete";
    }

    /**
     * Get the URL.
     * 
     * @return A URL.
     */
    @Native("$this.URL")
    public String url() {
        return "";
    }

    /**
     * Get the base URI.
     * 
     * @return A base URI.
     */
    @Native("$this.baseURI")
    public String baseURI() {
        return "";
    }

    /**
     * Write the text.
     * 
     * @param text A text.
     */
    @Native("$this.write($0)")
    public void write(String text) {
    }

    /**
     * Write the text with a newline.
     * 
     * @param text A text.
     */
    @Native("$this.writeln($0)")
    public void writeln(String text) {
    }

    /**
     * Open the document.
     */
    @Native("$this.open()")
    public void open() {
    }

    /**
     * Close the document.
     */
    @Native("$this.close()")
    public void close() {
    }
}
