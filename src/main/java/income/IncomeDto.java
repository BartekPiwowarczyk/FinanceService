package income;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class IncomeDto {
    private Long id;
    private BigDecimal amountIncome;
    private String comment;

    public IncomeDto(BigDecimal amountIncome, String comment) {
        this.amountIncome = amountIncome;
        this.comment = comment;
    }
}
