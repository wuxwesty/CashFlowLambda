package model;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CashFlow {

    private Date StartDate;
    private Date EndDate;
    private String Description;
    private boolean IsSubTotal;
    private double Amount;
    private double Amount1;
    private double Amount2;
    private double Amount3;
    private double Amount4;
    private double Amount5;
    private String Account1;
    private String Account2;
    private String Account3;
    private String Account4;
    private String Account5;

    public CashFlow() {
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public String getStartDateAWS() { return new SimpleDateFormat("yyyy-MM-dd").format(this.getStartDate());}

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getDescription() { return Description; }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isSubTotal() {
        return IsSubTotal;
    }

    public void setSubTotal(boolean subTotal) {
        IsSubTotal = subTotal;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public double getAmount1() {
        return Amount1;
    }

    public void setAmount1(double amount1) {
        Amount1 = amount1;
    }

    public double getAmount2() {
        return Amount2;
    }

    public void setAmount2(double amount2) {
        Amount2 = amount2;
    }

    public double getAmount3() {
        return Amount3;
    }

    public void setAmount3(double amount3) {
        Amount3 = amount3;
    }

    public double getAmount4() {
        return Amount4;
    }

    public void setAmount4(double amount4) {
        Amount4 = amount4;
    }

    public double getAmount5() {
        return Amount5;
    }

    public void setAmount5(double amount5) {
        Amount5 = amount5;
    }

    public String getAccount1() {
        return Account1;
    }

    public void setAccount1(String account1) {
        Account1 = account1;
    }

    public String getAccount2() {
        return Account2;
    }

    public void setAccount2(String account2) {
        Account2 = account2;
    }

    public String getAccount3() {
        return Account3;
    }

    public void setAccount3(String account3) {
        Account3 = account3;
    }

    public String getAccount4() {
        return Account4;
    }

    public void setAccount4(String account4) {
        Account4 = account4;
    }

    public String getAccount5() {
        return Account5;
    }

    public void setAccount5(String account5) {
        Account5 = account5;
    }
}
