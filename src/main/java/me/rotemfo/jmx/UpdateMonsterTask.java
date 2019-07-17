package me.rotemfo.jmx;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import me.rotemfo.jmx.mbean.JMXCounter;
import me.rotemfo.jmx.mbean.JMXMeter;
import me.rotemfo.jmx.mbean.JMXTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * project: spring-boot-jmx-example
 * package: me.rotemfo.jmx
 * file:    UpdateMonsterTask
 * created: 2019-07-16
 * author:  rotem
 */
public final class UpdateMonsterTask extends TimerTask {
    private static final Logger log = LoggerFactory.getLogger(UpdateMonsterTask.class);

    private enum KeyTypes {
        TIMER("t"),
        METER("m"),
        COUNTER("c");

        private final String name;

        KeyTypes(final String name) {
            this.name = name;
        }
    }

    private transient final Random random = new Random();
    private transient int level = 0;
    private transient MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    private Set<KeyTypes> keys = new HashSet<>(Arrays.asList(KeyTypes.TIMER, KeyTypes.METER, KeyTypes.COUNTER));
    private final MetricWrapper wrapper = MetricWrapper.getInstance();

    private Map<String, Counter> counters = new HashMap<>();
    private Map<String, JMXCounter> jcounters = new HashMap<>();
    private Map<String, Meter> meters = new HashMap<>();
    private Map<String, JMXMeter> jmeters = new HashMap<>();
    private Map<String, Timer> timers = new HashMap<>();
    private Map<String, JMXTimer> jtimers = new HashMap<>();

    private void register(final String name, final Object mbean) throws Exception {
        ObjectName objectName = new ObjectName(name);
        mbs.registerMBean(mbean, objectName);
    }

    private void registerCounter(final String name, final JMXCounter mbean) {
        try {
            register(name, mbean);
            jcounters.putIfAbsent(name, mbean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void registerMeter(final String name, final JMXMeter mbean) {
        try {
            register(name, mbean);
            jmeters.putIfAbsent(name, mbean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void registerTimer(final String name, final JMXTimer mbean) {
        try {
            register(name, mbean);
            jtimers.putIfAbsent(name, mbean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        if (level == 100) level = 0;
        level++;
        log.info("updating monster values to {}", level);
        MetricRegistry registry = wrapper.getRegistry();
        keys.stream().forEach(key -> {
            final String k = key.toString().toLowerCase();
            String name = String.format("com.upstreamsecurity.counters.%s:type=%s,name=%s", k, k, level);

            switch (key) {
                case TIMER: {
                    Timer t = timers.getOrDefault(name, registry.timer(name));
                    registerTimer(name, new JMXTimer(t));
                    timers.putIfAbsent(name, t);
                    break;
                }
                case COUNTER: {
                    Counter c = counters.getOrDefault(name, registry.counter(name));
                    JMXCounter jmxCounter = new JMXCounter(c);
                    registerCounter(name, jmxCounter);
                    counters.putIfAbsent(name, c);
                    break;
                }
                case METER: {
                    Meter m = meters.getOrDefault(name, registry.meter(name));
                    JMXMeter jmxMeter = new JMXMeter(m);
                    registerMeter(name, jmxMeter);
                    meters.putIfAbsent(name, m);
                    break;
                }
            }
        });
        timers.entrySet().stream().forEach(t -> {
            final String key = t.getKey();
            final Timer value = t.getValue();
            Timer.Context c = value.time();
            try {
                Thread.sleep(level * 10);
            } catch (InterruptedException ignored) {
            }
            c.stop();
            jtimers.get(key).update(value);
        });
        counters.entrySet().stream().forEach(t -> {
            final String key = t.getKey();
            final Counter value = t.getValue();
            value.inc(random.nextInt());
            jcounters.get(key).update(value);
        });
        meters.entrySet().stream().forEach(t -> {
            final String key = t.getKey();
            final Meter value = t.getValue();
            value.mark(random.nextLong());
            jmeters.get(key).update(value);
        });
    }
}
