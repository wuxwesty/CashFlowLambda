package com.wuxwesty.dataaccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.wuxwesty.model.Account;
import com.wuxwesty.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query {

    static final Logger logger = LoggerFactory.getLogger(Query.class);

    String url = "";
    String user = "";
    String password = "";

    public Query() {
        url = System.getenv("POSTGRES_URL");
        logger.info("url:" + url + "\n");
        user = System.getenv("POSTGRES_USER");
        logger.info("user:" + user + "\n");
        password = System.getenv("POSTGRES_PASSWORD");
        //logger.log("password:" + password + "\n");
    }

    private Connection getConnection() {

        Connection con = null;

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            logger.info("Finding Driver org.postgresql.Driver\n");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return con;
        }

        // String abc=database.database(input);
        try {
            logger.info("Opening Connection\n");
            //Connection con = DriverManager.getConnection(
            //        url: "jdbc:mysql://Endpoint/DB_Name", user: "User_Name", password: "password");
            con = DriverManager.getConnection(
                    //        "jdbc:postgresql://wucashflow.cnems7ltlrrb.us-east-1.rds.amazoncom:5432/wucashflow", "wucashflow", "wucashflow");
                    url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    private ResultSet runQuery(String query) {

        Connection con = null;
        ResultSet resultSet = null;

        // String abc=database.database(input);
        try {
            con = this.getConnection();
            if (con != null) {
                Statement statement = con.createStatement();
                resultSet = statement.executeQuery(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed())
                    con.close();
            } catch (Exception e) {
            }
        }
        return resultSet;

    }

    private int runUpdate(String query) {

        Connection con = null;
        int id = 0;

        // String abc=database.database(input);
        try {
            con = this.getConnection();
            if (con != null) {
                Statement statement = con.createStatement();
                int affectedRows = statement.executeUpdate(query);
                if (affectedRows == 0) {
                    throw new SQLException("No rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed())
                    con.close();
            } catch (Exception e) {
            }
        }
        return id;

    }

    // HELP: Inserted ID: https://stackoverflow.com/questions/1915166/how-to-get-the-insert-id-in-jdbc
    // HELP: Postgres Serial http://www.postgresqltutorial.com/postgresql-serial/
    private int runInsert(String query, String identityColumn) {

        Connection con = null;
        int id = 0;

        // String abc=database.database(input);
        try {
            con = this.getConnection();
            if (con != null) {
                String[] returnId = { identityColumn };
                PreparedStatement statement = con.prepareStatement(query, returnId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                ResultSet rs = null;
                try {
                    rs = statement.getGeneratedKeys();
                    if (rs.next()) {
                        id = rs.getInt(1);
                        System.out.println(String.format("new id: %d" , id));
                    }
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null && !rs.isClosed())
                            rs.close();
                    } catch (Exception e) {
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed())
                    con.close();
            } catch (Exception e) {
            }
        }
        return id;

    }

    private Account resultSetToAccount(ResultSet resultSet) {
        Account item = new Account();
        try {
            logger.info(resultSet.getString("UserID") + " - " + resultSet.getString("AccountID") + " - " + resultSet.getString("Description") + "\n");
            item.setUserID(resultSet.getString("UserID"));
            item.setAccountID(resultSet.getInt("AccountID"));
            item.setAccountTypeID(resultSet.getInt("AccountTypeID"));
            item.setDescription(resultSet.getString("Description"));
            item.setAmount(resultSet.getDouble("Amount"));
            item.setRate(resultSet.getDouble("Rate"));
            item.setSequence(resultSet.getInt("Sequence"));
        } catch (SQLException e) {
            e.printStackTrace();
            item = null;
        }

        return item;
    }

    public Account getAccount(String userID, Integer id) {
        String sql = String.format("select * from public.Account where userid = '%s' and accountid = %d \n", userID, id);

        Account account = null;

        logger.info(sql);
        try {
            ResultSet resultSet = this.runQuery(sql);
            if (resultSet!= null && resultSet.next()) {
                account = resultSetToAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account postAccount(String userID, Account account) {
        String sql = String.format(
                "insert into public.Account"
                + "(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) "
                + "values(default,'%s','%s',%d,%f,%f,%d) \n"
                , userID, account.getDescription(), account.getAccountTypeID(), account.getAmount(), account.getRate(), account.getSequence());

        logger.info(sql);
        try {
            int id = this.runInsert(sql, "accountid");
            account.setAccountID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account putAccount(String userID, Account account) {
        String sql = String.format(
                "update public.Account "
                        + "set UserID='%s',"
                        + "Description='%s',"
                        + "AccountTypeID=%d,"
                        + "Amount=%f,"
                        + "Rate=%f,"
                        + "Sequence=%d "
                        + "where AccountID = %d \n",
                userID,
                account.getDescription(),
                account.getAccountTypeID(),
                account.getAmount(),
                account.getRate(),
                account.getSequence(),
                account.getAccountID());

        logger.info(sql);
        try {
            this.runUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public void deleteAccount(String userID, int id) {
        String sql = String.format("delete from public.Account where UserID = '%s' and AccountID = %d \n", userID, id);

        logger.info(sql);
        try {
            this.runQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Account> getAllAccounts(String userID) {
        String sql = String.format("select * from public.Account where UserID = '%s' order by Sequence\n", userID);
        List<Account> list = new ArrayList<Account>();
        logger.info(sql);
        try {
            ResultSet resultSet = this.runQuery(sql);
            while (resultSet!=null && resultSet.next()) {
                Account account = resultSetToAccount(resultSet);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private Transaction resultSetToTransaction(ResultSet resultSet) {
        Transaction item = new Transaction();
        try {
            logger.info(resultSet.getString("UserID") + " - " + resultSet.getString("TransactionID") + " - " + resultSet.getString("Description") + "\n");
            item.setUserID(resultSet.getString("UserID"));
            item.setTransactionID(resultSet.getInt("TransactionID"));
            item.setAccountID(resultSet.getInt("AccountID"));
            item.setFromAccountID(resultSet.getInt("FromAccountID"));
            item.setDescription(resultSet.getString("Description"));
            item.setAmount(resultSet.getDouble("Amount"));
            item.setStartDate(resultSet.getDate("StartDate"));
            item.setEndDate(resultSet.getDate("EndDate"));
            item.setActivityTypeID(resultSet.getInt("ActivityTypeID"));
            item.setRecurrenceTypeID(resultSet.getInt("RecurrenceTypeID"));
            item.setDayOfWeek(resultSet.getInt("DayOfWeek"));
            item.setMonth(resultSet.getInt("Month"));
            item.setDayOfMonth(resultSet.getInt("DayOfMonth"));

        } catch (SQLException e) {
            e.printStackTrace();
            item = null;
        }

        return item;
    }

    public Transaction getTransaction(String userID, Integer id) {
        String sql = String.format("select * from public.Transact where userid = '%s' and transactionid = %d \n", userID, id);

        Transaction transaction = null;

        logger.info(sql);
        try {
            ResultSet resultSet = this.runQuery(sql);
            if (resultSet!= null && resultSet.next()) {
                transaction = resultSetToTransaction(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction postTransaction(String userID, Transaction transaction) {
        String sql = String.format(
                "insert into public.Transact"
                        + "(TransactionID, UserID,AccountID,FromAccountID,ActivityTypeID,StartDate,EndDate,RecurrenceTypeID,"
                        + "DayOfWeek,Month,DayOfMonth,Description,Amount) "
                        + "values(default,'%s',%d,%d,%d,'%s','%s',%d,%d,%d,%d,'%s',%f) \n",
                userID,
                transaction.getAccountID(),
                transaction.getFromAccountID(),
                transaction.getActivityTypeID(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getRecurrenceTypeID(),
                transaction.getDayOfWeek(),
                transaction.getMonth(),
                transaction.getDayOfMonth(),
                transaction.getDescription(),
                transaction.getAmount()
            );

        logger.info(sql);
        try {
            int  id = this.runInsert(sql, "transactionid");
            transaction.setTransactionID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction putTransaction(String userID, Transaction transaction) {
        String sql = String.format(
                "update public.Transact "
                + "set AccountID=%d,"
                + "FromAccountID=%d,"
                + "ActivityTypeID=%d,"
                + "StartDate='%s',"
                + "EndDate='%s',"
                + "RecurrenceTypeID=%d,"
                + "DayOfWeek=%d,"
                + "Month=%d,"
                + "DayOfMonth=%d,"
                + "Description='%s',"
                + "Amount=%f"
                + "where TransactionID = %d \n",
                transaction.getAccountID(),
                transaction.getFromAccountID(),
                transaction.getActivityTypeID(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getRecurrenceTypeID(),
                transaction.getDayOfWeek(),
                transaction.getMonth(),
                transaction.getDayOfMonth(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getTransactionID()
            );

        logger.info(sql);
        try {
            this.runUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public void deleteTransaction(String userID, int id) {
        String sql = String.format("delete from public.Transact where UserID = '%s' and TransactionID = %d \n", userID, id);

        logger.info(sql);
        try {
            this.runQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Transaction> getAllTransactions(String userID) {
        String sql = String.format("select * from public.Transact where UserID = '%s' \n", userID);
        List<Transaction> list = new ArrayList<Transaction>();
        logger.info(sql);
        try {
            ResultSet resultSet = this.runQuery(sql);
            while (resultSet!=null && resultSet.next()) {
                Transaction transaction = resultSetToTransaction(resultSet);
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}

