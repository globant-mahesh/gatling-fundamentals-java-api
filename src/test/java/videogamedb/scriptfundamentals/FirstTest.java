package videogamedb.scriptfundamentals;

/**
 * @author MaheshBhosale
 * @created 15/05/2024
 * @project gatling-fundamentals-java-api
 */

import io.gatling.core.action.Feed;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class FirstTest extends Simulation {

    private static FeederBuilder.FileBased<String> csvfeed = csv("data/gameCsvFile.csv").circular();

    private static FeederBuilder.FileBased<Object> jsonfeed = jsonFile("data/gameJsonFile.json").random();

    //    Return type of the feeder is casted to the expected type of Object for json field
    private static Iterator<Map<String, Object>> customFeed = Stream.generate((Supplier<Map<String, Object>>) () -> {
        Random rand = new Random();
        int gameId = rand.nextInt(10 - 1 + 1) + 1;
        return Collections.singletonMap("gameId", gameId);
    }).iterator();

    public static LocalDate getRandomDate() {
        int random = 100 * 365;
        return LocalDate.ofEpochDay(ThreadLocalRandom.current().nextInt(-random, random));
    }

    private static Iterator<Map<String, Object>> complexCustomFeed = Stream.generate((Supplier<Map<String, Object>>) () -> {
        Random rand = new Random();
        String category = RandomStringUtils.randomAlphanumeric(5) + "_gameCategory";
        String name = RandomStringUtils.randomAlphanumeric(5) + "_gameName";
        String releaseDate = getRandomDate().toString();
        String rating = RandomStringUtils.randomAlphanumeric(5) + "_gameRating";
        int reviewScore = rand.nextInt(10 - 1 + 1) + 1;
        Map<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("name", name);
        hmap.put("category", category);
        hmap.put("rating", rating);
        hmap.put("releaseDate", releaseDate);
        hmap.put("reviewScore", reviewScore);
        return hmap;
    }).iterator();

    public static ChainBuilder getSpecificGameFromComplexCustomFeed = feed(complexCustomFeed)
            .exec(http("Get specific game with custom feed").post("/api/videogame").header("authorization", "Bearer #{token}").body(ElFileBody("bodies/newGameTemplate.json")).asJson().check(bodyString().saveAs("responseBody")))
            .exec(session -> {
                System.out.println(session.getString("responseBody"));
                return session;
            });

    public static ChainBuilder getSpecificGameFromCustomFeed = feed(customFeed).exec(http("Get specific game with custom feed").get("/api/videogame/#{gameId}"));

    private static ChainBuilder getSpecificGameFromCSVFeed = feed(csvfeed).exec(http("Get specific game with name: #{gameName}").get("/api/videogame/#{gameId}").check(jmesPath("name").isEL("#{gameName}")));

    private static ChainBuilder getSpecificGameFromJSONFeed = feed(jsonfeed).exec(http("Get specific game with name: #{name}").get("/api/videogame/#{id}").check(jmesPath("name").isEL("#{name}")));

    private static ChainBuilder getSpecificGame = repeat(4, "myCounter").on(
            exec(http("Get specific game of id #{myCounter}").get("/api/videogame/#{myCounter}").check(status().is(200), status().not(500))));
    private static ChainBuilder getAllGames = repeat(3).on(exec(http("Get all games").get("/api/videogame").check(status().is(200), status().not(400))));

    private static ChainBuilder authenticateUser = exec(http("Authenticate user").post("/api/authenticate").header("Content-Type", "application/json").body(StringBody("{\n" +
            "    \"username\": \"admin\",\n" +
            "    \"password\": \"admin\"\n" +
            "}")).check(jmesPath("token").saveAs("token")));

    private static ChainBuilder postGame = exec(http("Post game").post("/api/videogame").header("Authorization", "Bearer #{token}").body(StringBody("{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}")));
    //    Http configuration
    HttpProtocolBuilder httpProtocol = http.baseUrl("https://videogamedb.uk")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

//    Scenario with complex custom feeder
    ScenarioBuilder scn = scenario("Scenario with complex custom").exec(authenticateUser).repeat(5).on(exec(getSpecificGameFromComplexCustomFeed));

    //    Scenario configuration with simple custom feeder
//    ScenarioBuilder scn = scenario("Get game using feed data from custom").repeat(5).on(exec(getSpecificGameFromCustomFeed));
//    ScenarioBuilder scn = scenario("Get game using feed data from json").exec(getSpecificGameFromJSONFeed);
//    ScenarioBuilder scn = scenario("Get game using feed data from csv").exec(getSpecificGameFromCSVFeed);

//    ScenarioBuilder scn = scenario("Post game scenario").exec(authenticateUser).exec(postGame);

//    ScenarioBuilder scn = scenario("My second scenario with chaining").repeat(1).on(exec(getSpecificGame)).repeat(2).on(exec(getAllGames));

//    ScenarioBuilder scn = scenario("My first test")
//            .exec(http("Get specific game")
//                    .get("/api/videogame/2").check(status().is(200)))
//            .pause(1).exec(http("Get all games")
//                    .get("/api/videogame").check(status().in(200, 201, 202)).check(jsonPath("$[?(@.id==1)].name").is("Resident Evil 5")))
//            .pause(1, 5).exec(http("Get all games")
//                    .get("/api/videogame").check(status().in(200, 201, 202)).check(jmesPath("[? category == 'Shooter'].name").ofList().is(List.of("Resident Evil 4", "Doom"))))
//            .pause(1, 5).exec(http("Get all games")
//                    .get("/api/videogame")
//                    .check(status().in(200, 201, 202))
//                    .check(jmesPath("[1].id")
//                            .saveAs("gameId")))
//            .pause(5)
//            .exec(http("Get specific game for saved variable #{gameId}").get("/api/videogame/#{gameId}").check(bodyString().saveAs("responseBody")).check(status().not(500), status().not(400)))
//            .pause(Duration.ofMillis(2500))
//            .exec(session -> {
//                System.out.printf("gameId set to: "+ session.getString("gameId"));
//                System.out.println("Response body: " + session.getString("responseBody"));
//                return session;
//            });

    //  Load simulation
    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }

}
