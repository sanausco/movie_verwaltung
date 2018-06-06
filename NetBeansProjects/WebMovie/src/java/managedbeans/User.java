/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import DBconnection.WebConnection;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author eu
 */
@Named(value = "user")
@SessionScoped
public class User implements Serializable {

    /**
     * Creates a new instance of User
     */
    private static String name;
    private String password;
    private String state="correct";
   
    public String getState() {
        return state;
    }

    public void setState(String mss) {
        this.state = mss;
    }

    public static String getName1() {
        return name;
    }
    
    public String getName(){
        return getName1();
    }

    public void setName(String name) {
        User.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public User() {
    }
    
    public String check() throws SQLException, ClassNotFoundException{
      state="correct";
      Connection conn =  WebConnection.getConnection();
      
      try {
      String query1 = "select name from users where name=?";
      PreparedStatement stmt = conn.prepareStatement(query1);
      stmt.setString(1, this.name);
      ResultSet rs = stmt.executeQuery();
            
      if(!rs.next()){
          state="!exist";
          return "index";
          
      }else{
          String query2 = "select password from users where name=?";
          PreparedStatement stmt2 = conn.prepareStatement(query2);
          stmt2.setString(1,this.name);
          ResultSet rs2 = stmt2.executeQuery();
          rs2.next();
          
          if(this.name.equals(rs.getString("NAME")) && this.password.equals(rs2.getString("password"))){
              state="correct";
              return "film";
          }else{
              state="false";
              return "index";
          }
          
      }
      } catch (SQLException e) {
          throw e;
      }
    }
    
    
    
    public ArrayList<Film> collectionList() throws SQLException, ClassNotFoundException{
        ArrayList<Film>filme = new ArrayList<>();
        Film film;
        Connection conn = WebConnection.getConnection();
        
        try {
        String query = "select * from film "
                + "join user_films coll on (film.ID=coll.FILM_ID) "
                + " where coll.USER_ID=? ";
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setLong(1, User.getId());
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            
            
            film = new Film();
            film.setId(rs.getLong("ID"));
            film.setTitle(rs.getString("TITLE"));
            film.setYear(rs.getLong("YEARS"));
            film.setDirector(rs.getString("DIRECTOR"));
            film.setGenre(rs.getString("GENRE"));
               
      
        filme.add(film);
        
        }
         return filme;  
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public static Long getId() throws SQLException, ClassNotFoundException{
        Connection conn = WebConnection.getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("select id from "
                + "users where name=? ");
            stmt.setString(1, User.getName1());
            ResultSet rs = stmt.executeQuery();
            rs.next();
                return rs.getLong("id");

        
        } catch (SQLException ex){
            throw ex;
        }
        
        
                
    }
     
}
