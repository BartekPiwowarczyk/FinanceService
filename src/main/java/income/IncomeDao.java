package income;

import config.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class IncomeDao {
    public void insert (Income income) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(income);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteById(Long incomeId) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        Income income = entityManager.find(Income.class,incomeId);
        if (income != null) {
            entityManager.remove(income);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Income> findAll() {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        List<Income> incomes = entityManager.createQuery("select i from Income i").getResultList();
        entityManager.close();
        return incomes;
    }
}
