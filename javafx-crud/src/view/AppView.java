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
            // Correção principal: inicializar o FXMLLoader com o caminho correto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/app.fxml"));
            
            AnchorPane pane = loader.load();  // Removi o tipo genérico pois não é necessário
            Scene scene = new Scene(pane);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("CRUD Cachorros");
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            // Adicione isto para ver detalhes do erro de carregamento
            System.err.println("Erro ao carregar o FXML: " + e.getMessage());
            throw e; // Re-lança a exceção para ver o stacktrace completo
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}