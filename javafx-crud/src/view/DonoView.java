package view;

import javafx.beans.property.*;

public class DonoView {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final StringProperty telefone;

    public DonoView(int id, String nome, String telefone) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.telefone = new SimpleStringProperty(telefone);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getTelefone() { return telefone.get(); }

    // Setters
    public void setNome(String nome) { this.nome.set(nome); }
    public void setTelefone(String telefone) { this.telefone.set(telefone); }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty telefoneProperty() { return telefone; }
}
