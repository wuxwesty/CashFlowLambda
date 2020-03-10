
DROP TABLE Transact;
DROP TABLE Account;
DROP TABLE RecurrenceType;
DROP TABLE ActivityType;
DROP TABLE AccountType;

CREATE TABLE RecurrenceType( 
	RecurrenceTypeID SERIAL PRIMARY KEY, 
	Description TEXT NOT NULL);

INSERT INTO RecurrenceType(RecurrenceTypeID,Description) 
	VALUES (1,'Once');
INSERT INTO RecurrenceType(RecurrenceTypeID,Description) 
	VALUES (2,'Weekly');
INSERT INTO RecurrenceType(RecurrenceTypeID,Description) 
	VALUES (3,'BiWeekly');
INSERT INTO RecurrenceType(RecurrenceTypeID,Description) 
	VALUES (4,'Monthly');
INSERT INTO RecurrenceType(RecurrenceTypeID,Description) 
	VALUES (5,'Annually');

CREATE TABLE ActivityType( 
	ActivityTypeID SERIAL PRIMARY KEY, 
	Description TEXT NOT NULL);

INSERT INTO ActivityType(ActivityTypeID,Description) 
	VALUES (1,'Income');
INSERT INTO ActivityType(ActivityTypeID,Description) 
	VALUES (2,'Expense');
INSERT INTO ActivityType(ActivityTypeID,Description) 
	VALUES (3,'Transfer');

CREATE TABLE AccountType( 
	AccountTypeID SERIAL PRIMARY KEY, 
	Description TEXT NOT NULL);

INSERT INTO AccountType(AccountTypeID,Description) 
	VALUES (1,'Income');
INSERT INTO AccountType(AccountTypeID,Description) 
	VALUES (2,'Expense');
INSERT INTO AccountType(AccountTypeID,Description) 
	VALUES (3,'Investment');
INSERT INTO AccountType(AccountTypeID,Description) 
	VALUES (4,'TaxDeferred');


CREATE TABLE Account( 
	AccountID SERIAL PRIMARY KEY, 
	UserID TEXT NOT NULL,
	Description TEXT NOT NULL,   
	AccountTypeID INTEGER, 
	Amount REAL, 
	Rate REAL, 
	Sequence INTEGER);

INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (1,'','RBC Checking',1,250,0,'001');
INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (3,'','RBC Savings',1,63,0,'002');
INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (2,'','RBC Line Of Credit',2,0,0,'003');
INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (5,'','Lexus Car Loan',2,0,0,'004');
INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (4,'','RBC Tax Free Savings Account',4,10000,0,'005');
INSERT INTO Account(AccountID,UserID,Description,AccountTypeID,Amount,Rate,Sequence) 
	VALUES (6,'','RBC DirectInvesting RRSP',4,120000,0,'006');

CREATE TABLE Transact( 
	TransactionID SERIAL PRIMARY KEY,
	UserID TEXT NOT NULL,
	AccountID INTEGER, 
	FromAccountID INTEGER, 
	ActivityTypeID INTEGER, 
	StartDate DATE, 
	EndDate DATE, 
	RecurrenceTypeID INTEGER, 
	DayOfWeek INTEGER, 
	Month INTEGER, 
	DayOfMonth INTEGER, 
	Description TEXT NOT NULL, 
	Amount REAL); 


INSERT INTO Transact (TransactionID,UserID,AccountID,FromAccountID,ActivityTypeID,StartDate,EndDate,RecurrenceTypeID,DayOfWeek,Month,DayOfMonth,Description,Amount)
	VALUES (1,'',1,0,1,'2018-01-01','2018-06-30',3,1,1,1,'Income',3500.00);
INSERT INTO Transact (TransactionID,UserID,AccountID,FromAccountID,ActivityTypeID,StartDate,EndDate,RecurrenceTypeID,DayOfWeek,Month,DayOfMonth,Description,Amount)
	VALUES (2,'',1,0,1,'2018-07-01','2018-12-31',3,1,1,1,'Income',3900.00);

SELECT * from RecurrenceType;
SELECT * from ActivityType;
SELECT * from AccountType;
SELECT * from Account;
SELECT * from Transact;