package expense;


import category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table (name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String comment;

    @Column (name = "expense_add_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;

    public Expense(BigDecimal amount, String comment, LocalDate createDate, Category category) {
        this.amount = amount;
        this.comment = comment;
        this.createDate = createDate;
        this.category = category;
    }
}
