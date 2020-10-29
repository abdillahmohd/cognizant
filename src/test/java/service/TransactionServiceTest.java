package service;

import exception.ImportCsvDataException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repo.Storage;

import java.math.BigDecimal;

public class TransactionServiceTest {

    TransactionService transactionService;

    @Before
    public void init() {
        Storage.initData();
    }

    @Test
    public void testTransactionServiceGenerateReport() {
        transactionService = new TransactionService();
        transactionService.processTransactionsFile("/Users/Shared/Documents/cognizant/src/test/resources/transactions.txt");
        Assert.assertEquals(new BigDecimal(21200.00).setScale(2, BigDecimal.ROUND_HALF_DOWN), transactionService.generateReport().getTotalIncome());
        Assert.assertEquals(new BigDecimal(16042.99).setScale(2, BigDecimal.ROUND_HALF_DOWN), transactionService.generateReport().getTotalExpenses());
        Assert.assertEquals(new BigDecimal(5157.01).setScale(2, BigDecimal.ROUND_HALF_DOWN), transactionService.generateReport().getTotalSavings());
        Assert.assertEquals("6901.89 @Grocery", transactionService.generateReport().getTotalExpenseCategory());
    }


    @Test(expected = ImportCsvDataException.class)
    public void testTransactionServiceGenerateReportWithParseException() {
        transactionService = new TransactionService();
        transactionService.processTransactionsFile("/Users/Shared/Documents/cognizant/src/test/resources/transactions_error.txt");
    }


}