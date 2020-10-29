package repo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static BigDecimal totalExpenses;
    public static BigDecimal totalIncome;
    public static Map<String, BigDecimal> categoryToTransactionsMap;

    public static void initData() {
        totalExpenses = new BigDecimal(0);
        totalIncome = new BigDecimal(0);
        categoryToTransactionsMap = new HashMap<>();
    }
}
