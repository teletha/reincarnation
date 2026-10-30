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
 * The JavaScript {@code MutationObserverInit}.
 */
public final class NativeMutationObserverInit {

    /** Whether the child list is observed. */
    public boolean childList;

    /** Whether the attributes are observed. */
    public boolean attributes;

    /** Whether the character data is observed. */
    public boolean characterData;

    /** Whether the subtree is observed. */
    public boolean subtree;

    /** Whether the old attribute value is observed. */
    public boolean attributeOldValue;

    /** Whether the old character data value is observed. */
    public boolean characterDataOldValue;

    /** The attribute filter. */
    public String[] attributeFilter;

    /**
     * Create the options.
     */
    public NativeMutationObserverInit() {
    }

    /**
     * Create the options.
     * 
     * @param childList Whether the child list is observed.
     * @param attributes Whether the attributes are observed.
     * @param characterData Whether the character data is observed.
     * @param subtree Whether the subtree is observed.
     */
    public NativeMutationObserverInit(boolean childList, boolean attributes, boolean characterData, boolean subtree) {
        this.childList = childList;
        this.attributes = attributes;
        this.characterData = characterData;
        this.subtree = subtree;
    }
}
