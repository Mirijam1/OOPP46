package nl.tudelft.gogreen.coapi;

import nl.tudelft.gogreen.coapi.config.Endpoints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class CarbonServer {
    /* Just ignore for now */
    public static void main(String... args) {
        SpringApplication.run(CarbonServer.class);
    }

    @RequestMapping(method = RequestMethod.GET, path = "test")
    public @ResponseBody
    String start() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Endpoints.baseURL)
            .queryParam("input_location_mode", "1")
            .queryParam("input_location", "10001")
            .queryParam("input_income", "1")
            .queryParam("input_size", "0");
        return builder.toUriString();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}

