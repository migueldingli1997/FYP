package transactionsystem;

public class UserAccount {
    protected boolean opened;
    protected String account_number;
    protected double balance;
    protected Integer owner;
    protected UserInfo ownerInfo;

    public UserAccount(Integer uid, String anumber, UserInfo userInfo) {
        account_number = anumber;
        balance = 0;
        opened = false;
        owner = uid;
        ownerInfo = userInfo;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public double getBalance() {
        return balance;
    }

    public Integer getOwner() {
        return owner;
    }

    public void activateAccount() {
        opened = true;
    }

    public void closeAccount() {
        opened = false;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public UserInfo getOwnerInfo() {
        return ownerInfo;
    }
}
