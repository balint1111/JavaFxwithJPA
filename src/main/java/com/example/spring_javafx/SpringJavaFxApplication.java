package com.example.spring_javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringJavaFxApplication extends Application {

    public static Stage primaryStage;

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
        this.primaryStage = primaryStage;
        stage.setTitle("Hello World");
        Scene scene = new Scene(root, 800, 600);
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
