package main;

import category.CategoryDao;
import category.CategoryService;
import expense.*;
import income.*;
import summary.SummaryDto;
import summary.SummaryService;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CategoryDao categoryDao = new CategoryDao();
        CategoryService categoryService = new CategoryService(categoryDao);

        List<String> categories;

        ExpenseDao expenseDao = new ExpenseDao();
        ExpenseService expenseService = new ExpenseService(expenseDao, categoryDao);

        IncomeDao incomeDao = new IncomeDao();
        IncomeService incomeService = new IncomeService(incomeDao);

        SummaryService summaryService = new SummaryService (expenseDao, incomeDao);

        categories = categoryService.getAllCategories();

        while (true) {
            System.out.println("Podaj operacje do wykonania:");
            System.out.println("0 - Zakończ program");
            System.out.println("1 - Dodawanie nowego wydatku");
            System.out.println("2 - Dodawanie nowego przychodu");
            System.out.println("3 - Usuwanie wydatku");
            System.out.println("4 - Usuwanie przychodu");
            System.out.println("5 - Wyświetlanie wszystkich wydatków");
            System.out.println("6 - Wyświetlanie wszystkich przychodów i wydatków");
            System.out.println("7 - Wyświetlanie wydatków na podstawie dat");
            System.out.println("8 - Wyświetlanie wydatków na podstawie wybranej kategorii");
            System.out.println("9 - Wyświetlanie sumę wydatków w danej kategorii");
            System.out.println("10 - Wyświetlanie wszystkich przychodów");
            System.out.println("11 - Wyświetlanie salda");
            System.out.println("12 - Dodawanie nowej kategorii");
            System.out.println("13 - Usunięcie istniejącej kategorii");

            Scanner in = new Scanner(System.in);
            switch (in.nextInt()) {
                case 0 -> System.exit(0);
                case 1 -> {
                    System.out.println("Podaj kwote wydatku: ");
                    BigDecimal amount = in.nextBigDecimal();
                    System.out.println("Dodaj komentarz: ");
                    in.nextLine();
                    String comment = in.nextLine();
                    categories = categoryService.getAllCategories();
                    System.out.println("Podaj kategorie: " + categories.toString());
                    String category = in.nextLine();
                    System.out.println("Podaj date wydatku (YYYY-MM-DD)");
                    String date = in.next();
                    ExpenseDto expenseDto = new ExpenseDto(amount, comment, category, date);
                    expenseService.addExpense(expenseDto);
                }
                case 2 -> {
                    System.out.println("Podaj kwote przychodu: ");
                    BigDecimal amountIncome = in.nextBigDecimal();
                    System.out.println("Podaj komentarz: ");
                    in.nextLine();
                    String comment = in.nextLine();
                    IncomeDto incomeDto = new IncomeDto(amountIncome, comment);
                    try {
                        incomeService.addIncome(incomeDto);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Podaj id wydatku który chcesz usunąć");
                    Long expenseId = in.nextLong();
                    expenseService.deleteExpense(expenseId);
                }
                case 4 -> {
                    System.out.println("Podaj id przychodu który chcesz usunąć");
                    Long incomeId = in.nextLong();
                    incomeService.deleteIncome(incomeId);
                }
                case 5 -> {
                    List<PrintExpenseDto> expenses = expenseService.getAllExpenses();
                    expenses.forEach(System.out::println);
                }
                case 6 -> {
                    List<PrintIncomeDto> incomes = incomeService.getAllIncomes();
                    incomes.forEach(System.out::println);
                    List<PrintExpenseDto> expenses = expenseService.getAllExpenses();
                    expenses.forEach(System.out::println);
                }
                case 7 -> {
                    System.out.println("Podaj date od dnia(yyyy-mm-dd): ");
                    String dateBegin = in.next();
                    System.out.println("do dnia (yyyy-mm-dd): ");
                    String dateEnd = in.next();
                    List<PrintExpenseDto> expensesBetweenDate = expenseService.getExpensesByDate(dateBegin, dateEnd);
                    expensesBetweenDate.forEach(System.out::println);
                }
                case 8 -> {
                    System.out.println("Podaj kategorie z której chcesz zobaczyć wydatki: " + categories);
                    String categoryFromUser = in.next();
                    List<PrintExpenseDto> expensesByCategories = expenseService.getExpensesByCategory(categoryFromUser);
                    expensesByCategories.forEach(System.out::println);
                }
                case 9 -> {
                    categories = categoryService.getAllCategories();
                    System.out.println("Z jakiej kategorii wyświetlić sumę wydatków?" + categories);
                    in.nextLine();
                    String categoryName = in.nextLine();
                    BigDecimal summary = summaryService.getSummaryFromCategory(categoryName);
                    SummaryDto summaryDto = new SummaryDto(summary.toString());
                    System.out.println("Suma wydatków z kategorii " + categoryName + " wynosi: " + summaryDto);

                }
                case 10 -> {
                    List<PrintIncomeDto> incomes = incomeService.getAllIncomes();
                    incomes.forEach(System.out::println);
                }
                case 11 -> {
                    String balance = summaryService.getBalance();
                    System.out.println("Twoje saldo wynosi: " + balance + " zł");
                }
                case 12 -> {
                    System.out.println("Podaj nazwę nowej kategorii: ");
                    in.nextLine();
                    String categoryName = in.nextLine();
                    categoryService.addCategory(categoryName);
                    System.out.println("Kategoria " + categoryName + " została dodana");
                }
                case 13 -> {
                    categories = categoryService.getAllCategories();
                    System.out.println("Podaj nazwę kategorii do usunięcia: " + categories);
                    in.nextLine();
                    String categoryName = in.nextLine();
                    try {
                        categoryService.deleteCategory(categoryName);
                        System.out.println("Kategoria: " + categoryName + " została usunięta");
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Czy usunąć wszystkie wydatki z kategorii? YES/NO");
                        String answer = in.next();
                        expenseService.deleteAllExpensesFromCategory(categoryName);
                        try {
                            categoryService.deleteCategory((categoryName));
                            System.out.println("Kategoria: " + categoryName + " została usunięta");
                        } catch (SQLIntegrityConstraintViolationException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }


                }
            }
        }
    }
}
