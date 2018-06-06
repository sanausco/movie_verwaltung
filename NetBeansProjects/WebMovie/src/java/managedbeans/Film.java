/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import DBconnection.Sequence;
import DBconnection.WebConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 *
 * @author eu
 */
@Named(value = "film")
@RequestScoped
public class Film implements Serializable{
    
    private Long id;
    private String title;
    private static String inCollection="false";
    private String search = "";
   
    @Min(1900)
    @Max(2017)
    private Long year;
    private String director;
    private String genre;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public static String getInCollection() {
        return inCollection;
    }

    public static void setInCollection(String inCollection) {
        Film.inCollection = inCollection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Creates a new instance of Film
     */
    public Film() {
    }
    
    public void addFilm() throws ClassNotFoundException, SQLException{
        inCollection="false";
        id = Sequence.getSequence();
        Connection conn = WebConnection.getConnection();
        
        try {
        String q = "insert into Film(id,title,years,genre,director) "
                + "values (?,?,?,?,?) ";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setLong(1, id);
        stmt.setString(2, this.title);
        stmt.setLong(3, this.year);
        stmt.setString(4, this.genre);
        stmt.setString(5, this.director);
        
        stmt.executeUpdate();
        
        
        title="";
        year=null;
        genre="";
        director="";
        
        
        
        conn.commit();
        stmt.close();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        
        
    }
    
    public static void addFilmToCollection(Long id) throws SQLException, ClassNotFoundException{
        
        Connection conn = WebConnection.getConnection();
        PreparedStatement stmt;
        
        try {
        
        String query = "select * from user_films where user_id=? and film_id=? ";
        stmt = conn.prepareStatement(query);
        stmt.setLong(1, User.getId());
        stmt.setLong(2, id);
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()){
            inCollection="true";
        }else{
        
        
        inCollection="false";
        query = "insert into user_films (user_id,film_id)"
                + "values(?,?) ";
        
        stmt = conn.prepareStatement(query);
        stmt.setLong(1, User.getId());
        stmt.setLong(2, id);
        stmt.executeUpdate();
        
        
        }
        
        conn.commit();
        
        stmt.close();
        
        }catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
    
    public static void deleteFilmFromCollection(Long id) throws SQLException{
        inCollection="false";
        Connection conn = WebConnection.getConnection();
        
        try{
        
        String q = "delete from user_films where film_id=? ";
        PreparedStatement stmt = conn.prepareStatement(q);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        
        conn.commit();
        stmt.close();
        
        }catch (SQLException e) {
                conn.rollback();
                throw e;
        }
        
        }
    
    
    public ArrayList<Film> filmeList() throws SQLException, ClassNotFoundException{
        ArrayList<Film>filme = new ArrayList<>();
        Film film;
        Connection conn = WebConnection.getConnection();
        
        try {
        
        PreparedStatement stmt;
        ResultSet rs;
                
        if(search.equals("")){
            stmt = conn.prepareStatement("select * from film");
            rs = stmt.executeQuery();
        }else{
            System.out.println(search);
            
            String query = "select * from film where upper(title) like upper('%"+search+"%') "
                    + "or upper(director) like upper('%"+search+"%') "
                    + "or upper(genre) like upper('%"+search+"%') ";
            stmt = conn.prepareStatement(query);
            //stmt.setString(1, search);
            rs = stmt.executeQuery();
        }
        
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
  
    
   
    
}
