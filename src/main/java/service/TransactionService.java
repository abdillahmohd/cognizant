package service;

import exception.ImportCsvDataException;
import model.Report;
import model.Transaction;
import repo.Storage;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TransactionService {
    Logger logger = Logger.getLogger(TransactionService.class.getName());

    List<Transaction> transactionList = new ArrayList<>();

    public void processTransactionsFile(String fileName) {
        Storage.initData();
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(new File(fileName).getAbsoluteFile().toPath());
        } catch (IOException e) {
            logger.severe("Error in reading file " + fileName);
            throw new ImportCsvDataException(e.getMessage());
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines  = bufferedReader.lines().collect(Collectors.toList());
        for(String s : lines) {
            try {
                transactionList.add(processLine(s));
            } catch (ParseException exception) {
                logger.severe("Error in parsing date in line " + s);
                throw new ImportCsvDataException(exception.getMessage());
            }
        }
        for (Transaction transaction : transactionList) {
            if (transaction.getValue().compareTo(new BigDecimal(0)) < 0) {
                Storage.totalExpenses = Storage.totalExpenses.add(transaction.getValue());
            } else {
                Storage.totalIncome = Storage.totalIncome.add(transaction.getValue());
            }

            if (Storage.categoryToTransactionsMap.get(transaction.getCategory()) == null) {
                Storage.categoryToTransactionsMap.put(transaction.getCategory(), transaction.getValue());
            } else {
                BigDecimal existingValue = Storage.categoryToTransactionsMap.get(transaction.getCategory());
                existingValue = existingValue.add(transaction.getValue());
                Storage.categoryToTransactionsMap.put(transaction.getCategory(), existingValue);
            }


        }
    }


    private Transaction processLine(String s) throws ParseException {
        String[] tokens = s.split(",");
        BigDecimal value = new BigDecimal(tokens[1]);
        String category = tokens[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date transactionDate = null;
        try {
            transactionDate = simpleDateFormat.parse(tokens[0]);
        } catch (ParseException exception) {
            throw exception;
        }

        return new Transaction(category, value, transactionDate);

    }

    public Report generateReport() {
        BigDecimal min = new BigDecimal(Integer.MAX_VALUE);
        String minCategory = null;
        for (String category : Storage.categoryToTransactionsMap.keySet()) {
            BigDecimal transactionValue = Storage.categoryToTransactionsMap.get(category);
            if (transactionValue.compareTo(min) < 0) {
                min = transactionValue;
                minCategory = category;
            }
        }
        return new Report(Storage.totalIncome.setScale(2, BigDecimal.ROUND_HALF_DOWN),
                Storage.totalExpenses.abs().setScale(2, BigDecimal.ROUND_HALF_DOWN),
                Storage.totalIncome.add(Storage.totalExpenses).setScale(2, BigDecimal.ROUND_HALF_DOWN),
                Math.abs(min.doubleValue()) + " " + "@" + minCategory);
    }

}
