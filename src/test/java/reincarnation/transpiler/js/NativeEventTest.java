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

import js.lang.NativeElement;
import js.lang.NativeEvent;
import js.lang.NativeEventTarget;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the event APIs.
 */
class NativeEventTest extends JavascriptCodeVerifier {

    @Test
    void event() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeEvent event = new NativeEvent("click");
                boolean before = event.defaultPrevented();
                event.preventDefault();
                event.stopPropagation();
                event.stopImmediatePropagation();
                boolean after = event.defaultPrevented();
                return event.type() + "|" + (event.bubbles() ? "true" : "false") + "|" + (event.cancelable() ? "true" : "false") + "|" + (before ? "true" : "false") + "|" + (after ? "true" : "false");
            }
        });
    }

    @Test
    void eventTarget() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                NativeElement target = new NativeElement("div");
                NativeEventTarget.addEventListener(target, "click", null);
                NativeEventTarget.addEventListener(target, "click", null, false);
                NativeEventTarget.removeEventListener(target, "click", null);
                boolean dispatched = NativeEventTarget.dispatchEvent(target, new NativeEvent("click"));
                return (dispatched ? "true" : "false");
            }
        });
    }
}
