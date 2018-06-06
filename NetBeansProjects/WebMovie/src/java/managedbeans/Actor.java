/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import DBconnection.Sequence;
import DBconnection.WebConnection;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author rueda-ma
 */
@Named(value = "actor")
@RequestScoped
public class Actor implements Serializable {

    private String name;
    private String surname;
    private static Long filmID;
    private static String filmTitle;

    public static String getFilmTitle() {
        return filmTitle;
    }

    public static void setFilmTitle(String filmName) {
        Actor.filmTitle = filmName;
    }
    
    

    public static Long getFilmID() {
        return filmID;
    }

    public static void setFilmID(Long filmID) {
        Actor.filmID = filmID;
    }
    
    

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    
    public static ArrayList<Actor> getActors() throws SQLException{
        ArrayList<Actor> actors = new ArrayList<>();
        Connection conn = WebConnection.getConnection();
        
        try {
        String query = "select * from Actor where film_id=? ";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setLong(1, filmID);
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            Actor actor = new Actor();
            actor.setName(rs.getString("name"));
            actor.setSurname(rs.getString("surname"));
            actors.add(actor);
        }
        
        return actors;
        
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void addActor() throws SQLException{
        Connection conn = WebConnection.getConnection();
        
        try {
        String query = "insert into Actor values(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        
        stmt.setLong(1, Sequence.getSequence());
        stmt.setString(2, name);
        stmt.setString(3, surname);
        stmt.setLong(4, filmID);
        stmt.executeUpdate();
        
        name = "";
        surname = "";
        
        conn.commit(); 
        
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        
    }
    
    /**
     * Creates a new instance of Actor
     */
    public Actor() {
    }
    
}
