package category;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao=categoryDao;
    }

    public List<String> getAllCategories() {
        return categoryDao.findAllCategories();
    }

    public void addCategory(String categoryName) {
        Category category1 = categoryDao.findByName(categoryName);
        if (category1 == null) {
            Category category = new Category(categoryName);
            categoryDao.insert(category);
        }
    }

    public void deleteCategory(String categoryName) throws SQLIntegrityConstraintViolationException{
        if (categoryName != null) {
            Category category = categoryDao.findByName(categoryName);
            if (category.getExpenses().isEmpty() || category.getExpenses() == null) {
                categoryDao.deleteCategory(category);
            } else {
                throw new SQLIntegrityConstraintViolationException("Nie można usunąć kategorii. Najpierw usuń wydatki z kategorii");
            }
        }
    }
}
