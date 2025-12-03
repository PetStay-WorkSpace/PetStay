package model.dao;

import model.Proprietario;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import security.PasswordUtil;

/**
 * @author lohra
 */
public class ProprietarioDAO implements IDao<Proprietario> {

    private EntityManager entityManager;

    public ProprietarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Proprietario p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } 
    }

    @Override
    public void delete(Proprietario p) {
        try {
            entityManager.getTransaction().begin();
            Proprietario ref = entityManager.find(Proprietario.class, p);
            if(ref != null) {
                entityManager.remove(ref);
            } else {
                System.out.println(" Proprietario nao encontrado");
            } 
            entityManager.getTransaction().commit();
        } catch(Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Proprietario find(int id) {
        return entityManager.find(Proprietario.class, id);
    }

    @Override
    public List<Proprietario> findAll() {
        String jpql = "SELECT p FROM Proprietario p ORDER BY p.id ASC";
        TypedQuery<Proprietario> query = entityManager.createQuery(jpql, Proprietario.class);
        return query.getResultList();
    }

    public Proprietario validateLogin(String email, String senhaPlain) {
        try {
            String jpql = "SELECT p FROM Proprietario p WHERE p.email = :email";
            TypedQuery<Proprietario> query = entityManager.createQuery(jpql, Proprietario.class);
            query.setParameter("email", email);

            List<Proprietario> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                Proprietario p = resultados.get(0);
                if (PasswordUtil.verificarSenha(senhaPlain, p.getSenha())) {
                    return p;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar login: " + e.getMessage(), e);
        }
    }

}


/*
public Proprietario validateLogin(String email, String senha) {
    Proprietario resultado = null; 
    sql = "SELECT * FROM proprietario WHERE email = ? AND senha = ?"; 
    try {
        connection = Persistencia.getConnection();
        statement = connection.prepareStatement(sql); 
        statement.setString(1, email); 
        statement.setString(2, senha); 
        ResultSet rs = statement.executeQuery(); 
        if (rs.next()) { 
            resultado = new Proprietario( rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("senha"), rs.getString("cpf"), rs.getBoolean("ativo") ); 
        } statement.close(); 
    } catch (SQLException e) { 
        throw new RuntimeException("Erro ao buscar Proprietário por email/senha: " + e.getMessage(), e); 
    } finally { 
        Persistencia.closeConnection(); } 
    return resultado; } 
}
*/