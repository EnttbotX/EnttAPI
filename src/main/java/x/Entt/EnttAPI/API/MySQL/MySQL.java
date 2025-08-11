package x.Entt.EnttAPI.API.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
   private Connection connection;

   public boolean connect(String host, String port, String database, String username, String password) {
      if (!isConnected()) {
         try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true",
                    username,
                    password
            );
            return true;
         } catch (SQLException e) {
            e.printStackTrace();
            return false;
         }
      }
      return true;
   }

   public void disconnect() {
      if (isConnected()) {
         try {
            connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   public boolean isConnected() {
      return connection != null;
   }

   public void executeCommand(String command) {
      if (!isConnected()) return;
      try {
         connection.createStatement().executeUpdate(command);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void createTable(String tableName, String values) {
      executeCommand("CREATE TABLE IF NOT EXISTS " + tableName + " (" + values + ")");
   }

   public ResultSet getResult(String query) {
      if (!isConnected()) return null;
      try {
         return connection.createStatement().executeQuery(query);
      } catch (SQLException e) {
         e.printStackTrace();
         return null;
      }
   }
}