
### Тема: Концепты проектирования. ООП. SOLID
### Домашнее задание 8

* Написать эмулятор АТМ (банкомата). Объект класса АТМ должен уметь:
* Принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка).
* Выдавать запрошенную сумму минимальным количеством банкнот или ошибку, если сумму нельзя выдать.
Это задание не на алгоритмы, а на проектирование. Поэтому оптимизировать выдачу не надо.
* Выдавать сумму остатка денежных средств.
* UI не нужен ни в каком виде (НЕ надо консоль, web, swing)
* Надо продемонстрировать работу в main(), а лучше в тестах.
* Не надо: пользователи, авторизация, клавиатура, дисплей, валюта, счет, карта и тп

### Сделано:
Классы, интерфейсы ..:
- Banknote 
- и его наследники для купюр разных номиналов (TenBanknote, FiftyBanknote, HundredBanknote, TwoHundredBanknote, FiveHundredBanknote, ThousandBanknote, TwoThousandBanknote, FiveThousandBanknote)
- SimpleATM, реализующий интерфейс ATMService

Тесты:
* testGetCashInATM - проверка баланса
* testWithdrawSuccessful - успешное снятие наличных
* testWithdrawMultipleDenominations - успешное снятие наличных (несколько купюр одного номинала)
* testWithdrawInsufficientFunds - неудачное снятие наличных (недостаточно средств в банкомате)
* testWithdrawExactChangeNotDispensed - неудачное снятие наличных (недостаточно купюр, чтобы выдать точную сумму)
