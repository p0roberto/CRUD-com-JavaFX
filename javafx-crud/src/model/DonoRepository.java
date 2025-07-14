package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonoRepository {
    private static Database database;
    private static Dao<Dono, Integer> dao;
    private List<Dono> donos;

    public DonoRepository(Database database) {
        DonoRepository.database = database;
        donos = new ArrayList<>();
        try {
            dao = DaoManager.createDao(database.getConnection(), Dono.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Dono.class);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Dono create(Dono dono) {
        try {
            dao.create(dono);
            donos.add(dono);
        } catch (SQLException e) {
            System.out.println("Erro ao criar dono: " + e.getMessage());
        }
        return dono;
    }

    public void update(Dono dono) {
        try {
            dao.update(dono);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dono: " + e.getMessage());
        }
    }

    public void delete(Dono dono) {
        try {
            dao.delete(dono);
            donos.remove(dono);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar dono: " + e.getMessage());
        }
    }

    public Dono loadFromId(int id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println("Erro ao carregar dono: " + e.getMessage());
        }
        return null;
    }

    public Dono loadFromIdByName(String nome) {
        try {
            List<Dono> resultados = dao.queryForEq("nome", nome);
            if (!resultados.isEmpty()) {
                return resultados.get(0);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar dono por nome: " + e.getMessage());
        }
        return null;
    }

    public List<Dono> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar todos os donos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
