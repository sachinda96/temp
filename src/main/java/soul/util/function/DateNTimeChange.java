/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soul.util.function;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.write.DateTime;

/**
 *
 * @author admin
 */
public class DateNTimeChange {
   // SimpleDateFormat sdfsimple = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfTimesimple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Date getDateNTimechange(String Date){
         
           Time t= new Time(new Date().getTime());
           Date parsed = null;
           try {
          parsed = sdfTimesimple.parse(Date+" "+t.toString());
           } catch (ParseException ex) {
           
               System.out.println("Ex : data : "+ ex.getMessage());
           }
           
           return parsed;
    }
    
    public Date getTimechange(String Date){
         
           String dateWithoutTime1 =sdfTimesimple.format(new Date());
           Date parsed = null;
           try {
          parsed = sdfTimesimple.parse(dateWithoutTime1.substring(0,10)+" "+Date.substring(11,Date.length()));
           } catch (ParseException ex) {
           
               System.out.println("Ex1 : data1 : "+ ex.getMessage());
           }
           
           return parsed;
    }
    
}
