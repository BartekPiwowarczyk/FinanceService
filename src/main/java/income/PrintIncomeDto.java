package income;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PrintIncomeDto extends IncomeDto {
    private Long id;
    private LocalDate date;

    public PrintIncomeDto(BigDecimal amountIncome, String comment, Long id, LocalDate date) {
        super(amountIncome, comment);
        this.id = id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Przychód = {" +
                "id: " + id +
                ", wielość wydatku:" + getAmountIncome() +
                ", komentarz: " + getComment() +
                ", date: " + date +
                '}';
    }
}
