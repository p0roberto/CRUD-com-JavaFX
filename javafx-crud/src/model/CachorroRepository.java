package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CachorroRepository {
    private static Database database;
    private static Dao<Cachorro, Integer> dao;
    private List<Cachorro> cachorros;

    public CachorroRepository(Database database) {
        CachorroRepository.database = database;
        cachorros = new ArrayList<>();
        try {
            dao = DaoManager.createDao(database.getConnection(), Cachorro.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Cachorro.class);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Cachorro create(Cachorro cachorro) {
        try {
            dao.create(cachorro);
            cachorros.add(cachorro);
        } catch (SQLException e) {
            System.out.println("Erro ao criar cachorro: " + e.getMessage());
        }
        return cachorro;
    }

    public void update(Cachorro cachorro) {
        try {
            dao.update(cachorro);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cachorro: " + e.getMessage());
        }
    }

    public void delete(Cachorro cachorro) {
        try {
            dao.delete(cachorro);
            cachorros.remove(cachorro);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cachorro: " + e.getMessage());
        }
    }

    public Cachorro loadFromId(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println("Erro ao carregar cachorro: " + e.getMessage());
        }
        return null;
    }

    public List<Cachorro> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar todos os cachorros: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}