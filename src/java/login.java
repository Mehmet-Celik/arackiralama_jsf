import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class login extends SqlConnections.JSqlConnection {


     private String kul;
     private String pass;
     private PreparedStatement ps=null;
     private Connection con=null;
     private int id;
     private String marka;
     private String yil;
     private String model;
     private String koltuk;
     private String kapi;
     private String vites;
     private String bagaj;
     private String yakit;
     private String klima;
     private String fiyat;
     private String image;
     private String markam;
     private int markaid;
     private int mesajsayisi;
     private int kiralamaistekleri;
  
     
     

     public String giris(){
         
        try {
       con = getConn();
        ps=con.prepareStatement("select * from uyeler where kad=? and pass=?");
                ps.setString(1, kul);
                ps.setString(2, pass);
                ResultSet rs=ps.executeQuery();
            
                if(rs.next())
                   return "admin";
                else 
			return "login";
                
        
        }
        catch(Exception e){ 
        System.out.println(e);
        return "Hatam "+e;
        }
        
        }
     //admin paneli okunmamış mesaj sayılarını çeken fonk.
     public int karsilama()
     {
          try {
         con = getConn();
        ps=con.prepareStatement("select count(id) as sayi from mesajlar");
         ResultSet rs=ps.executeQuery();
         if(rs.next())
             mesajsayisi=Integer.parseInt(rs.getString("sayi"));
         return mesajsayisi;
        }
        catch(Exception e){ 
        System.out.println(e);
        return 0;
        }
     }
     
     //admin paneli beklemede olan kiralama isteklerinin sayısını çeken fonk.
      public int karsilama2()
     {
          try {
         con = getConn();
        ps=con.prepareStatement("select count(id) as sayi from kiralama where durum='Beklemede'");
         ResultSet rs=ps.executeQuery();
         if(rs.next())
             kiralamaistekleri=Integer.parseInt(rs.getString("sayi"));
         return kiralamaistekleri;
        }
        catch(Exception e){ 
        System.out.println(e);
        return 0;
        }
     }
     
   
   
     //araclar sayfasına kayıtlı olan araçları getiren fonk.
   public List<login> araclarcek() { 
        
    try {
        con = getConn();
         ps=con.prepareStatement("select * from araclar left join marka on araclar.marka=marka.id");
         ResultSet rs=ps.executeQuery();
         List<login> liste=new ArrayList<>();
         while(rs.next())
        {
            login listem=new login();
            listem.setMarka(rs.getString("ad"));
            listem.setyil(rs.getString("yil"));
            listem.setModel(rs.getString("model"));
            listem.setKoltuk(rs.getString("koltuk"));
            listem.setKapi(rs.getString("kapi"));
            listem.setVites(rs.getString("vites"));
            listem.setBagaj(rs.getString("bagaj"));
            listem.setYakit(rs.getString("yakit"));
            listem.setKlima(rs.getString("klima"));
            listem.setFiyat(rs.getString("fiyat"));
            listem.setImage(rs.getString("image"));
            listem.setId(rs.getInt("id"));
            liste.add(listem);
            
        }
         return liste;
    
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
   }
   
   //kaydedilen markaları getiren fonk.
   
   public List<login> markayukle(){
       try {
      con = getConn();
         ps=con.prepareStatement("select * from marka");
         ResultSet rs=ps.executeQuery();
         List<login> markalist=new ArrayList<>();
         
         while(rs.next())
        {
             login listem=new login();
             listem.setMarkam(rs.getString("ad"));
            listem.setMarkaid(rs.getInt("id"));
            markalist.add(listem);
        }
         return markalist;
       }
       catch(Exception e)
       {
           System.out.println(e);
        return null;
       }
   }

   
     public String getkul()
     {
         return kul;
     }
        
      public void setkul(String kul)
     {
         this.kul=kul;
         
     }
      public String getpass()
     {
         return pass;
     }
        
      public void setpass(String pass)
     {
         this.pass=pass;
         
     }
    public int getMesajsayisi() {
        return mesajsayisi;
    }

    public void setMesajsayisi(int mesajsayisi) {
        this.mesajsayisi = mesajsayisi;
    }

    public int getKiralamaistekleri() {
        return kiralamaistekleri;
    }

    public void setKiralamaistekleri(int kiralamaistekleri) {
        this.kiralamaistekleri = kiralamaistekleri;
    }
    public int getMarkaid() {
        return markaid;
    }

    public void setMarkaid(int markaid) {
        this.markaid = markaid;
    }
     

    public String getMarkam() {
        return markam;
    }

    public void setMarkam(String markam) {
        this.markam = markam;
    }
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
     
    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
      public String getKlima() {
        return klima;
    }

    public void setKlima(String klima) {
        this.klima = klima;
    }
      public String getYakit() {
        return yakit;
    }

    public void setYakit(String yakit) {
        this.yakit = yakit;
    }
     public String getBagaj() {
        return bagaj;
    }

    public void setBagaj(String bagaj) {
        this.bagaj = bagaj;
    }
  public String getVites() {
        return vites;
    }

    public void setVites(String vites) {
        this.vites = vites;
    }
    public String getKapi() {
        return kapi;
    }

    public void setKapi(String kapi) {
        this.kapi = kapi;
    }

    public String getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(String koltuk) {
        this.koltuk = koltuk;
    }
     public String getMarka()
     {
         return marka;
     }
     public void setMarka(String marka)
     {
         this.marka=marka;
     }
      public String getModel()
     {
         return model;
     }
     public void setModel(String model)
     {
         this.model=model;
     }
     public String getyil()
     {
         return yil;
     }
     public void setyil(String yil)
     {
         this.yil=yil;
     }
    
}
