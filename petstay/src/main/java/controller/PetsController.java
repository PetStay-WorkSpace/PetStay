package controller;

import factory.DatabaseJPA;
import model.Pets;
import model.dao.PetsDAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

public class PetsController {

    private final PetsDAO petsDAO;
    private final EntityManager entityManager;

    public PetsController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.petsDAO = new PetsDAO(entityManager);
    }

    public boolean save(int dono, String nome, String raca, String especie) {
        try {
            Pets pet = new Pets(0, dono, nome, raca, especie);
            petsDAO.save(pet);
            System.out.println("Pet salvo com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao salvar pet: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            Pets pet = new Pets();
            pet.setId(id);
            petsDAO.delete(pet);
            System.out.println("Pet deletado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar pet: " + e.getMessage());
            return false;
        }
    }

    public Pets find(int id) {
        return petsDAO.find(id);
    }

    public List<Pets> findAll() {
        return petsDAO.findAll();
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println("EntityManager fechado com sucesso.");
        }
    }
    
    public void carregarTabela(DefaultTableModel model) {
    List<Pets> lista = findAll();
    model.setRowCount(0);

    for (Pets p : lista) {
        model.addRow(new Object[]{
            p.getId(),
            p.getDono(),
            p.getNome(),
            p.getRaca(),
            p.getEspecie()
        });
    }
}

}
