import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ATM {

    public String getAccountBalance(Card card) {

        String balance = null;

        if (isValidCard(card)) {
            if (isValidPin(card)) {
                balance = String.valueOf(card.getBalance());
            } else {
                return "Invalid Pin";
            }
        } else {
            return "Invalid Credit Card";
        }

        return balance;

    }

    public PairCardBalance withdrawCash(Card card, float amount) {

        String balance = "";
        if (isValidCard(card)) {
            if (isValidPin(card)) {
                if (card.getBalance() >= amount) {
                    float newBalance = card.getBalance() - amount;
                    card.setBalance(newBalance);
                    balance = String.valueOf(newBalance);
                } else {
                    balance = "you don't have enough funds";
                }
            } else {
                balance = "Invalid Pin";
            }
        } else {
            balance = "Invalid Credit Card";
        }
        PairCardBalance cardBalance = new PairCardBalance(card, balance);

        return cardBalance;
    }

    public PairCardBalance doDeposit(Card card, float amount) {

        String balance = "";
        if (isValidCard(card)) {
            if (isValidPin(card)) {
                if (amount > 0) {
                    float newBalance = card.getBalance() + amount;
                    card.setBalance(newBalance);
                    balance = String.valueOf(newBalance);
                } else {
                    balance = "amount to deposit must be greater than 0";
                }
            } else {
                balance = "Invalid Pin";
            }
        } else {
            balance = "Invalid Credit Card";
        }
        PairCardBalance cardBalance = new PairCardBalance(card, balance);

        return cardBalance;
    }

    private boolean isValidPin(Card card) {
        boolean isValidPin = false;

        if (card.getCardPin()==MockValues.PIN_NUMBER) {
            isValidPin = true;
        }

        return isValidPin;
    }

    private boolean isValidCard(Card card) {
        boolean isValid = false;

        if (cardHasValidFormat(card) && cardHasValidExpirationDate(card)) {
            if (checkCardNumberInSystem(card)) {
                isValid = true;
            }
        }

        return isValid;
    }

    private boolean cardHasValidFormat(Card card) {
        boolean isValid = false;

        if (card.getCardNumber().length() == 16) {
            try {
                Long cardNumber = new Long(card.getCardNumber());
            } catch (NumberFormatException e) {
                return isValid;
            }
            isValid = true;
        }

        return isValid;
    }

    private boolean cardHasValidExpirationDate(Card card) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        if (card.getExpirationYear() < year) {
            return false;
        } else if (card.getExpirationYear() > year) {
            return true;
        } else if (card.getExpirationYear() == year) {
            if (card.getExpirationMonth() > (month+1)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean checkCardNumberInSystem(Card card) {
        boolean isValidCardNumber = false;

        if (card.getCardNumber().equalsIgnoreCase(MockValues.CARD_NUMBER)) {
            isValidCardNumber = true;
        }

        return isValidCardNumber;
    }
}
