package nl.tudelft.gogreen.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static javafx.application.Application.launch;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
public class Server extends Application {
    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    public void init() throws Exception {
        springContext = SpringApplication.run(Server.class);
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    public void start(Stage primaryStage) throws Exception{
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        rootNode = fxmlLoader.load();
        primaryStage.getIcons().add(new Image("/img/icon.png"));
        primaryStage.setTitle("GoGreen");
        Scene scene = new Scene(rootNode, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stop() {
        springContext.stop();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public ObjectMapper jackson2JsonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        return mapper;
    }
}
