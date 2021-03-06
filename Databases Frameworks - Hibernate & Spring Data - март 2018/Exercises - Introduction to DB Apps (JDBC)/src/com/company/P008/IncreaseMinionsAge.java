package com.company.P008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;

public class IncreaseMinionsAge {

    private static final String URL = "jdbc:mysql://localhost:3306/minionsdb?useSSL=false";
    private static final String USER = "root";

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Password: ");
        String pass = reader.readLine();
        try(Connection conn = DriverManager.getConnection(URL, USER, pass)) {
            System.out.println("Connection successfully!...");
            int[] idArr = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            updateMinions(conn, idArr);
            printResult(conn);
        }
    }

    private static void printResult(Connection conn) throws SQLException {
        String sqlQuery = "SELECT * FROM `minions`;";
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rs.beforeFirst();
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(String.format("%s %d", name, age));
            }
        }
    }

    private static void updateMinions(Connection conn, int[] idArr) throws SQLException {
        for (int id : idArr) {
            increaseAge(conn, id);
            makeTitleCase(conn, id);
        }
    }

    private static void makeTitleCase(Connection conn, int id) throws SQLException {
        String sqlQuery = "SELECT `name` FROM `minions` WHERE id = ?;";
        try(PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.first();
            String[] name = rs.getString("name").split("\\s+");
            checkAndUpdateFirstLetterCase(conn, name, id);
        }
    }

    private static void checkAndUpdateFirstLetterCase(Connection conn, String[] name, int id) throws SQLException {
        StringBuilder sb = new StringBuilder();
        for (String str : name) {
            if (str.substring(0,1).equals(str.substring(0,1).toUpperCase())) {
                sb.append(str.substring(0,1).toLowerCase())
                        .append(str.substring(1,str.length()))
                        .append(" ");
            } else {
                sb.append(str.substring(0,1).toUpperCase())
                        .append(str.substring(1,str.length()))
                        .append(" ");
            }
            updateName(conn, id, sb.toString().trim());
        }
    }

    private static void updateName(Connection conn, int id, String name) throws SQLException {
        String sqlQuery =
                "UPDATE `minions`" +
                "SET `name` = ? " +
                "WHERE `id` = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1,name);
            stmt.setInt(2,id);
            stmt.executeUpdate();
        }
    }

    private static void increaseAge(Connection conn, int id) throws SQLException {
        String sqlQuery =
                "UPDATE `minions`" +
                "SET `age` = `age` + 1 " +
                "WHERE `id` = ?;";
        try(PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


}
