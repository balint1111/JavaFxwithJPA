package com.example.spring_javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringJavaFxApplication extends Application {


    private ConfigurableApplicationContext applicationContext;
    private Parent root;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(SpringJavaFxApplication.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/primaryScene.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Objektum orientált adatbázis demó");
        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add("/style.css");
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        Application.launch(SpringJavaFxApplication.class, args);
    }
}
