package com.tools.util;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class KeyValue {
    public static final String getEnvironmentVariable(final String key) {
        final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        return environmentVariables.getProperty(key);
    }
}
