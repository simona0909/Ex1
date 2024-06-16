
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Tests {
    @Test
    @DisplayName("Проверка создания счета с нормальным наименованием клиента")
    public void createCorrectAccount () {
        Account acc = new Account("Irina Ivanova");
        Assertions.assertNotNull(acc, "Ошибка при создании счета с правильным наименованием клиента");
    }

    @Test
    @DisplayName("Проверка наименованием клиента на возможность смены на пустое значение")
    public void createNotCorrectAccount () {
        Account acc = new Account("Irina Ivanova");
        Assertions.assertThrows(IllegalArgumentException.class, ()->acc.setNameClient(""), "Ошибочно изменяется наименование клиента на счета на пустое");
    }

    @Test
    @DisplayName("Проверка на заведение значения валюты")
    public void addCorrectCurrency () {
        Account acc = new Account("Irina Ivanova");
        Assertions.assertDoesNotThrow(()->acc.addCurrencyVal(CurrencyKind.RUB, 100), "Ошибка при добавлении значения валюты");

        int val = acc.getCurrencyVal(CurrencyKind.RUB);
        Assertions.assertEquals(val, 100);
    }
    @Test
    @DisplayName("Проверка на заведение некорректного значения валюты")
    public void addNotCorrectCurrency () {
        Account acc = new Account("Irina Ivanova");
        Assertions.assertThrows(IllegalArgumentException.class, ()->acc.addCurrencyVal(CurrencyKind.RUB, -1), "Ошибочно заведено некорректное значение валюты счета");
    }

    @Test
    @DisplayName("Проверка на правильный откат изменений")
    public void undoChanges () {
      /*  List<HistoryAccount> lstHist;
        HistoryAccount ha;*/

        Account acc = new Account("Irina Ivanova");
        acc.setNameClient("Inga Ivanova");  // поменяли имя, в истории должна появиться запись
        acc.addCurrencyVal(CurrencyKind.RUB, 100);
        acc.addCurrencyVal(CurrencyKind.RUB, 300);

        int val = acc.getCurrencyVal(CurrencyKind.RUB);
        Assertions.assertEquals(val, 300, "Ошибочное значение валюты после изменений");
        Assertions.assertEquals(acc.getNameClient(), "Inga Ivanova", "Ошибочное значение наименования клиента после изменений");
        acc.undo();
        val = acc.getCurrencyVal(CurrencyKind.RUB);
        Assertions.assertEquals(val, 100, "Ошибочное значение валюты после отката");
        acc.undo();
        acc.undo();
        Assertions.assertEquals(acc.getNameClient(), "Irina Ivanova", "Ошибочное значение наименования клиента после отката");
    }

    @Test
    @DisplayName("Проверка на сохранение истории")
    public void addSaveHistoryChanges () {
        Account acc = new Account("Irina Ivanova");
        acc.savepointAccount.add(()-> acc.save());

        boolean b = acc.savepointAccount.isEmpty();
        Assertions.assertNotEquals(b, true, "Ошибка. Пустой список сохранений");
    }
}