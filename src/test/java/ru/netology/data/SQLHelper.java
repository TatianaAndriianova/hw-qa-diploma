package ru.netology.data;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
@UtilityClass
public class SQLHelper {

    private static final String DB_URL =
            System.getProperty("db.url");

    private static final String DB_USER =
            System.getProperty("db.user", "app");

    private static final String DB_PASS =
            System.getProperty("db.password", "pass");

    @SneakyThrows
    private static Connection getConnection() {
        log.info("Подключение к БД: {}", DB_URL);
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASS
        );
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        log.info("Получение статуса оплаты");
        var runner = new QueryRunner();
        var sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {
            var result = runner.query(
                    conn,
                    sql,
                    new ScalarHandler<String>()
            );
            log.info("Статус оплаты: {}", result);

            return result;
        }
    }

    @SneakyThrows
    public static String getCreditStatus() {
        log.info("Получение статуса кредита");
        var runner = new QueryRunner();
        var sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = getConnection()) {

            var result = runner.query(
                    conn,
                    sql,
                    new ScalarHandler<String>()
            );

            log.info("Статус кредита: {}", result);

            return result;
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        log.info("Очистка базы данных");
        var runner = new QueryRunner();
        try (var conn = getConnection()) {
            runner.update(
                    conn,
                    "DELETE FROM order_entity;"
            );
            runner.update(
                    conn,
                    "DELETE FROM payment_entity;"
            );
            runner.update(
                    conn,
                    "DELETE FROM credit_request_entity;"
            );
            log.info("База данных успешно очищена");
        }
    }
}