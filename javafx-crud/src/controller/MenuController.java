package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class MenuController {

    @FXML
    private void abrirTelaCachorro() {
        carregarTela("/view/app.fxml", "CRUD Cachorros");
    }

    @FXML
    private void abrirTelaDono() {
        carregarTela("/view/dono.fxml", "CRUD Donos");
    }

    private void carregarTela(String caminhoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
