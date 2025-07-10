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
    @FXML 
    private TableView<CachorroView> tabela;
    
    @FXML 
    private TableColumn<CachorroView, Integer> idCol;
    
    @FXML 
    private TableColumn<CachorroView, String> nomeCol;
    
    @FXML 
    private TableColumn<CachorroView, Integer> idadeCol;
    
    @FXML 
    private TableColumn<CachorroView, Double> pesoCol;
    
    @FXML 
    private TextField idField;
    
    @FXML 
    private TextField nomeField;
    
    @FXML 
    private TextField idadeField;
    
    @FXML 
    private TextField pesoField;
    
    @FXML 
    private Button adicionarButton;
    
    @FXML 
    private Button atualizarButton;
    
    @FXML 
    private Button deletarButton;
    
    @FXML 
    private Button cancelarButton;
    
    @FXML 
    private Button salvarButton;

    private static Database database = new Database("cachorros.db");
    private static CachorroRepository cachorroRepo = new CachorroRepository(database);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idadeCol.setCellValueFactory(new PropertyValueFactory<>("idade"));
        pesoCol.setCellValueFactory(new PropertyValueFactory<>("peso"));
        
        tabela.setItems(loadAllCachorros());
        
        tabela.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> handleCachorroSelected(newValue));
        
        desabilitarBotoes(false, true, true, true, true);
        desabilitarCampos(true);
        
        idadeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                idadeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        pesoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                pesoField.setText(oldValue);
            }
        });
    }

    private ObservableList<CachorroView> loadAllCachorros() {
        ObservableList<CachorroView> lista = FXCollections.observableArrayList();
        List<Cachorro> listaFromDatabase = cachorroRepo.loadAll();
        
        for (Cachorro cachorro : listaFromDatabase) {
            lista.add(new CachorroView(
                cachorro.getId(),
                cachorro.getNome(),
                cachorro.getIdade(),
                cachorro.getPeso()
            ));
        }
        return lista;
    }

    private void handleCachorroSelected(CachorroView cachorro) {
        if (cachorro != null) {
            idField.setText(Integer.toString(cachorro.getId()));
            nomeField.setText(cachorro.getNome());
            idadeField.setText(Integer.toString(cachorro.getIdade()));
            pesoField.setText(Double.toString(cachorro.getPeso()));
            desabilitarBotoes(false, false, false, true, true);
            desabilitarCampos(false);
        }
    }

    private void desabilitarBotoes(boolean adicionar, boolean atualizar, boolean deletar, 
                                 boolean cancelar, boolean salvar) {
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
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        idadeField.setText("");
        pesoField.setText("");
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
            Cachorro cachorro = new Cachorro();
            cachorro.setNome(nomeField.getText());
            cachorro.setIdade(Integer.parseInt(idadeField.getText()));
            cachorro.setPeso(Double.parseDouble(pesoField.getText()));
            
            Cachorro cachorroSalvo = cachorroRepo.create(cachorro);
            
            tabela.getItems().add(new CachorroView(
                cachorroSalvo.getId(),
                cachorroSalvo.getNome(),
                cachorroSalvo.getIdade(),
                cachorroSalvo.getPeso()
            ));
            
            desabilitarCampos(true);
            desabilitarBotoes(false, true, true, true, true);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).show();
        }
    }

    @FXML
    private void onAtualizarButtonAction() {
        CachorroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Cachorro cachorro = new Cachorro();
                cachorro.setId(selected.getId());
                cachorro.setNome(nomeField.getText());
                cachorro.setIdade(Integer.parseInt(idadeField.getText()));
                cachorro.setPeso(Double.parseDouble(pesoField.getText()));
                
                cachorroRepo.update(cachorro);
                
                selected.setNome(cachorro.getNome());
                selected.setIdade(cachorro.getIdade());
                selected.setPeso(cachorro.getPeso());
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
        CachorroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                Cachorro cachorro = new Cachorro();
                cachorro.setId(selected.getId());
                cachorro.setNome(selected.getNome());
                cachorro.setIdade(selected.getIdade());
                cachorro.setPeso(selected.getPeso());
                
                cachorroRepo.delete(cachorro);
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