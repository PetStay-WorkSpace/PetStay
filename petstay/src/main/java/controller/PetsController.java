package controller;

import factory.DatabaseJPA;
import model.Pets;
import model.dao.PetsDAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

public class PetsController {

    private final PetsDAO petsDAO;

    public PetsController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.petsDAO = new PetsDAO(em);
    }

    public void save(Pets pet) {
       if (pet.getNome() == null || pet.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do pet é obrigatório.");
        }

        if (pet.getEspecie() == null || pet.getEspecie().isBlank()) {
            throw new IllegalArgumentException("Espécie do pet é obrigatória.");
        }

        petsDAO.save(pet);
    }

    public void delete(int id) {
       Pets pet = new Pets();
       pet.setId(id);
       petsDAO.delete(pet);
       
    }

    public Pets find(int id) {
        return petsDAO.find(id);
    }

    public List<Pets> findAll() {
        return petsDAO.findAll();
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
