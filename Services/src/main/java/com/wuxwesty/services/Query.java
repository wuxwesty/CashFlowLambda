package com.wuxwesty.services;

import com.amazonaws.services.lambda.runtime.LambdaLogger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.wuxwesty.model.*;

public class Query {

    String url = "";
    String user = "";
    String password = "";

    public Query(LambdaLogger logger) {
        url = System.getenv("url");
        logger.log("url:" + url + "\n");
        user = System.getenv("user");
        logger.log("user:" + user + "\n");
        password = System.getenv("password");
        //logger.log("password:" + password + "\n");
    }

    private Connection getConnection(LambdaLogger logger) {

        Connection con = null;

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            logger.log("Finding Driver org.postgresql.Driver\n");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return con;
        }

        // String abc=database.database(input);
        try {
            logger.log("Opening Connection\n");
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

    private ResultSet runQuery(String query, LambdaLogger logger) {

        Connection con = null;
        ResultSet resultSet = null;

        // String abc=database.database(input);
        try {
            con = this.getConnection(logger);
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

    private int runUpdate(String query, LambdaLogger logger) {

        Connection con = null;
        int id = 0;

        // String abc=database.database(input);
        try {
            con = this.getConnection(logger);
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
    private int runInsert(String query, String identityColumn, LambdaLogger logger) {

        Connection con = null;
        int id = 0;

        // String abc=database.database(input);
        try {
            con = this.getConnection(logger);
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

    private Account resultSetToAccount(ResultSet resultSet, LambdaLogger logger) {
        Account item = new Account();
        try {
            logger.log(resultSet.getString("UserID") + " - " + resultSet.getString("AccountID") + " - " + resultSet.getString("Description") + "\n");
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

    public Account getAccount(String userID, Integer id, LambdaLogger logger) {
        String sql = String.format("select * from public.Account where userid = '%s' and accountid = %d \n", userID, id);

        Account account = null;

        logger.log(sql);
        try {
            ResultSet resultSet = this.runQuery(sql, logger);
            if (resultSet!= null && resultSet.next()) {
                account = resultSetToAccount(resultSet, logger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account postAccount(String userID, Account account, LambdaLogger logger) {
        String sql = String.format(
                "insert into public.Account"
                + "(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) "
                + "values(default,'%s','%s',%d,%f,%f,%d) \n"
                , userID, account.getDescription(), account.getAccountTypeID(), account.getAmount(), account.getRate(), account.getSequence());

        logger.log(sql);
        try {
            int id = this.runInsert(sql, "accountid", logger);
            account.setAccountID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account putAccount(String userID, Account account, LambdaLogger logger) {
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

        logger.log(sql);
        try {
            this.runUpdate(sql, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public void deleteAccount(String userID, int id, LambdaLogger logger) {
        String sql = String.format("delete from public.Account where UserID = '%s' and AccountID = %d \n", userID, id);

        logger.log(sql);
        try {
            this.runQuery(sql, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Account> getAllAccounts(String userID, LambdaLogger logger) {
        String sql = String.format("select * from public.Account where UserID = '%s' order by Sequence\n", userID);
        List<Account> list = new ArrayList<Account>();
        logger.log(sql);
        try {
            ResultSet resultSet = this.runQuery(sql, logger);
            while (resultSet!=null && resultSet.next()) {
                Account account = resultSetToAccount(resultSet, logger);
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private Transaction resultSetToTransaction(ResultSet resultSet, LambdaLogger logger) {
        Transaction item = new Transaction();
        try {
            logger.log(resultSet.getString("UserID") + " - " + resultSet.getString("TransactionID") + " - " + resultSet.getString("Description") + "\n");
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

    public Transaction getTransaction(String userID, Integer id, LambdaLogger logger) {
        String sql = String.format("select * from public.Transact where userid = '%s' and transactionid = %d \n", userID, id);

        Transaction transaction = null;

        logger.log(sql);
        try {
            ResultSet resultSet = this.runQuery(sql, logger);
            if (resultSet!= null && resultSet.next()) {
                transaction = resultSetToTransaction(resultSet, logger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction postTransaction(String userID, Transaction transaction, LambdaLogger logger) {
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

        logger.log(sql);
        try {
            int  id = this.runInsert(sql, "transactionid", logger);
            transaction.setTransactionID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction putTransaction(String userID, Transaction transaction, LambdaLogger logger) {
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

        logger.log(sql);
        try {
            this.runUpdate(sql, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public void deleteTransaction(String userID, int id, LambdaLogger logger) {
        String sql = String.format("delete from public.Transact where UserID = '%s' and TransactionID = %d \n", userID, id);

        logger.log(sql);
        try {
            this.runQuery(sql, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Transaction> getAllTransactions(String userID, LambdaLogger logger) {
        String sql = String.format("select * from public.Transact where UserID = '%s' \n", userID);
        List<Transaction> list = new ArrayList<Transaction>();
        logger.log(sql);
        try {
            ResultSet resultSet = this.runQuery(sql, logger);
            while (resultSet!=null && resultSet.next()) {
                Transaction transaction = resultSetToTransaction(resultSet, logger);
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}

