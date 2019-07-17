package me.rotemfo.jmx.util;

import java.util.Random;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.util
 * file:    RandomUtils
 * created: 2019-07-16
 * author:  rotem
 */
public abstract class RandomUtils {
    private static final Random random = new Random();

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(final int bound) {
        return random.nextInt(bound);
    }

    public static long nextLong() {
        return random.nextLong();
    }
}
