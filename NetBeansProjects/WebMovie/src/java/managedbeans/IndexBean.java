/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;


import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import static managedbeans.Actor.*;
import static managedbeans.Film.*;


/**
 *
 * @author rueda-ma
 */
@Named(value = "indexBean")
@RequestScoped
public class IndexBean implements Serializable {

    private HtmlDataTable datatableFilm;
    private HtmlDataTable datatableCollection;
    
    public HtmlDataTable getDatatableCollection() {
        return datatableCollection;
    }

    public void setDatatableCollection(HtmlDataTable datatableCollection) {
        this.datatableCollection = datatableCollection;
    }
    
    public HtmlDataTable getDatatableFilm() {
        return datatableFilm;
    }

    public void setDatatableFilm(HtmlDataTable datatableBooks) {
        this.datatableFilm = datatableBooks;
    }

    public void addFilmCollection() throws IOException, SQLException, ClassNotFoundException{
        //int index = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("index").toString());
        //System.out.println(index);
        Film film = (Film) datatableFilm.getRowData();
        System.out.println(film.getId());
        addFilmToCollection(film.getId());
    }
    
    public void deleteFilmCollection() throws IOException, SQLException, ClassNotFoundException{
        //int index = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("index").toString());
        //System.out.println(index);
        Film film = (Film) datatableCollection.getRowData();
        System.out.println(film.getId());
        deleteFilmFromCollection(film.getId());
    }
    
  
    public void setID() throws SQLException{
        Film film = (Film) datatableFilm.getRowData();
        Film.setInCollection("false");
        System.out.println(film.getId());
        setFilmID(film.getId());
        setFilmTitle(film.getTitle());
    }
    
    /**
     * Creates a new instance of IndexBean
     */
    public IndexBean() {
    }
    
}
