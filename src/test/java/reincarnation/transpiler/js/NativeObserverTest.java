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

import js.lang.NativeArray;
import js.lang.NativeMutationObserver;
import js.lang.NativeMutationObserverInit;
import js.lang.NativeObject;
import js.lang.NativeResizeObserver;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the observer APIs.
 */
class NativeObserverTest extends JavascriptCodeVerifier {

    @Test
    void mutationObserver() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                NativeMutationObserver observer = new NativeMutationObserver((records, source) -> {
                });
                NativeObject target = new NativeObject();
                observer.observe(target, new NativeMutationObserverInit(true, true, true, false));
                NativeArray records = observer.takeRecords();
                observer.disconnect();
                return records.length();
            }
        });
    }

    @Test
    void resizeObserver() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                NativeResizeObserver observer = new NativeResizeObserver((entries, source) -> {
                });
                NativeObject target = new NativeObject();
                observer.observe(target);
                observer.observe(target, "content-box");
                observer.unobserve(target);
                observer.disconnect();
                return true;
            }
        });
    }
}
