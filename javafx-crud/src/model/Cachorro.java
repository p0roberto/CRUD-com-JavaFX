package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cachorro")
public class Cachorro {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String nome;

    @DatabaseField
    private int idade;

    @DatabaseField
    private double peso;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Dono dono;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public Dono getDono() { return dono; }
    public void setDono(Dono dono) { this.dono = dono; }
}
