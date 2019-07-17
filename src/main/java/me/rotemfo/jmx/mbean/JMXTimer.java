package me.rotemfo.jmx.mbean;

import com.codahale.metrics.Timer;
import me.rotemfo.jmx.util.RandomUtils;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.mbean
 * file:    JMXTimer
 * created: 2019-07-16
 * author:  rotem
 */
public class JMXTimer implements JMXTimerMBean {

    private double m1Rate;
    private double m5Rate;
    private double m15Rate;
    private long count;
    private double mean;

    public JMXTimer(final Timer timer) {
        this(timer.getOneMinuteRate(), timer.getFiveMinuteRate(), timer.getFifteenMinuteRate(), timer.getCount(), timer.getMeanRate());
    }

    public JMXTimer(double m1Rate, double m5Rate, double m15Rate, long count, double mean) {
        this.m1Rate = m1Rate;
        this.m5Rate = m5Rate;
        this.m15Rate = m15Rate;
        this.count = count;
        this.mean = mean;
    }

    public void update(Timer timer) {
        this.m1Rate = timer.getOneMinuteRate();
        this.m5Rate = timer.getFiveMinuteRate();
        this.m15Rate = timer.getFifteenMinuteRate();
        this.count = timer.getCount();
        this.mean = timer.getMeanRate();
    }

    @Override
    public double getMedian() {
        return RandomUtils.nextDouble();
    }

    @Override
    public long getMax() {
        return RandomUtils.nextLong();
    }

    @Override
    public long getMin() {
        return RandomUtils.nextLong();
    }

    @Override
    public double getStdDev() {
        return RandomUtils.nextDouble();
    }

    @Override
    public long getCount() {
        return count;
    }
}
