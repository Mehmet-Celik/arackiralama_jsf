
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class kullaniciislem extends SqlConnections.JSqlConnection{

        private PreparedStatement ps=null;
        private Connection con=null;
        private String ad;
        private String soyad;      
        private String kad;
        private String pass;
        private String mail;
        private int id;
        private int yetki;
        private int uyeID;

        
        //admin panellinden üye ekleme işlemini yapan fonk.
    
    public String uyeEkle()
    {
        try {
        con = getConn();
        ps=con.prepareStatement("insert into uyeler(ad,soyad,kad,pass,mail,yetki) values(?, ?, ?, ?, ?, ?)");
                
                ps.setString(1, ad);
                ps.setString(2, soyad);
                 ps.setString(3, kad);
                ps.setString(4, pass);
                 ps.setString(5, mail);
                ps.setInt(6, yetki);
                ps.executeUpdate();
                return "kullanicilar?faces-redirect=true";
        }
        catch(Exception e){ 
        System.out.println(e);
        return "hatali";
        }
    }
    
    
    //eklenen üyeleri datatable'a yükelyen fonk
    public List<kullaniciislem> uyeGetir() { 
        
    try {
        con = getConn();
         ps=con.prepareStatement("select * from uyeler");
         ResultSet rs=ps.executeQuery();
         List<kullaniciislem> liste=new ArrayList<>();
         while(rs.next())
        {
            kullaniciislem listem=new kullaniciislem();
            listem.setId(rs.getInt("id"));
            listem.setAd(rs.getString("ad"));
            listem.setSoyad(rs.getString("soyad"));
            listem.setKad(rs.getString("kad"));
            listem.setMail(rs.getString("mail"));
            listem.setYetki(rs.getInt("yetki"));
            liste.add(listem);
            
        }
         return liste;
    
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
   }
    //secilen üyeyi silen fonk.
      public String uyeSil(){
        try {
          FacesContext fc = FacesContext.getCurrentInstance();
        this.uyeID = Integer.parseInt(IDParametresiniAl(fc));
        con = getConn();
        ps=con.prepareStatement("delete from uyeler where id=?");
               ps.setInt(1, uyeID);
               int i=ps.executeUpdate();
               if(i>0)
                return "kullanicilar?faces-redirect=true";
               else 
                     return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return "hatali";
        }
    }
    public String IDParametresiniAl(FacesContext fc)
    {
        Map<String,String> parametreler = fc.getExternalContext().getRequestParameterMap();
	return parametreler.get("uyeID");
    }
    
    
    public int getUyeID() {
        return uyeID;
    }

    public void setUyeID(int uyeID) {
        this.uyeID = uyeID;
    }
        
     public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public int getYetki() {
        return yetki;
    }

    public void setYetki(int yetki) {
        this.yetki = yetki;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getKad() {
        return kad;
    }

    public void setKad(String kad) {
        this.kad = kad;
    }
    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
    
    public kullaniciislem() {
    }
    
    
}
