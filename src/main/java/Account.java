import java.util.*;

public class Account {
    private String clientFIO;
    private Map<CurrencyKind, Integer> currency;
    private Deque<Command> histAccount; // история изменений объекта
    public List<Save> savepointAccount;

    public Account(String clientFIO) {
        if (clientFIO == null || clientFIO.isEmpty()) throw new IllegalArgumentException("Ошибка. Наименование клиента не должно быть пустым");
        this.clientFIO = clientFIO;
        currency = new HashMap<>();
        histAccount = new ArrayDeque<>();
        savepointAccount = new ArrayList<>();
    }

    public String getNameClient() {
        return this.clientFIO;
    }

    public void setNameClient(String clientFIO) {
        if (clientFIO == null || clientFIO.isEmpty()) throw new IllegalArgumentException("Ошибка. Наименование клиента не должно быть пустым");
        if (!this.clientFIO.equals(clientFIO)) {
            String tmpName = Account.this.clientFIO;
            histAccount.push(()->Account.this.clientFIO=tmpName);

            this.clientFIO = clientFIO;
        }
    }


    public void addCurrencyVal(CurrencyKind cur, Integer new_value) {
        if (new_value < 0) throw new IllegalArgumentException("Ошибка. Значение валюты должно быть больше 0");

        Integer tmpName = currency.get(cur);
        histAccount.push(()->currency.put(cur, tmpName));

        currency.put(cur, new_value);
    }


    public Integer getCurrencyVal(CurrencyKind cur) {
        return currency.get(cur);
    }

    public Map<CurrencyKind, Integer> getCurrency() {
        return new HashMap<>(currency);
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

    public void undo (){
        if (histAccount.isEmpty()) throw new RuntimeException("История изменений пуста");
        histAccount.pop().make();
    }

    interface Command{
        public void make();
    }

    public Save save() {
        return new SP();
    }

     private class SP implements Save {
        private String clientFIO = Account.this.clientFIO;
        private final Map<CurrencyKind, Integer> currency = new HashMap<>(Account.this.currency);

        public void load() {
            Account.this.clientFIO = clientFIO;
            Account.this.currency.clear();
            Account.this.currency.putAll(currency);
        }


        public void print() {
            System.out.println("--------------------------------------");

            System.out.println("Клиент " + clientFIO);
            for(Map.Entry<CurrencyKind, Integer> entry: currency.entrySet()) {
                CurrencyKind key = entry.getKey();
                Integer val = entry.getValue();
                System.out.println("На счете "+ val + " " +key.toString());
            }
        }
    }

    public void printSavepointAccount() {
        for (Save sp : savepointAccount) {
          sp.print();
        }
    }
}



