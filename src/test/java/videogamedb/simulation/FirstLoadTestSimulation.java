package videogamedb.simulation;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * @author MaheshBhosale
 * @created 27/05/2024
 * @project gatling-fundamentals-java-api
 */
public class FirstLoadTestSimulation extends Simulation {

    private static final int INITIAL_RAMP_RATE = Integer.parseInt(System.getProperty("INITIAL_RAMP_RATE", "1"));
    private static final int FINAL_RAMP_RATE = Integer.parseInt(System.getProperty("FINAL_RAMP_RATE", "5"));
    private static final int MAX_DURATION = Integer.parseInt(System.getProperty("MAX_DURATION", "60"));

    //    Protocol setup for API tests
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json");

    //    API Call Test1
    private static ChainBuilder getAllVideoGames =
            exec(http("Get all video games")
                    .get("/videogame"));

    //    API Call Test2
    private static ChainBuilder getSpecificGame =
            exec(http("Get specific game")
                    .get("/videogame/2"));

// For ramp up user scenario
//    private ScenarioBuilder scn = scenario("Video game db - Section 7 code")
//            .exec(getAllVideoGames)
//            .pause(5)
//            .exec(getSpecificGame)
//            .pause(5)
//            .exec(getAllVideoGames);

    //    For fixed duration testing
    private ScenarioBuilder scn = scenario("Video game db - Section 7 code").forever().on(exec(getAllVideoGames)
            .pause(5)
            .exec(getSpecificGame)
            .pause(5)
            .exec(getAllVideoGames));

    @Override
    public void before() {
        System.out.println("Starting test with parameters: " + INITIAL_RAMP_RATE + FINAL_RAMP_RATE + MAX_DURATION);
    }

    //    load test setup
    //    rampup per sec doesn't follow exact ramping up but a trend as per the code
    {
        setUp(
                scn.injectOpen(
                        nothingFor(5),
                        atOnceUsers(5),
//                        rampUsers(10).during(20),
                        rampUsersPerSec(INITIAL_RAMP_RATE).to(FINAL_RAMP_RATE).during(10).randomized()
                ).protocols(httpProtocol)
        ).maxDuration(MAX_DURATION);
    }
}
