package me.rotemfo.jmx.mbean;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.mbean
 * file:    ITimer
 * created: 2019-07-16
 * author:  rotem
 */
public interface ITimer extends ICounter {
    double getMedian();

    long getMax();

    long getMin();

    double getStdDev();
}
