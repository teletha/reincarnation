/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util.concurrent;

/**
 * The Java implementation of {@link java.util.concurrent.TransferQueue} which is transpiled into
 * JavaScript.
 */
public interface TransferQueue<E> extends BlockingQueue<E> {

    /**
     * Transfer the value.
     * 
     * @param value A value.
     */
    default void transfer(Object value) {
        put(value);
    }

    /**
     * Try to transfer the value.
     * 
     * @param value A value.
     * @return A result.
     */
    default boolean tryTransfer(Object value) {
        return offer(value);
    }

    /**
     * Check whether a consumer is waiting.
     * 
     * @return A result.
     */
    default boolean hasWaitingConsumer() {
        return false;
    }

    /**
     * Get the number of waiting consumers.
     * 
     * @return A number.
     */
    default int getWaitingConsumerCount() {
        return 0;
    }
}
