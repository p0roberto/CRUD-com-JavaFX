package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Menu Principal");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar o FXML: " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
