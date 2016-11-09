import spock.lang.Specification
import spock.lang.Unroll;
import Card;

class AtmTest extends Specification {

    @Unroll
    def "get Account balance with valid card and pin is good"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = (String) atm.getAccountBalance(card);

        then: "The request should succeed"
        assert balance != null;
        assert balance == String.valueOf(openingBalance);
    }

    @Unroll
    def "get Account balance with valid card with an invalid PIN should fail"() {

        given: "a valid card with an invalid pin"
        float openingBalance = 200;
        int invalidPin = 5555;
        Card card = new Card("9876345609875678", 10, 2020, invalidPin, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = (String) atm.getAccountBalance(card);

        then: "The request should fail"
        assert balance != null;
        assert balance == "Invalid Pin";

    }

    @Unroll
    def "get Account balance with an Invalid card should fail"() {

        given: "a valid card with an invalid pin"
        float openingBalance = 200;
        String cardNumber = "9876345609899999"
        int pin = 5555;
        Card card = new Card(cardNumber, 10, 2020, pin, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = (String) atm.getAccountBalance(card);

        then: "The request should fail"
        assert balance != null;
        assert balance == "Invalid Credit Card"

    }

    @Unroll
    def "get Account balance with an expired date - year - card is bad "() {

        given: "a card with an expired date"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2015, 1234, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = atm.getAccountBalance(card);

        then: "The request should fail"
        assert balance != null;
        assert balance == "Invalid Credit Card"

    }

    @Unroll
    def "get Account balance with an expired date - same month - card is bad "() {

        given: "a card with an expired date"
        float openingBalance = 200;
        int month = 11; //current month 11
        Card card = new Card("9876345609875678", month, 2016, 1234, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = atm.getAccountBalance(card);

        then: "The request should fail"
        assert balance != null;
        assert balance == "Invalid Credit Card"

    }

    @Unroll
    def "get Account balance with an expired date - previous month - card is bad "() {

        given: "a card with an expired date - previous month"
        float openingBalance = 200;
        int month = 10; //current month 11
        Card card = new Card("9876345609875678", month, 2016, 1234, openingBalance);

        when: "A get balance call is executed"
        ATM atm = new ATM();
        String balance = atm.getAccountBalance(card);

        then: "The request should fail"
        assert balance != null;
        assert balance == "Invalid Credit Card"

    }

    @Unroll
    def "withdraw funds from card with positive balance should be good"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A withdraw cash call is executed with available balance"
        ATM atm = new ATM();
        float withdrawCash = 100;
        PairCardBalance cardBalance = atm.withdrawCash(card, withdrawCash);

        then: "the new balance is calculated"
        float newbalance = openingBalance - withdrawCash;

        then: "The request should succeed"
        assert cardBalance != null;
        assert cardBalance.balance == String.valueOf(newbalance);
    }

    @Unroll
    def "withdraw entire funds from card with positive balance should be ok"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A withdraw cash call is executed with available balance"
        ATM atm = new ATM();
        float withdrawCash = 200;
        PairCardBalance cardBalance = atm.withdrawCash(card, withdrawCash);

        then: "the new balance is calculated"
        float newbalance = openingBalance - withdrawCash;

        and: "the new balance should be zero"
        assert newbalance == 0.0

        then: "The request should succeed"
        assert cardBalance != null;
        assert cardBalance.balance == String.valueOf(newbalance);
    }

    @Unroll
    def "withdraw more funds than the available balance from card is not good"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A withdraw cash call is executed requesting more funds than the available balance"
        ATM atm = new ATM();
        float withdrawCash = 400;
        PairCardBalance cardBalance = atm.withdrawCash(card, withdrawCash);

        then: "The request should fail"
        assert cardBalance != null;
        assert cardBalance.balance == "you don't have enough funds";
    }

    @Unroll
    def "withdraw funds from card with invalid pin should fail"() {

        given: "a valid card with an invalid pin"
        float openingBalance = 200;
        int invalidPin = 9999;
        Card card = new Card("9876345609875678", 10, 2020, invalidPin, openingBalance);

        when: "A withdraw cash call is executed"
        ATM atm = new ATM();
        float withdrawCash = 100;
        PairCardBalance cardBalance = atm.withdrawCash(card, withdrawCash);

        then: "The request failed for invalid Pin"
        assert cardBalance != null;
        assert cardBalance.balance == "Invalid Pin";
    }

    @Unroll
    def "withdraw funds from invalid card should fail"() {

        given: "an invalid card number"
        float openingBalance = 200;
        String invalidCard = "4343434344444111";
        Card card = new Card(invalidCard, 10, 2020, 4561, openingBalance);

        when: "A withdraw cash call is executed"
        ATM atm = new ATM();
        float withdrawCash = 100;
        PairCardBalance cardBalance = atm.withdrawCash(card, withdrawCash);

        then: "The request should fail"
        assert cardBalance != null;
        assert cardBalance.balance == "Invalid Credit Card";
    }

    @Unroll
    def "do Deposit of a valid amount should be good"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A deposit call is executed"
        ATM atm = new ATM();
        float amountToDeposit = 100;
        PairCardBalance cardBalance = atm.doDeposit(card, amountToDeposit);

        and: "the new balance is calculated"
        float newbalance = openingBalance + amountToDeposit;

        then: "The request should succeed"
        assert cardBalance != null;
        assert cardBalance.balance == String.valueOf(newbalance);
    }

    @Unroll
    def "do Deposit of amount zero should fail"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A deposit call is executed"
        ATM atm = new ATM();
        float amountToDeposit = 0;
        PairCardBalance cardBalance = atm.doDeposit(card, amountToDeposit);

        and: "the new balance is calculated"
        float balance = openingBalance + amountToDeposit;

        then: "The request failed"
        assert cardBalance != null;
        assert cardBalance.balance == "amount to deposit must be greater than 0";

        and: "the balance is still the same"
        assert cardBalance.card.balance == balance;
    }

    @Unroll
    def "do Deposit of a negative value should fail"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 200;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A deposit call is executed"
        ATM atm = new ATM();
        float amountToDeposit = -350;
        PairCardBalance cardBalance = atm.doDeposit(card, amountToDeposit);

        then: "The request failed"
        assert cardBalance != null;
        assert cardBalance.balance == "amount to deposit must be greater than 0";

        and: "the balance is still the same"
        assert cardBalance.card.balance == openingBalance;
    }

    @Unroll
    def "a valid work flow should be good"() {

        given: "a valid card with a valid pin and a valid account"
        float openingBalance = 100;
        Card card = new Card("9876345609875678", 10, 2020, 1234, openingBalance);

        when: "A balance check is executed"
        ATM atm = new ATM();
        String balance = (String) atm.getAccountBalance(card);

        then: "The request should succeed"
        assert balance != null;
        assert balance == String.valueOf(openingBalance);

        and: "a deposit wants to be maid"
        float amountToDeposit = 1000;

        then: "A deposit call is executed"
        PairCardBalance cardBalance = atm.doDeposit(card, amountToDeposit);

        and: "the balance is updated"
        float newBalance = openingBalance + amountToDeposit;

        then: "the balance in the card is correct"
        assert cardBalance.card.balance == newBalance;

        and: "a withdraw is executed"
        int amountToWithdraw = 300;
        PairCardBalance cardBalanceTwo = atm.withdrawCash(cardBalance.card, amountToWithdraw);

        then: "the balance is updated"
        float newBalanceTwo = newBalance - amountToWithdraw;

        and: "the withdraw is executed and the balance in the card is correct"
        assert cardBalanceTwo.card.balance == newBalanceTwo;

    }
}
