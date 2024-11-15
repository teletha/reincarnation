/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.awt.List;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDesc;
import java.time.temporal.ChronoField;
import java.util.AbstractCollection;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import impl.org.controlsfx.collections.MappingChange.Map;

class ReincarnationTest {

    @Test
    void rebirth() {
        Reincarnation.rebirth(Reincarnation.class);
    }

    @Test
    void rebirthCoreClass() {
        // Reincarnation.rebirth(ArrayList.class);
        Reincarnation.rebirth(Properties.class);
    }

    @Test
    void rebirthCoreAbstractClass() {
        Reincarnation.rebirth(AbstractCollection.class);
    }

    @Test
    void rebirthCoreSealedClass() {
        Reincarnation.rebirth(ConstantDesc.class);
        Reincarnation.rebirth(ClassDesc.class);
    }

    @Test
    void rebirthCoreInterface() {
        Reincarnation.rebirth(Map.class);
        Reincarnation.rebirth(List.class);
        Reincarnation.rebirth(Runnable.class);
    }

    @Test
    void rebirthCoreEnum() {
        Reincarnation.rebirth(RetentionPolicy.class);
        Reincarnation.rebirth(ChronoField.class);
    }

    @Test
    void rebirthCoreAnnotation() {
        Reincarnation.rebirth(Retention.class);
        Reincarnation.rebirth(Deprecated.class);
    }
}
