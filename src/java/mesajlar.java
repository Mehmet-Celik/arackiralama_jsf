

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@RequestScoped
public class mesajlar extends SqlConnections.JSqlConnection{

    public mesajlar() {
    }
    
    private String adsoyad;
    private String mail;
    private String konu;
    private String mesaj;
    private String tarih;
    private int mesajID;
    private PreparedStatement ps=null;
    private Connection con=null;

     
   
    
      public List<mesajlar> mesajGetir() { 
        
    try {
        con = getConn();
         ps=con.prepareStatement("select * from mesajlar");
         ResultSet rs=ps.executeQuery();
         List<mesajlar> liste=new ArrayList<>();
         while(rs.next())
        {
            mesajlar listem=new mesajlar();
            listem.setMesajID(rs.getInt("id"));
            listem.setAdsoyad(rs.getString("adsoy"));
            listem.setMail(rs.getString("mail"));
            listem.setKonu(rs.getString("konu"));
            listem.setMesaj(rs.getString("mesaj"));
            listem.setTarih(rs.getString("tarih"));
            liste.add(listem);
            
        }
         return liste;
    
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
   }
      
      //index sayfasında mesaj gönderme kısmı için fonk.
      public String mesajGonder()
    {
        try {
            Calendar cl=Calendar.getInstance();
        con = getConn();
        ps=con.prepareStatement("insert into mesajlar(adsoy,mail,konu,mesaj,tarih) values(?, ?, ?, ?,?)");
                
                ps.setString(1, adsoyad);
                ps.setString(2, mail);
                 ps.setString(3, konu);
                ps.setString(4, mesaj);
                 ps.setString(5, cl.getTime().toString());
                ps.executeUpdate();
                return "index?faces-redirect=true";
        }
        catch(Exception e){ 
        System.out.println(e);
        return "hatali"+e.toString();
        }
    }
      //admin panelinde gelen mesajları silen fonk.
      public String mesajSil(){
        try {
          FacesContext fc = FacesContext.getCurrentInstance();
        this.mesajID = Integer.parseInt(IDParametresiniAl(fc));
        con = getConn();
        ps=con.prepareStatement("delete from mesajlar where id=?");
               ps.setInt(1, mesajID);
               int i=ps.executeUpdate();
               if(i>0)
                return "mesajlarim?faces-redirect=true";
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
	return parametreler.get("mesajID");
    }
    
    
     public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
    
    public int getMesajID() {
        return mesajID;
    }

    public void setMesajID(int mesajID) {
        this.mesajID = mesajID;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
    
    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getAdsoyad() {
        return adsoyad;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }
}
