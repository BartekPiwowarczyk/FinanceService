package income;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amountIncome;

    private String comment;

    @Column(name = "income_add_date")
    private LocalDate date;

    public Income(BigDecimal amountIncome, String comment) {
        this.amountIncome = amountIncome;
        this.comment = comment;
        this.date = LocalDate.now();
    }
}
