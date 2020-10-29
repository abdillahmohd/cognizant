import repo.Storage;
import service.TransactionService;

import java.io.IOException;
import java.text.ParseException;

public class MainClass {


    public static void main(String[] args) throws IOException, ParseException {
        TransactionService transactionService = new TransactionService();
        transactionService.processTransactionsFile("/transactions.txt");

    }


}
