package nl.tudelft.gogreen.server;

import nl.tudelft.gogreen.shared.Shared;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class Server {
    /* Just ignore for now */
    public static void main(String... args) {
        SpringApplication.run(Server.class);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, String> test() {
        return Collections.singletonMap("response", Shared.getTestString());
    }
}
