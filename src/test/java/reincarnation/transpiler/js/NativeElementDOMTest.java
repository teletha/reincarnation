/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.js;

import js.lang.NativeDOMTokenList;
import js.lang.NativeElement;
import js.lang.NativeEvent;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the DOM APIs with a real DOM implementation installed.
 * <p>
 * The test is skipped unless happy-dom is available. See
 * {@link reincarnation.transpiler.JavetSupport} for the configuration.
 * </p>
 */
class NativeElementDOMTest extends JavascriptCodeVerifier {

    @Test
    void attributes() throws Exception {
        verifyWithDOM(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("div");
                element.id("main");
                element.textContent("hello");
                NativeElement html = new NativeElement("div");
                html.innerHTML("world");
                element.setAttribute("data-x", "1");
                boolean has = element.hasAttribute("data-x");
                element.removeAttribute("data-x");
                boolean removed = element.hasAttribute("data-x");
                return element.id() + "|" + element.textContent() + "|" + html.innerHTML() + "|" + (has ? "true" : "false") + "|" + (removed ? "true" : "false") + "|" + element.getAttribute("data-x");
            }
        });
    }

    @Test
    void classList() throws Exception {
        verifyWithDOM(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("div");
                NativeDOMTokenList list = element.classList();
                list.add("a", "b");
                list.remove("a");
                boolean toggle = list.toggle("c");
                return list.length() + "|" + (list.contains("a") ? "true" : "false") + "|" + (list.contains("b") ? "true" : "false") + "|" + (toggle ? "true" : "false") + "|" + list.item(0) + "|" + list + "|" + list.value();
            }
        });
    }

    @Test
    void children() throws Exception {
        verifyWithDOM(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("div");
                NativeElement child = new NativeElement("span");
                boolean appended = element.appendChild(child) == child;
                int children = element.children().length();
                int childNodes = element.childNodes().length();
                boolean item = element.children().item(0) == child;
                boolean removed = element.removeChild(child) == child;
                int after = element.children().length();
                return (appended ? "true" : "false") + "|" + children + "|" + childNodes + "|" + (item ? "true" : "false") + "|" + (removed ? "true" : "false") + "|" + after;
            }
        });
    }

    @Test
    void event() throws Exception {
        verifyWithDOM(new TestCode.Text() {

            @Override
            public String run() {
                NativeEvent event = new NativeEvent("click");
                boolean bubbles = event.bubbles();
                boolean cancelable = event.cancelable();
                event.preventDefault();
                boolean prevented = event.defaultPrevented();
                return event.type() + "|" + (bubbles ? "true" : "false") + "|" + (cancelable ? "true" : "false") + "|" + (prevented ? "true" : "false");
            }
        });
    }
}
