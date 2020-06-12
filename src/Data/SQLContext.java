package Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author moham
 */
public class SQLContext {

    static Connection Main_Connection;

    Statement Main_Statemnet;

    ResultSet rs;

    public SQLContext() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Main_Connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "SYSTEM", "123");

            Main_Connection.setAutoCommit(true);

            Main_Statemnet = Main_Connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {

            Logger.getLogger(SQLContext.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    public void Create_Table_Attendees() {
        try {
            String sql = "CREATE TABLE Attendees( "
                    + " UserName  VARCHAR(20)) ";

            Main_Statemnet.executeUpdate(sql);

            PreparedStatement stmt = Main_Connection.prepareStatement("insert into Attendees values(?)");
            stmt.setString(1, "ali");

            PreparedStatement stmt2 = Main_Connection.prepareStatement("insert into Attendees values(?)");
            stmt2.setString(1, "osama");

            PreparedStatement stmt3 = Main_Connection.prepareStatement("insert into Attendees values(?)");
            stmt3.setString(1, "abood");

            stmt.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();

        } catch (SQLException ex) {

            Logger.getLogger(SQLContext.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void Create_Table_Calendar() {
        try {

            String createString = "create table CALENDAR "
                    + "(TIME varchar(32) NOT NULL, "
                    + "DAY varchar(10), "
                    + "EventTitle varchar(10)) ";

            Main_Statemnet.executeUpdate(createString);


        } catch (SQLException ex) {

            Logger.getLogger(SQLContext.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public String CheckUser(String u) throws SQLException {
        String result = "";
        PreparedStatement ps = Main_Connection.prepareStatement("select UserName from  Attendees where UserName=?");
        ps.setString(1, u);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            result = rs.getString("UserName");
        }

        return result;

    }

    public ArrayList<HashMap<String, String>> GetUsers() {

        ArrayList<HashMap<String, String>> userList = new ArrayList();

        try {

            ResultSet MyResult = Main_Statemnet.executeQuery("select UserName from Attendees");

            while (MyResult.next()) {

                HashMap<String, String> user = new HashMap();

                user.put("user", MyResult.getString("UserName"));

                userList.add(user);

            }

        } catch (SQLException ex) {

            Logger.getLogger(SQLContext.class.getName()).log(Level.SEVERE, null, ex);

        }

        return userList;
    }

    public ArrayList<HashMap<String, String>> GetCalendar() {

        ArrayList<HashMap<String, String>> CalendarList = new ArrayList();

        try {

            ResultSet MyResult = Main_Statemnet.executeQuery("select * from Calendar");

            while (MyResult.next()) {

                HashMap<String, String> user = new HashMap();

                user.put("ti", MyResult.getString("TIME"));
                user.put("di", MyResult.getString("DAY"));
                user.put("ei", MyResult.getString("EVENTTITLE"));

                CalendarList.add(user);

            }

        } catch (SQLException ex) {

            Logger.getLogger(SQLContext.class.getName()).log(Level.SEVERE, null, ex);

        }

        return CalendarList;
    }

    public static boolean tableExist(String tableName) throws SQLException {
        DatabaseMetaData dbm = Main_Connection.getMetaData();
        ResultSet rs = dbm.getTables(null, null, tableName, null);
        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

    public String getTime() throws SQLException {
        String result = "";
        PreparedStatement ps = Main_Connection.prepareStatement("select TIME from  CALENDAR");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            result = rs.getString("TIME");
        }
        return result;

    }

    public String getDay() throws SQLException {
        String result = "";
        PreparedStatement ps = Main_Connection.prepareStatement("select TIME from  CALENDAR");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            result = rs.getString("COLUMNCOUNT");
        }
        return result;

    }

    public void SetCalenader(String time, String day, String title) throws SQLException {
        PreparedStatement stmt = Main_Connection.prepareStatement("insert into CALENDAR values(?,?,?)");
        stmt.setString(1, time);
        stmt.setString(2, day);
        stmt.setString(3, title);

        stmt.executeUpdate();

    }

}
