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
 * The JavaScript {@code MutationRecord}.
 */
public final class NativeMutationRecord {

    /** The type. */
    public String type;

    /** The target. */
    public Object target;

    /** The added nodes. */
    public NativeArray addedNodes;

    /** The removed nodes. */
    public NativeArray removedNodes;

    /** The previous sibling. */
    public Object previousSibling;

    /** The next sibling. */
    public Object nextSibling;

    /** The attribute name. */
    public String attributeName;

    /** The attribute namespace. */
    public String attributeNamespace;

    /** The old value. */
    public String oldValue;
}
