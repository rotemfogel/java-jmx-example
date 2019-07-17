package me.rotemfo.jmx;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.Optional;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx
 * file:    MetricWrapper
 * created: 2019-07-16
 * author:  rotem
 */
public final class MetricWrapper {
    private final MetricRegistry metrics;
    private static final Object mutex = new Object();
    private static Optional<MetricWrapper> instance = Optional.empty();

    private MetricWrapper() {
        this.metrics = new MetricRegistry();
        JmxReporter.forRegistry(metrics).build().start();
    }

    public static MetricWrapper getInstance() {
        synchronized (mutex) {
            if (!instance.isPresent())
                instance = Optional.of(new MetricWrapper());
            return instance.get();
        }
    }

    public MetricRegistry getRegistry() {
        return this.metrics;
    }
}
