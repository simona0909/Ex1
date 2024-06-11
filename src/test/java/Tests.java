
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Tests {
    @Test
    @DisplayName("Проверка создания счета с нормальным наименованием клиента")
    public void createCorrectAccount () {
        Account acc = new Account("Vasya Pupkin");
        Assertions.assertNotNull(acc, "Ошибка при создании счета с правильным наименованием клиента");
    }

    @Test
    @DisplayName("Проверка наименованием клиента на возможность смены на пустое значение")
    public void createNotCorrectAccount () {
        Account acc = new Account("Vasya Pupkin");
        Assertions.assertThrows(IllegalArgumentException.class, ()->acc.setNameClient(""), "Ошибочно изменяется наименование клиента на счета на пустое");
    }

    @Test
    @DisplayName("Проверка на заведение значения валюты")
    public void addCorrectCurrency () {
        Account acc = new Account("Vasya Pupkin");
        Assertions.assertDoesNotThrow(()->acc.setCurVal(Currency.RUB, 100), "Ошибка при добавлении значения валюты");

        int val = acc.getCurVal(Currency.RUB);
        Assertions.assertEquals(val, 100);
    }
    @Test
    @DisplayName("Проверка на заведение некорректного значения валюты")
    public void addNotCorrectCurrency () {
        Account acc = new Account("Vasya Pupkin");
        Assertions.assertThrows(IllegalArgumentException.class, ()->acc.setCurVal(Currency.RUB, -1), "Ошибочно заведено некорректное значение валюты счета");
    }

    @Test
    @DisplayName("Проверка на сохранение истории изменения наименования клиента и изменение значение валюты")
    public void addSaveHistoryChanges () {
        List<HistoryAccount> lstHist;
        HistoryAccount ha;

        Account acc = new Account("Vasya Pupkin");

        acc.setNameClient("Vasilisa Pupkina");  // поменяли имя, в истории должна появиться запись
        lstHist = acc.getHistState();
        ha = lstHist.get(0);

        Assertions.assertEquals(ha.getTypeChange(), 1, "Тип значения в истории изменений не соответствует 1");
        Assertions.assertEquals(ha.getPrevClientName(), "Vasya Pupkin", "Предыдущее значение клиента сохранено неверно");

        acc.setCurVal(Currency.RUB, 100);
        ha = lstHist.get(1);
        Assertions.assertEquals(ha.getTypeChange(), 3, "Тип значения в истории изменений не соответствует 3");

        acc.setCurVal(Currency.RUB, 300);
        ha = lstHist.get(2);
        Assertions.assertEquals(ha.getTypeChange(), 2, "Тип значения в истории изменений не соответствует 2");
        Assertions.assertEquals(ha.getPrevVal(), 100, "Предыдущее значение валюты сохранено неверно");
    }

    @Test
    @DisplayName("Проверка на правильный откат изменений")
    public void undoChanges () {
        List<HistoryAccount> lstHist;
        HistoryAccount ha;

        Account acc = new Account("Vasya Pupkin");
        acc.setNameClient("Vasilisa Pupkina");  // поменяли имя, в истории должна появиться запись
        acc.setCurVal(Currency.RUB, 100);
        acc.setCurVal(Currency.RUB, 300);

        int val = acc.getCurVal(Currency.RUB);
        Assertions.assertEquals(val, 300, "Ошибочное значение валюты после изменений");
        Assertions.assertEquals(acc.getNameClient(), "Vasilisa Pupkina", "Ошибочное значение наименования клиента после изменений");
        acc.undo();
        val = acc.getCurVal(Currency.RUB);
        Assertions.assertEquals(val, 100, "Ошибочное значение валюты после отката");
        acc.undo();
        acc.undo();
        Assertions.assertEquals(acc.getNameClient(), "Vasya Pupkin", "Ошибочное значение наименования клиента после отката");
    }
}