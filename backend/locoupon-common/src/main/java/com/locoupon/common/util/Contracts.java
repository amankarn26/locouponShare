/*********************************************************************************************
*
* Copyright © 2020  Drut Technologies, Inc. All Rights Reserved. Proprietary and Confidential
*
* This confidential and proprietary software may be used only as expressly authorized by a
* licensing and specific agreement from DRUT.  In the event that DRUT authorizes any copying,
* distribution or publication of this software, then this notice must be reproduced in full
* on all such authorized copies.
*
**********************************************************************************************
*/

package com.locoupon.common.util;

import java.util.function.Supplier;

public final class Contracts {
    private Contracts() {
    }

    public static void checkState(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    public static void requires(boolean expression, String message) {
        checkArgument(expression, () -> new IllegalArgumentException(message));
    }

    public static <T extends Throwable> void checkArgument(boolean expression, Supplier<T> supplier) throws T {
        if (!expression) {
            throw supplier.get();
        }
    }

    public static <T> T requiresNonNull(T arg, String argName) {
        return requiresNonNull(arg, argName, "cannot be null");
    }

    public static <T> T requiresNonNull(T arg, String argName, String message) {
        if (arg == null) {
            throw new IllegalArgumentException(String.valueOf(argName) + ": " + message);
        }

        return arg;
    }

    public static <T, E extends Throwable> T requiresNonNull(T arg, Supplier<E> supplier) throws E {
        if (arg == null) {
            throw supplier.get();
        }

        return arg;
    }
}
