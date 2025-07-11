package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import model.*;
import view.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DonoController implements Initializable {

    @FXML private TableView<DonoView> tabela;

    @FXML private TableColumn<DonoView, Integer> idCol;
    @FXML private TableColumn<DonoView, String> nomeCol;
    @FXML private TableColumn<DonoView, String> telefoneCol;

    @FXML private TextField idField;
    @FXML private TextField nomeField;
    @FXML private TextField telefoneField;

    @FXML private Button adicionarButton;
    @FXML private Button atualizarButton;
    @FXML private Button deletarButton;
    @FXML private Button cancelarButton;
    @FXML private Button salvarButton;

    private static Database database = new Database("cachorros.db");
    private static DonoRepository donoRepo = new DonoRepository(database);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telefoneCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tabela.setItems(loadAllDonos());

        tabela.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> handleDonoSelected(newVal)
        );

        desabilitarBotoes(false, true, true, true, true);
        desabilitarCampos(true);
    }

    private ObservableList<DonoView> loadAllDonos() {
        ObservableList<DonoView> lista = FXCollections.observableArrayList();
        List<Dono> listaFromDb = donoRepo.loadAll();
        for (Dono dono : listaFromDb) {
            lista.add(new DonoView(dono.getId(), dono.getNome(), dono.getTelefone()));
        }
        return lista;
    }

    private void handleDonoSelected(DonoView dono) {
        if (dono != null) {
            idField.setText(String.valueOf(dono.getId()));
            nomeField.setText(dono.getNome());
            telefoneField.setText(dono.getTelefone());
            desabilitarBotoes(false, false, false, true, true);
            desabilitarCampos(false);
        }
    }

    private void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, boolean cancelar, boolean salvar) {
        adicionarButton.setDisable(adicionar);
        atualizarButton.setDisable(atualizar);
        deletarButton.setDisable(deletar);
        cancelarButton.setDisable(cancelar);
        salvarButton.setDisable(salvar);
    }

    private void desabilitarCampos(boolean desabilitado) {
        nomeField.setDisable(desabilitado);
        telefoneField.setDisable(desabilitado);
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        telefoneField.setText("");
    }

    @FXML
    private void onAdicionarButtonAction() {
        tabela.getSelectionModel().clearSelection();
        desabilitarCampos(false);
        desabilitarBotoes(true, true, true, false, false);
        limparCampos();
        nomeField.requestFocus();
    }

    @FXML
    private void onCancelarButtonAction() {
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        limparCampos();
        tabela.getSelectionModel().clearSelection();
    }

    @FXML
    private void onSalvarButtonAction() {
        try {
            Dono dono = new Dono();
            dono.setNome(nomeField.getText());
            dono.setTelefone(telefoneField.getText());

            Dono donoSalvo = donoRepo.create(dono);

            tabela.getItems().add(new DonoView(
                donoSalvo.getId(),
                donoSalvo.getNome(),
                donoSalvo.getTelefone()
            ));

            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    @FXML
    private void onAtualizarButtonAction() {
        DonoView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Dono dono = new Dono();
                dono.setId(selected.getId());
                dono.setNome(nomeField.getText());
                dono.setTelefone(telefoneField.getText());

                donoRepo.update(dono);

                selected.setNome(dono.getNome());
                selected.setTelefone(dono.getTelefone());
                tabela.refresh();

                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Erro ao atualizar: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    private void onDeletarButtonAction() {
        DonoView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Dono dono = new Dono();
                dono.setId(selected.getId());
                dono.setNome(selected.getNome());
                dono.setTelefone(selected.getTelefone());

                donoRepo.delete(dono);
                tabela.getItems().remove(selected);
                limparCampos();

                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Erro ao deletar: " + e.getMessage()).show();
            }
        }
    }
}
