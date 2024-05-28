package videogamedb;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulationVideoGame extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://videogamedb.uk")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("PostmanRuntime/7.38.0");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "ca62dd97-51c7-4695-8450-c269b53c8a5b")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "d963879e-e118-4180-822d-912c4ede5ec1")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "41bcb28c-7e57-47e9-b79d-68d27153418f")
  );
  
  private Map<CharSequence, String> headers_3 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "73dbf9e9-ad36-48e4-afed-c3be49260d73")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "a72d416b-6d6a-47ec-a2a3-b17649bdb9d2")
  );
  
  private Map<CharSequence, String> headers_5 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "77a64b0d-044b-45dc-b7c2-fbc3077ab0d2")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulationVideoGame")
    .exec(
      http("request_0")
        .get("/api/videogame")
        .headers(headers_0)
    )
    .pause(4)
    .exec(
      http("request_1")
        .get("/api/videogame/2")
        .headers(headers_1)
    )
    .pause(3)
    .exec(
      http("request_2")
        .post("/api/videogame")
        .headers(headers_2)
        .body(RawFileBody("videogamedb/recordedsimulationvideogame/0002_request.json"))
        .check(status().is(403))
    )
    .pause(6)
    .exec(
      http("request_3")
        .put("/api/videogame/3")
        .headers(headers_3)
        .body(RawFileBody("videogamedb/recordedsimulationvideogame/0003_request.json"))
        .check(status().is(403))
    )
    .pause(8)
    .exec(
      http("request_4")
        .delete("/api/videogame/2")
        .headers(headers_4)
        .check(status().is(403))
    )
    .pause(7)
    .exec(
      http("request_5")
        .post("/api/authenticate")
        .headers(headers_5)
        .body(RawFileBody("videogamedb/recordedsimulationvideogame/0005_request.json"))
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
