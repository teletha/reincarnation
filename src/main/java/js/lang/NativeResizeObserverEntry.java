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
 * The JavaScript {@code ResizeObserverEntry}.
 */
public final class NativeResizeObserverEntry {

    /** The target. */
    public Object target;

    /** The content rectangle. */
    public Object contentRect;

    /** The border box size. */
    public NativeArray borderBoxSize;

    /** The content box size. */
    public NativeArray contentBoxSize;

    /** The device pixel content box size. */
    public NativeArray devicePixelContentBoxSize;
}
