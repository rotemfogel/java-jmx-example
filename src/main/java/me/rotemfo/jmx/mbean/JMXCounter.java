package me.rotemfo.jmx.mbean;

import com.codahale.metrics.Counter;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx.mbean
 * file:    JMXCounter
 * created: 2019-07-16
 * author:  rotem
 */
public class JMXCounter implements JMXCounterMBean {
    public long count = 0;

    public JMXCounter(final Counter counter) {
        this(counter.getCount());
    }

    public JMXCounter(final long count) {
        this.count = count;
    }

    public void update(final Counter counter) {
        this.count = counter.getCount();
    }

    @Override
    public long getCount() {
        return count;
    }
}
