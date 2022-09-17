package expense;

import category.Category;
import config.ConnectionManager;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpenseDao {

    public void insert(Expense expense) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(expense);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteById(Long expenseId) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.getTransaction().begin();
        Expense expense = entityManager.find(Expense.class, expenseId);
        if (expense != null) {
            entityManager.remove(expense);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Expense> findAll() {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        List<Expense> expenses = entityManager.createQuery("select e from Expense e").getResultList();
        entityManager.close();
        return expenses;
    }


    public List<Expense> findExpenseByDate(LocalDate localDateBegin, LocalDate localDateEnd) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        List<Expense> expenseBetweenDate = entityManager.createQuery("select e from Expense e where e.createDate>=?1 and e.createDate<=?2")
                .setParameter(1,localDateBegin)
                .setParameter(2, localDateEnd)
                .getResultList();
        entityManager.close();
        return expenseBetweenDate;
    }


    public List<Expense> findExpensesByCategories(Category category) {
        EntityManager entityManager = ConnectionManager.getEntityManager();
        List<Expense> expenses = entityManager.createQuery("select e from Expense e where e.category=?1")
                .setParameter(1, category)
                .getResultList();
        entityManager.close();
        return expenses;
    }
}
