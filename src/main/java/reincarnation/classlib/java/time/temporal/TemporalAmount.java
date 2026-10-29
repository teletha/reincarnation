/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.time.temporal;

import reincarnation.classlib.java.util.List;

/**
 * The Java implementation of {@link java.time.temporal.TemporalAmount} which is transpiled into
 * JavaScript.
 */
public interface TemporalAmount {

    /**
     * Get the amount of the unit.
     * 
     * @param unit A unit.
     * @return An amount.
     */
    long get(TemporalUnit unit);

    /**
     * Get the units.
     * 
     * @return A list of units.
     */
    List<TemporalUnit> getUnits();

    /**
     * Add this amount to the temporal.
     * 
     * @param temporal A temporal.
     * @return A temporal.
     */
    Temporal addTo(Temporal temporal);

    /**
     * Subtract this amount from the temporal.
     * 
     * @param temporal A temporal.
     * @return A temporal.
     */
    Temporal subtractFrom(Temporal temporal);
}
