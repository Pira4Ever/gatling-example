package metrics;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class GatlingMetrics {

    private static final PrometheusMeterRegistry registry =
            new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    private static final Timer REQUEST_TIMER =
            Timer.builder("gatling_request_latency")
                    .publishPercentiles(0.95, 0.99)
                    .publishPercentileHistogram()
                    .register(registry);

    private static final Counter REQUEST_COUNTER =
            Counter.builder("gatling_requests_total")
                    .register(registry);

    private static final Counter SUCCESS_COUNTER =
            Counter.builder("gatling_requests_success_total")
                    .register(registry);

    private static final Counter ERROR_COUNTER =
            Counter.builder("gatling_requests_error_total")
                    .register(registry);

    static {

        try {

            HttpServer server = HttpServer.create(new InetSocketAddress(9091), 0);

            server.createContext("/metrics", exchange -> {

                String response = registry.scrape();

                exchange.getResponseHeaders().add(
                        "Content-Type",
                        "text/plain; version=0.0.4; charset=utf-8"
                );

                exchange.sendResponseHeaders(200, response.getBytes().length);

                OutputStream os = exchange.getResponseBody();

                os.write(response.getBytes());

                os.close();
            });

            server.start();

            System.out.println("Metrics running on :9091/metrics");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void record(long durationMs) {

        REQUEST_TIMER.record(durationMs, TimeUnit.MILLISECONDS);

        REQUEST_COUNTER.increment();
    }

    public static void success(long durationMs) {

        REQUEST_TIMER.record(durationMs, TimeUnit.MILLISECONDS);

        REQUEST_COUNTER.increment();

        SUCCESS_COUNTER.increment();
    }

    public static void error(long durationMs) {

        REQUEST_TIMER.record(durationMs, TimeUnit.MILLISECONDS);

        REQUEST_COUNTER.increment();

        ERROR_COUNTER.increment();
    }
}