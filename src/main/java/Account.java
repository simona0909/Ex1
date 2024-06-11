import java.util.*;

public class Account {
    private String clientFIO;
    private Map<CurrencyKind, Integer> currency;
    private Stack<Account> histAccount; // история изменений объекта
    private Map<Integer, Account> savepointAccount;

    public Account(String clientFIO) {
        if (clientFIO == null || clientFIO.isEmpty()) throw new IllegalArgumentException("Ошибка. Наименование клиента не должно быть пустым");
        this.clientFIO = clientFIO;
        currency = new HashMap<>();
        histAccount = new Stack<>();
        savepointAccount = new HashMap<>();
    }

    public String getNameClient() {
        return this.clientFIO;
    }

    public void setNameClient(String clientFIO) {
        if (clientFIO == null || clientFIO.isEmpty()) throw new IllegalArgumentException("Ошибка. Наименование клиента не должно быть пустым");
        if (!this.clientFIO.equals(clientFIO)) {
           this.clientFIO = clientFIO;
            histAccount.push(this);
        }
    }


    public void addCurrencyVal(CurrencyKind cur, Integer new_value) {
        if (new_value < 0) throw new IllegalArgumentException("Ошибка. Значение валюты должно быть больше 0");
        currency.put(cur, new_value);
        histAccount.push(this);
    }


    public Integer getCurrencyVal(Currency cur) {
        return currency.get(cur);
    }


    public void printCurVal() {
        System.out.println("------ На счете суммы в валютах -------");
        for(Map.Entry<CurrencyKind, Integer> entry: currency.entrySet()) {
            CurrencyKind key = entry.getKey();
            Integer val = entry.getValue();
            System.out.println("На счете "+ val + " " +key.toString());
        }
        System.out.println("--------------------------------------");
    }



    public void addSavepointAccount(Integer point, Account account) {
        savepointAccount.put(point, account);
    }

    public void printSavepointAccount() {
        System.out.println("------ Savepoint -------");
        for(Map.Entry<Integer, Account> entry: savepointAccount.entrySet()) {
            Integer key = entry.getKey();
            Account val = entry.getValue();
            System.out.println("Точка "+key.toString()/*+" "+ val.printCurVal() */);
            System.out.println("Клиент "+val.getNameClient());
            val.printCurVal();
        }
        System.out.println("--------------------------------------");
    }

    public void undo (){
        if (histAccount.isEmpty()) throw new RuntimeException("История изменений пуста");
        histAccount.pop();
    }
}


