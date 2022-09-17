package expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrintExpenseDto extends ExpenseDto {
    private Long id;


    public PrintExpenseDto(BigDecimal amount, String comment, String category,Long id, String date)  {
        super(amount, comment, category, date);
        this.id = id;
    }


    @Override
    public String toString() {
        return "Wydatek={" +
                "id=" + id +
                ", wielkość wydatku: " + getAmount() +
                ", komentarz: " + getComment() +
                ", kategoria: " + getCategory() +
                ", date=" + getCreateDate() +
                '}';
    }
}
