package view;

import javafx.beans.property.*;

public class CachorroView {
    private final IntegerProperty id;
    private final StringProperty nome;
    private final IntegerProperty idade;
    private final DoubleProperty peso;
    private final StringProperty nomeDono;

    public CachorroView(int id, String nome, int idade, double peso, String nomeDono) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.idade = new SimpleIntegerProperty(idade);
        this.peso = new SimpleDoubleProperty(peso);
        this.nomeDono = new SimpleStringProperty(nomeDono);
    }

    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public int getIdade() { return idade.get(); }
    public double getPeso() { return peso.get(); }
    public String getNomeDono() { return nomeDono.get(); }

    public void setNome(String nome) { this.nome.set(nome); }
    public void setIdade(int idade) { this.idade.set(idade); }
    public void setPeso(double peso) { this.peso.set(peso); }
    public void setNomeDono(String nomeDono) { this.nomeDono.set(nomeDono); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public IntegerProperty idadeProperty() { return idade; }
    public DoubleProperty pesoProperty() { return peso; }
    public StringProperty nomeDonoProperty() { return nomeDono; }
}
