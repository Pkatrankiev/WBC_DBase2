package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExampleOfMSAccess2007 
{
    public static void main(String[] args) 
    {
        System.out.println("Start of Program");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String url=null,userID=null,password=null;
        String dbFileName=null;
        String sql=null;

        dbFileName = "D:\\Testdb.mdb";
        userID = "idk2_3";
        password = "qwer1234";
        url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};"+
                "DBQ="+dbFileName+";"+
                "Uid="+userID+";"+
                "Pwd="+password+";";
        sql = "SELECT * FROM tblUserProfile";
        System.out.println("url = "+url); 

        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(url,userID,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs!=null)
            {
                while(rs.next())
                {
                    System.out.print("User ID = "+rs.getString("User ID"));
                    System.out.print(" User Name = "+rs.getString("User Name"));
                    System.out.print(" Password = "+rs.getString("Password"));
                    System.out.println(" Access Type = "+rs.getString("Access Type"));
                }
                rs.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
        try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
        System.out.println("End of Program");
    }
}
