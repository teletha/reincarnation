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
import js.lang.NativeNodeList;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for {@link NativeElement}.
 */
class NativeElementTest extends JavascriptCodeVerifier {

    @Test
    void element() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("div");
                element.id("main");
                element.className("box");
                element.textContent("hello");
                element.innerHTML("<b>world</b>");
                element.innerText("text");
                element.setAttribute("data-x", "1");
                element.setAttribute("title", "test");
                boolean hasAttribute = element.hasAttribute("data-x");
                element.removeAttribute("title");
                boolean removed = element.hasAttribute("title");
                NativeElement child = new NativeElement("span");
                boolean appended = element.appendChild(child) == child;
                boolean removedChild = element.removeChild(child) == child;
                element.addEventListener("click", null);
                element.removeEventListener("click", null);
                element.focus();
                element.blur();
                element.click();
                element.scrollIntoView();
                return element.tagName() + "|" + element.id() + "|" + element.className() + "|" + element.textContent() + "|" + element.innerHTML() + "|" + element.innerText() + "|" + element.getAttribute("data-x") + "|" + (hasAttribute ? "true" : "false") + "|" + (removed ? "true" : "false") + "|" + (appended ? "true" : "false") + "|" + (removedChild ? "true" : "false") + "|" + (element.querySelector(".child") == null ? "null" : "found") + "|" + (element.matches(".box") ? "true" : "false") + "|" + (element.dispatchEvent(null) ? "true" : "false");
            }
        });
    }

    @Test
    void createElement() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("section");
                return element.tagName();
            }
        });
    }

    @Test
    void domTokenList() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeDOMTokenList list = new NativeDOMTokenList();
                list.add("a", "b");
                list.add("a");
                boolean contains = list.contains("a");
                list.remove("b");
                boolean toggle = list.toggle("c");
                boolean toggleOff = list.toggle("a");
                boolean replace = list.replace("c", "d");
                list.value("x y");
                return list.length() + "|" + (contains ? "true" : "false") + "|" + (toggle ? "true" : "false") + "|" + (toggleOff ? "true" : "false") + "|" + (replace ? "true" : "false") + "|" + list + "|" + list.value();
            }
        });
    }

    @Test
    void nodeList() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeNodeList list = new NativeNodeList(new java.lang.Object[] {"a", "b"});
                return list.length() + "|" + list.item(0) + "|" + list.get(1);
            }
        });
    }

    @Test
    void elementClassList() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement element = new NativeElement("div");
                NativeDOMTokenList list = element.classList();
                list.add("x", "y");
                return list.length() + "|" + (list.contains("x") ? "true" : "false") + "|" + list;
            }
        });
    }
}
