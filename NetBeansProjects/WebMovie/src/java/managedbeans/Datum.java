/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.util.Date;
import java.util.TimeZone;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author test
 */
@Named(value = "datum")
@RequestScoped
public class Datum {

    private Date now;

     /**
     * Creates a new instance of Datum
     */
    public Datum() {
        this.now = new Date();
    }
    
    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
   
    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }
}
