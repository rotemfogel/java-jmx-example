package me.rotemfo.jmx;

import java.util.Timer;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx
 * file:    JMXApplication
 * created: 2019-07-16
 * author:  rotem
 */
public class JMXApplication {
    public static void main(String[] args) {
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.scheduleAtFixedRate(new UpdateMonsterTask(), delay, delay);
    }
}
