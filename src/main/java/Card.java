public class Card {

    private String cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private int cardPin;
    private float balance;

    public Card (String number, int month, int year, int pin, float bal) {
        cardNumber = number;
        expirationMonth = month;
        expirationYear = year;
        cardPin = pin;
        balance = bal;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public int getCardPin() {
        return cardPin;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float bal) {
        balance = bal;
    }
}
