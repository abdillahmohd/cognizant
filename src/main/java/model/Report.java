package model;

import java.math.BigDecimal;

public class Report {


    public Report(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal totalSavings, String totalExpenseCategory) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.totalSavings = totalSavings;
        this.totalExpenseCategory = totalExpenseCategory;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getTotalSavings() {
        return totalSavings;
    }

    public String getTotalExpenseCategory() {
        return totalExpenseCategory;
    }

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal totalSavings;
    private String totalExpenseCategory;


}
