package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulationAgain extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://videogamedb.uk")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("PostmanRuntime/7.38.0");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "5d2bee12-828b-402b-aff5-a0d963a96bf2")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "a5fa8877-120c-4228-a09a-098b91d245e2"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjg5MDI3MywiZXhwIjoxNzE2ODkzODczfQ.LPLAT4GqaH4yJ0vMB2KlPr2ugVDPVxvkNFjUFwTfk_0")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "e3005e20-5407-4842-850b-ce4cadc4f921")
  );
  
  private Map<CharSequence, String> headers_3 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "2b3dcf6f-c64c-4537-9bbd-d821970ccf2d")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Postman-Token", "885ca849-9cf8-49ae-9da9-b529bc2b60b1"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjg5MDI3MywiZXhwIjoxNzE2ODkzODczfQ.LPLAT4GqaH4yJ0vMB2KlPr2ugVDPVxvkNFjUFwTfk_0")
  );
  
  private Map<CharSequence, String> headers_5 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Postman-Token", "02996474-4079-49a2-b4ec-1018e8cfd31e"),
    Map.entry("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNjg5MDI3MywiZXhwIjoxNzE2ODkzODczfQ.LPLAT4GqaH4yJ0vMB2KlPr2ugVDPVxvkNFjUFwTfk_0")
  );


  private ScenarioBuilder scn = scenario("RecordedSimulationAgain")
    .exec(
      http("request_0")
        .post("/api/authenticate")
        .headers(headers_0)
        .body(RawFileBody("computerdatabase/recordedsimulationagain/0000_request.json"))
    )
    .pause(6)
    .exec(
      http("request_1")
        .post("/api/videogame")
        .headers(headers_1)
        .body(RawFileBody("computerdatabase/recordedsimulationagain/0001_request.json"))
    )
    .pause(2)
    .exec(
      http("request_2")
        .get("/api/videogame/2")
        .headers(headers_2)
    )
    .pause(2)
    .exec(
      http("request_3")
        .get("/api/videogame")
        .headers(headers_3)
    )
    .pause(1)
    .exec(
      http("request_4")
        .put("/api/videogame/3")
        .headers(headers_4)
        .body(RawFileBody("computerdatabase/recordedsimulationagain/0004_request.json"))
    )
    .pause(2)
    .exec(
      http("request_5")
        .delete("/api/videogame/2")
        .headers(headers_5)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
