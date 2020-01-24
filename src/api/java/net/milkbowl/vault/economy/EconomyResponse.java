package net.milkbowl.vault.economy;

/**
 * @author Himmelt
 */
public class EconomyResponse {
    public final double amount;
    public final double balance;
    public final EconomyResponse.ResponseType type;
    public final String errorMessage;

    public EconomyResponse(double amount, double balance, EconomyResponse.ResponseType type, String errorMessage) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    public boolean transactionSuccess() {
        return this.type == ResponseType.SUCCESS;
    }

    public enum ResponseType {
        SUCCESS(1),
        FAILURE(2),
        NOT_IMPLEMENTED(3);

        private int id;

        ResponseType(int id) {
            this.id = id;
        }
    }
}
