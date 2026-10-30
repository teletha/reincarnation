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

import js.lang.NativeStorage;
import js.lang.NativeURL;
import js.lang.NativeURLSearchParams;
import js.lang.NativeWindow;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the web native APIs.
 */
class NativeWebTest extends JavascriptCodeVerifier {

    @Test
    void url() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeURL url = new NativeURL("https://user:pass@example.com:8080/a/b?x=1&y=2#frag");
                return url.href() + "|" + url.protocol() + "|" + url.host() + "|" + url.hostname() + "|" + url.port() + "|" + url.pathname() + "|" + url.search() + "|" + url.hash() + "|" + url.origin() + "|" + url.username() + "|" + url.password();
            }
        });
    }

    @Test
    void urlDefaultPort() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NativeURL("https://example.com:443/a").port() + "|" + new NativeURL("http://example.com:80/a").port() + "|" + new NativeURL("https://example.com").pathname();
            }
        });
    }

    @Test
    void urlRelative() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new NativeURL("../c", "https://example.com/a/b/").href();
            }
        });
    }

    @Test
    void urlUpdate() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeURL url = new NativeURL("https://example.com/a");
                url.pathname("/b");
                url.search("?x=1");
                url.hash("#f");
                return url.href();
            }
        });
    }

    @Test
    void searchParams() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeURL url = new NativeURL("https://example.com/?x=1&x=2&y=3");
                NativeURLSearchParams params = url.searchParams();
                return params.get("x") + "|" + params.getAll("x").length + "|" + params.has("y") + "|" + params.size() + "|" + params.toString();
            }
        });
    }

    @Test
    void storage() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeStorage storage = NativeStorage.local();
                storage.clear();
                storage.setItem("a", "1");
                storage.setItem("b", "2");
                String text = storage.getItem("a") + "|" + storage.length() + "|" + storage.key(0) + "|" + storage.key(1);
                storage.removeItem("a");
                text += "|" + storage.length() + "|" + storage.getItem("a");
                storage.clear();
                return text + "|" + storage.length();
            }
        });
    }

    @Test
    void sessionStorage() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeStorage storage = NativeWindow.sessionStorage;
                storage.clear();
                storage.setItem("key", "value");
                return storage.getItem("key") + "|" + storage.length();
            }
        });
    }

    @Test
    void window() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return (NativeWindow.document == null ? "null" : "document") + "|" + NativeWindow.innerWidth + "|" + NativeWindow.innerHeight + "|" + (int) NativeWindow.devicePixelRatio + "|" + (int) NativeWindow.scrollX + "|" + (int) NativeWindow.scrollY;
            }
        });
    }

    @Test
    void global() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeWindow.global() != null;
            }
        });
    }

    @Test
    void windowFunctions() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeWindow.alert("test");
                boolean confirm = NativeWindow.confirm("test");
                String prompt = NativeWindow.prompt("test");
                boolean open = NativeWindow.open("about:blank", "_blank", "") == null;
                NativeWindow.close();
                return (confirm ? "true" : "false") + "|" + prompt + "|" + (open ? "null" : "window");
            }
        });
    }

    @Test
    void documentAvailability() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return NativeWindow.document == null;
            }
        });
    }
}
