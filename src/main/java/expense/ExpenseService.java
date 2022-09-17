package expense;

import category.Category;
import category.CategoryDao;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor

public class ExpenseService {
    private ExpenseDao expenseDao;
    private CategoryDao categoryDao;
    public LocalDate date;


    public ExpenseService(ExpenseDao expenseDao, CategoryDao categoryDao) {
        this.expenseDao = expenseDao;
        this.categoryDao = categoryDao;
    }

    public void addExpense(@NotNull ExpenseDto expenseDto) {
            Category category = categoryDao.findByName(expenseDto.getCategory());
            Expense expense = new Expense(expenseDto.getAmount(), expenseDto.getComment(), LocalDate.parse(expenseDto.getCreateDate()), category);
            expenseDao.insert(expense);
    }

    public void deleteExpense(Long expenseId) {
        if(expenseId != null) {
            expenseDao.deleteById(expenseId);
        }
    }

    public List<PrintExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseDao.findAll();
        return expenses.stream()
                .map(expense -> new PrintExpenseDto(expense.getAmount(),expense.getComment(),expense.getCategory().getName(),expense.getId(),expense.getCreateDate().toString()))
                .collect(Collectors.toList());
    }


    public List<PrintExpenseDto> getExpensesByDate(String dateBegin, String dateEnd) {
        LocalDate localDateBegin = LocalDate.parse(dateBegin);
        LocalDate localDateEnd = LocalDate.parse(dateEnd);
        List<Expense> expensesByDate = expenseDao.findExpenseByDate(localDateBegin,localDateEnd);
        return expensesByDate.stream()
                .map(e -> new PrintExpenseDto(e.getAmount(),e.getComment(),e.getCategory().getName(),e.getId(),e.getCreateDate().toString()))
                .collect(Collectors.toList());
    }

    public List<PrintExpenseDto> getExpensesByCategory(String categoryFromUser) {
        Category category = categoryDao.findByName(categoryFromUser);
        List<Expense> expensesByCategory = expenseDao.findExpensesByCategories(category);
        return expensesByCategory.stream()
                .map(e -> new PrintExpenseDto(e.getAmount(),e.getComment(),e.getCategory().getName(),e.getId(),e.getCreateDate().toString()))
                .collect(Collectors.toList());
    }

    public void deleteAllExpensesFromCategory(String categoryName) {
        Category category = categoryDao.findByName(categoryName);
        List<Expense> expenses = expenseDao.findExpensesByCategories(category);
        List<Long> expensesId = expenses.stream().map(e->e.getId())
                .collect(Collectors.toList());
        for (Long element:expensesId) {
            deleteExpense(element);
        }
    }
}
