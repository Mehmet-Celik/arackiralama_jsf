/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;

@ManagedBean
@RequestScoped
public class baglan {

    /**
     * Creates a new instance of baglan
     */
    int i=0;
    public String baglan() {
        PreparedStatement ps=null;
        Connection con=null;
        try {
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/arac","root","");
        i++;
        }
        catch(Exception e){ 
        System.out.println(e);
       
        }
        if(i!=0)
            return "Bağlandı.";
        else 
            return "baglanmadi.";
    }
    
}
