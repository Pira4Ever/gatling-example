package simulations;

import metrics.GatlingMetrics;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://127.0.0.1:8080")
                    .acceptHeader("application/json")
                    .shareConnections();

    ScenarioBuilder scenario = scenario("UserLogin")

            .exec(session ->
                    session.set("startTime", System.currentTimeMillis())
            )

            .exec(
                    http("UserLogin")
                            .post("/user/login")
                            .header("Content-Type", "application/json")
                            .body(StringBody("""
                        {
                            "username": "Pira",
                            "password": "s3cr3t_p@ss"
                        }"""))
                            .check(status().is(200))
            )

            .exec(session -> {

                long start = session.getLong("startTime");

                long duration = System.currentTimeMillis() - start;

                if (session.isFailed()) {
                    GatlingMetrics.error(duration);
                } else {
                    GatlingMetrics.success(duration);
                }

                GatlingMetrics.record(duration);

                return session;
            });

    {
        setUp(
                scenario.injectOpen(
                        rampUsersPerSec(0).to(1000).during(60),
                        constantUsersPerSec(1000).during(60),
                        rampUsersPerSec(1000).to(2000).during(60),
                        constantUsersPerSec(2000).during(60),
                        rampUsersPerSec(2000).to(3000).during(60),
                        constantUsersPerSec(3000).during(60),
                        rampUsersPerSec(3000).to(4000).during(60),
                        constantUsersPerSec(4000).during(60),
                        rampUsersPerSec(4000).to(5000).during(60),
                        constantUsersPerSec(5000).during(60)
                ).protocols(httpProtocol)
        );
    }
}