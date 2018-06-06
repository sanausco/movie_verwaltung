/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author rueda-ma
 */
public class Sequence {
    
    public static Long getSequence() throws SQLException {
        Connection conn = WebConnection.getConnection();
                
        try{
        String seq = "values(next value for seq_id)";
        PreparedStatement stmt = conn.prepareStatement(seq);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getLong(1);
        }catch (SQLException e){
            throw e;
        }
    }
    
}
