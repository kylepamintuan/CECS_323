/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class JavaApplication1 
{
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/Main Database;user=kylepamintuan;password=008830924";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) 
    {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "ID", "First Name", "Last Name", "Phone #");
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("au_id");
                String phone = rs.getString("phone");
                String first = rs.getString("au_fname");
                String last = rs.getString("au_lname");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(id), dispNull(first), dispNull(last), dispNull(phone));
            }
            //STEP 6: Clean-up environment
            rs.close();
            pstmt.close();
            conn.close();
        } 
        catch (SQLException se) 
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } 
        catch (Exception e) 
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally 
        {
            //finally block used to close resources
            try 
            {
                if (stmt != null) 
                {
                    stmt.close();
                }
            } 
            catch (SQLException se2) {}// nothing we can do
            try 
            {
                if (conn != null) 
                {
                    conn.close();
                }
            } 
            catch (SQLException se) 
            {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}