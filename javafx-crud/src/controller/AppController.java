package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import model.*;
import view.*;

public class AppController implements Initializable {
    @FXML private TableView<CachorroView> tabela;

    @FXML private TableColumn<CachorroView, Integer> idCol;
    @FXML private TableColumn<CachorroView, String> nomeCol;
    @FXML private TableColumn<CachorroView, Integer> idadeCol;
    @FXML private TableColumn<CachorroView, Double> pesoCol;
    @FXML private TableColumn<CachorroView, String> nomeDonoCol;

    @FXML private TextField idField;
    @FXML private TextField nomeField;
    @FXML private TextField idadeField;
    @FXML private TextField pesoField;
    @FXML private ComboBox<Dono> donoComboBox;
    @FXML private Label contadorLabel;

    @FXML private Button adicionarButton;
    @FXML private Button atualizarButton;
    @FXML private Button deletarButton;
    @FXML private Button cancelarButton;
    @FXML private Button salvarButton;

    private static Database database = new Database("cachorros.db");
    private static CachorroRepository cachorroRepo = new CachorroRepository(database);
    private static DonoRepository donoRepo = new DonoRepository(database);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idadeCol.setCellValueFactory(new PropertyValueFactory<>("idade"));
        pesoCol.setCellValueFactory(new PropertyValueFactory<>("peso"));
        nomeDonoCol.setCellValueFactory(new PropertyValueFactory<>("nomeDono"));

        tabela.setItems(loadAllCachorros());
        List<Dono> donos = donoRepo.loadAll();
        donoComboBox.setItems(FXCollections.observableArrayList(donos != null ? donos : List.of()));

        tabela.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> handleCachorroSelected(newValue)
        );

        atualizarContador();
        desabilitarBotoes(false, true, true, true, true);
        desabilitarCampos(true);

        idadeField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                idadeField.setText(newVal.replaceAll("[^\\d]", ""));
            }
        });

        pesoField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d*)?")) {
                pesoField.setText(oldVal);
            }
        });
    }

    private ObservableList<CachorroView> loadAllCachorros() {
        ObservableList<CachorroView> lista = FXCollections.observableArrayList();
        List<Cachorro> listaFromDb = cachorroRepo.loadAll();
        for (Cachorro c : listaFromDb) {
            lista.add(new CachorroView(
                c.getId(),
                c.getNome(),
                c.getIdade(),
                c.getPeso(),
                c.getDono() != null ? c.getDono().getNome() : ""
            ));
        }
        return lista;
    }

    private void handleCachorroSelected(CachorroView c) {
        if (c != null) {
            idField.setText(String.valueOf(c.getId()));
            nomeField.setText(c.getNome());
            idadeField.setText(String.valueOf(c.getIdade()));
            pesoField.setText(String.valueOf(c.getPeso()));
            donoComboBox.getSelectionModel().select(donoRepo.loadFromIdByName(c.getNomeDono()));
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
        idadeField.setDisable(desabilitado);
        pesoField.setDisable(desabilitado);
        donoComboBox.setDisable(desabilitado);
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        idadeField.setText("");
        pesoField.setText("");
        donoComboBox.getSelectionModel().clearSelection();
    }

    private void atualizarContador() {
        contadorLabel.setText("Total de Cachorros: " + cachorroRepo.count());
    }

    @FXML private void onAdicionarButtonAction() {
        tabela.getSelectionModel().clearSelection();
        desabilitarCampos(false);
        desabilitarBotoes(true, true, true, false, false);
        limparCampos();
        nomeField.requestFocus();
    }

    @FXML private void onCancelarButtonAction() {
        desabilitarCampos(true);
        desabilitarBotoes(false, true, true, true, true);
        limparCampos();
        tabela.getSelectionModel().clearSelection();
    }

    @FXML private void onSalvarButtonAction() {
        try {
            Cachorro c = new Cachorro();
            c.setNome(nomeField.getText());
            c.setIdade(Integer.parseInt(idadeField.getText()));
            c.setPeso(Double.parseDouble(pesoField.getText()));
            c.setDono(donoComboBox.getValue());

            cachorroRepo.create(c);
            tabela.setItems(loadAllCachorros());
            atualizarContador();
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    @FXML private void onAtualizarButtonAction() {
        CachorroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Cachorro c = new Cachorro();
                c.setId(selected.getId());
                c.setNome(nomeField.getText());
                c.setIdade(Integer.parseInt(idadeField.getText()));
                c.setPeso(Double.parseDouble(pesoField.getText()));
                c.setDono(donoComboBox.getValue());

                cachorroRepo.update(c);
                tabela.setItems(loadAllCachorros());
                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Erro ao atualizar: " + e.getMessage()).show();
            }
        }
    }

    @FXML private void onDeletarButtonAction() {
        CachorroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Cachorro c = new Cachorro();
                c.setId(selected.getId());
                c.setNome(selected.getNome());
                c.setIdade(selected.getIdade());
                c.setPeso(selected.getPeso());
                cachorroRepo.delete(c);

                tabela.setItems(loadAllCachorros());
                atualizarContador();
                limparCampos();
                desabilitarCampos(true);
                desabilitarBotoes(false, true, true, true, true);
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Erro ao deletar: " + e.getMessage()).show();
            }
        }
    }
}
