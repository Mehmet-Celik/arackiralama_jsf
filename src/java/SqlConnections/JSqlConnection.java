
package SqlConnections;

import java.sql.Connection;
import java.sql.DriverManager;


public class JSqlConnection {
   

    public  Connection getConn(){
        
        Connection conn = null;
        
        try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/arac","root","");
            System.out.println("Baglanti Acildi");
            
            return conn;
        }
        catch(Exception e){
            System.out.println("Baglanti Acilmadi");
            return null;
        }
    }
    
}
