package model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    public Transaction(String category, BigDecimal value, Date transactionDate) {
        this.category = category;
        this.value = value;
        this.transactionDate = transactionDate;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    private String category;
    private BigDecimal value;
    private Date transactionDate;


}
