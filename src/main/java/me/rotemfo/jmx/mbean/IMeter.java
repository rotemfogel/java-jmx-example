package me.rotemfo.jmx.mbean;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.mbean
 * file:    IMeter
 * created: 2019-07-16
 * author:  rotem
 */
public interface IMeter extends ICounter {
    double getFifteenMinuteRate();

    double getFiveMinuteRate();

    double getOneMinuteRate();

    double getMeanRate();
}
