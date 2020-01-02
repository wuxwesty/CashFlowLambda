import com.wuxwesty.Account;
import com.wuxwesty.CashFlow;
import com.wuxwesty.CashFlows;
import com.wuxwesty.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class GetCashFlowsTest {

    private Logger log = Logger.getLogger("GetCashFlowsTest");

    List<Account> accountList = new ArrayList<Account>();
    List<Transaction> transactionList = new ArrayList<Transaction>();

    @Before
    public void Setup() {
        accountList.add ( new Account("", 1,"RBC Checking",1,250,0,1) );
        accountList.add ( new Account("", 2,"RBC Savings",1,63,0,2) );
        accountList.add ( new Account("", 3,"RBC Tax Free Savings Account",4,20000,0,5));
        accountList.add ( new Account("", 4,"RBC DirectInvesting RRSP",4,120000,0,6));
        accountList.add ( new Account("", 5,"SunLife Savings",4,11000,0,6));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            transactionList.add(new Transaction("", 1, 1, 0,
                    1, formatter.parse("2018-01-01"), formatter.parse("2018-06-30"),
                    3, 1, 1, 1, "Income", 3500.00));
            transactionList.add(new Transaction("", 2, 1, 0,
                    1, formatter.parse("2018-07-01"), formatter.parse("2018-12-31"),
                    3, 1, 1, 1, "Income", 3900.00));
            transactionList.add(new Transaction("", 3, 3, 1,
                    3, formatter.parse("2018-01-01"), formatter.parse("2018-12-31"),
                    3, 1, 1, 1, "TFSA", 200.00));
            transactionList.add(new Transaction("", 4, 4, 1,
                    1, formatter.parse("2018-01-01"), formatter.parse("2018-12-31"),
                    3, 1, 1, 1, "DB", 600.00));
            transactionList.add(new Transaction("", 5, 5, 1,
                    3, formatter.parse("2018-01-01"), formatter.parse("2018-12-31"),
                    3, 1, 1, 1, "Savings", 325.00));
        } catch (ParseException e) {

        }

    }

    @Test
    public void GetCashFlows() {
        CashFlows cfs = new CashFlows();
        List<CashFlow> cfList = cfs.Generate(accountList, transactionList);
        int expected = 13; //29; //57;
        int actual = cfList.size();
        for(CashFlow cf: cfList) {
            log.info(String.format("%tF %tF %s %s %f %f %f %f %f %f", cf.getStartDate(), cf.getEndDate(), cf.getStartDateAWS(), cf.getDescription(), cf.getAmount(), cf.getAmount1(), cf.getAmount2(), cf.getAmount3(), cf.getAmount4(), cf.getAmount5()));
        }
        assertEquals(expected, actual);
    }
}

