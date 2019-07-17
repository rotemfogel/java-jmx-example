package me.rotemfo.jmx.mbean;

import com.codahale.metrics.Meter;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.mbean
 * file:    JMXMeter
 * created: 2019-07-16
 * author:  rotem
 */
public class JMXMeter implements JMXMeterMBean {

    private double m1Rate;
    private double m5Rate;
    private double m15Rate;
    private long count;
    private double mean;

    public JMXMeter(final Meter meter) {
        this(meter.getOneMinuteRate(), meter.getFiveMinuteRate(), meter.getFifteenMinuteRate(), meter.getCount(), meter.getMeanRate());
    }

    public JMXMeter(double m1Rate, double m5Rate, double m15Rate, long count, double mean) {
        this.m1Rate = m1Rate;
        this.m5Rate = m5Rate;
        this.m15Rate = m15Rate;
        this.count = count;
        this.mean = mean;
    }

    public void update(final Meter meter) {
        this.m1Rate = meter.getOneMinuteRate();
        this.m5Rate = meter.getFiveMinuteRate();
        this.m15Rate = meter.getFifteenMinuteRate();
        this.count = meter.getCount();
        this.mean = meter.getMeanRate();
    }

    @Override
    public double getFifteenMinuteRate() {
        return m15Rate;
    }

    @Override
    public double getFiveMinuteRate() {
        return m5Rate;
    }

    @Override
    public double getOneMinuteRate() {
        return m1Rate;
    }

    @Override
    public double getMeanRate() {
        return mean;
    }

    @Override
    public long getCount() {
        return count;
    }
}
