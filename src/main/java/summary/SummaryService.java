package summary;

import category.Category;
import category.CategoryDao;
import expense.Expense;
import expense.ExpenseDao;
import income.Income;
import income.IncomeDao;

import java.math.BigDecimal;
import java.util.List;

public class SummaryService {
    private ExpenseDao expenseDao;
    private IncomeDao incomeDao;
    private CategoryDao categoryDao = new CategoryDao();

    public SummaryService(ExpenseDao expenseDao, IncomeDao incomeDao) {
        this.expenseDao = expenseDao;
        this.incomeDao = incomeDao;
    }

    public String getBalance() {
        List<Expense> expenses = expenseDao.findAll();
        List<Income> incomes = incomeDao.findAll();
        BigDecimal totalExpenses = expenses.stream()
                .map(e -> e.getAmount())
                .reduce(BigDecimal.ZERO, (currentTotalExpenses, streamElement) -> currentTotalExpenses.add(streamElement));
        BigDecimal totalIncomes = incomes.stream()
                .map(Income::getAmountIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal balance = totalIncomes.subtract(totalExpenses);
        return balance.toString();
    }

    public BigDecimal getSummaryFromCategory(String categoryName) {
        Category category = categoryDao.findByName(categoryName);
        List<Expense> expensesFromCategory = expenseDao.findExpensesByCategories(category);
        BigDecimal summaryFromCategories = expensesFromCategory.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return summaryFromCategories;
    }
}

