package model.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Avaliacao;
import java.util.List;


public class AvaliacaoRepository {

    private EntityManager em;

    public AvaliacaoRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Avaliacao a) {
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
    }

    public void update(Avaliacao a) {
        em.getTransaction().begin();
        em.merge(a); 
        em.getTransaction().commit();
    }

    public void delete(Avaliacao a) {
        em.getTransaction().begin();
        Avaliacao ref = em.find(Avaliacao.class, a.getId_avaliacao());
        if (ref != null) {
            em.remove(ref);
        }
        em.getTransaction().commit();
    }

    public Avaliacao find(int id) {
        return em.find(Avaliacao.class, id); 
    }

    public List<Avaliacao> findAll() {
        TypedQuery<Avaliacao> query = em.createQuery(
                "SELECT a FROM Avaliacao a ORDER BY a.data DESC", Avaliacao.class);
        return query.getResultList();
    }
}
