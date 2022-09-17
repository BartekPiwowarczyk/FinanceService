package expense;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data

public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private String comment;
    private String category;
    private String createDate;

    public ExpenseDto(BigDecimal amount, String comment, String category, String date) {
        this.amount = amount;
        this.comment = comment;
        this.category = category;
        this.createDate = date;
    }
}
