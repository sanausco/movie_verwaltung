/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rueda-ma
 */
public class WebConnection {
    
    
    
    private static Connection conn;
    
    static{
        
        conn = null;            
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Webtec","santiago","santiago");
            conn.setAutoCommit(false);
            System.out.println("Connection successful!");
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static Connection getConnection(){
        return conn;
    }
}
