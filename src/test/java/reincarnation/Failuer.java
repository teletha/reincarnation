/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @version 2018/10/04 13:52:57
 */
@SuppressWarnings("serial")
public class Failuer extends AssertionError {

    /** The type. */
    private String type;

    /** The reason manager. */
    private List<Object> reasons;

    /**
     * Hide constructor.
     */
    private Failuer() {
    }

    /**
     * Set human-readable error type.
     * 
     * @param type
     * @return
     */
    public static Failuer type(String type) {
        Failuer failuer = new Failuer();
        failuer.type = Objects.requireNonNull(type);
        return failuer;
    }

    /**
     * Add cause of this {@link Failuer}.
     * 
     * @param reason
     * @return
     */
    public Failuer reason(Throwable reason) {
        if (reason != null) {
            addSuppressed(reason);
        }
        return this;
    }

    /**
     * Add reason of this {@link Failuer}.
     * 
     * @param reason
     * @return Chainable API.
     */
    public Failuer reason(Object reason) {
        if (reason != null) {
            if (reasons == null) {
                reasons = new ArrayList();
            }
            reasons.add(reason);
        }
        return this;
    }

    /**
     * Add reason of this {@link Failuer}.
     * 
     * @param reasons
     * @return Chainable API.
     */
    public Failuer reason(Object[] reasons) {
        if (reasons != null) {
            for (Object reason : reasons) {
                reason(reason);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder(type);
        for (Object reason : reasons) {
            builder.append("\r\n").append(reason);
        }
        return builder.toString();
    }
}