package nl.tudelft.gogreen.coapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class CarbonServer {
    /* Just ignore for now */
    public static void main(String... args) {
        SpringApplication.run(CarbonServer.class);
    }

    @RequestMapping(method = RequestMethod.GET, path = "test")
    public @ResponseBody String start() {
        return "Second API";
    }
}
