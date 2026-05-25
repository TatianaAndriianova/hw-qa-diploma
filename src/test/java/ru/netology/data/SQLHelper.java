package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static final String DB_URL = System.getProperty("db.url");
    private static final String DB_USER = System.getProperty("db.user", "app");
    private static final String DB_PASS = System.getProperty("db.password", "pass");

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var runner = new QueryRunner();
        var sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var runner = new QueryRunner();
        var sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(conn, "DELETE FROM order_entity;");
            runner.update(conn, "DELETE FROM payment_entity;");
            runner.update(conn, "DELETE FROM credit_request_entity;");
        }
    }
}