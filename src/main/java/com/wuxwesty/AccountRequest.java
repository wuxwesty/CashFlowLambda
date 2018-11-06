package com.wuxwesty;

public class AccountRequest {
    private Account account;
    public AccountRequest() {
    }
    public AccountRequest(Account account) {
        this.account = account;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
