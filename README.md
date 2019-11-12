# Fancy-Bank-V2.0-with-stocks-database

## Authors

Group 21

## How to test

Run main method in Test.java to test all code. The recommended approach is to run the application via IntelliJ IDEA

The code in src folder is the db version (all data stored in the database). You need to setup database before you test it.
If you don't want to setup database, you can test all functions in the no-db version (no database, all data stored in memory) by replacing [\src\Bankdatabase.java] with [\doc\BankDatabaseBackUp\offline\Bankdatabase.java].
you can choose to use [\doc\BankDatabaseBackUp\offline\Bankdatabase.java] for testing the no-db version, or [\doc\BankDatabaseBackUp\online\Bankdatabase.java] for testing the db version.

## How to create Database

1. First install mysql@5.7, and set the root password as "pass" (without the quotes)
2. Next, create a database called stock. In stock database, create three different tables: records, clientBytes, stocks.
    `mysql> create database stock;`
    `use stock;`
    `create table stocks (name VARCHAR(255), tick VARCHAR(20), price DOUBLE);`
    `create table clientByte ( userName varchar(20), javaObject blob, primary key (userName));`
    `create records ( summary varchar(225), content varchar(225);`
3. keep the database running, then you can test all functions.
