package income;


import lombok.NoArgsConstructor;
import validation.ValidationMessage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
public class IncomeService {
    private IncomeDao incomeDao;

    public IncomeService(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public void addIncome(IncomeDto incomeDto)throws IllegalArgumentException {
        ValidationMessage validationMessage = validateIncomeDto(incomeDto);
        if(validationMessage.isValidationResult()) {
            Income income = new Income(incomeDto.getAmountIncome(), incomeDto.getComment());
            incomeDao.insert(income);
        } else {
            throw new IllegalArgumentException(validationMessage.getMessage());
        }
    }

    private ValidationMessage validateIncomeDto(IncomeDto incomeDto) {
        ValidationMessage validationMessage = new ValidationMessage();
        validationMessage.setValidationResult(true);
        if (incomeDto == null || incomeDto.getAmountIncome() == null) {
            validationMessage.setValidationResult(false);
            validationMessage.setMessage(ValidationMessage.MISSING_FIELDS);
        }
        return validationMessage;

    }

    public void deleteIncome(Long incomeId) {
        if (incomeId != null) {
            incomeDao.deleteById(incomeId);
        }
    }

    public List<PrintIncomeDto> getAllIncomes() {
        List<Income> incomes = incomeDao.findAll();
        return incomes.stream()
                .map(i -> new PrintIncomeDto(i.getAmountIncome(), i.getComment(), i.getId(), i.getDate()))
                .collect(Collectors.toList());
    }
}
