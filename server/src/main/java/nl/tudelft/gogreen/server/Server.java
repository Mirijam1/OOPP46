package nl.tudelft.gogreen.server;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class Server {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * <p>Main entry point of program. Will bootstrap server.</p>
     * @param args Program arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Server.class);
    }

    /**
     * initialize Unirest.
     */
    @PostConstruct
    public void initUnirest() {
        com.fasterxml.jackson.databind.ObjectMapper mapper =
                new com.fasterxml.jackson.databind.ObjectMapper();

        Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueClass) {
                try {
                    return mapper.readValue(value, valueClass);
                } catch (IOException exception) {
                    System.err.println("Something went wrong while reading a value from internal API");
                    exception.printStackTrace();
                }

                return null;
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException exception) {
                    System.err.println("Something went wrong while writing a value to internal API");
                    exception.printStackTrace();
                }

                return null;
            }
        });
    }

    /**
     * Encrypts password.
     * @return encryptedPassword
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * instantiate jackson2JsonObjectMapper.
     * @return objectmapper
     */
    @Bean
    public ObjectMapper jackson2JsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        return mapper;
    }
}