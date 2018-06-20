

import java.sql.Connection;
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
public class kiralama extends SqlConnections.JSqlConnection {
private PreparedStatement ps=null;
private Connection con=null;
private int[] gunum={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
private int[] ayim={1,2,3,4,5,6,7,8,9,10,11,12};
private int[] yilim={2017,2018,2019,2020};
private int aracid;
private int yil;
private int koltuk;
private int kapi;
private String vites;
private int bagaj;
private String yakit;
private String klima;
private int fiyat;
private String imgyol;
private String marka;
private String model;
private int byil;
private int bay;
private int bgun;
private int syil;
private int say;
private int sgun;
private String ad;
private String soyad;
private String mail;
private String yas;
private String tel;
private int id;
  private String starih;
  private String btarih;
  private String toplamgun;
  private String toplamfiyat;
  private String durum;
private String aracismi;
private int kiraID;

 

  
   


    
    //gelen aracın IDsini almak için fonk.
      public String IDParametresiniAl(FacesContext fc)
    {
        Map<String,String> parametreler = fc.getExternalContext().getRequestParameterMap();
	return parametreler.get("aracID");
        //IDParametresiniAl(fc)
    }
      
      //kiralama isteklerinin IDsini alan fonk.
      public String KiraIDParametresiniAl(FacesContext fc)
    {
        Map<String,String> parametreler = fc.getExternalContext().getRequestParameterMap();
	return parametreler.get("kiraID");
        //IDParametresiniAl(fc)
    }
      
      //kiralama sayfasında aracın bilgilerini ekrana dolduran fonk.
   public String aracYukle()
       {
            try {
        
                FacesContext fc = FacesContext.getCurrentInstance();
               aracid = Integer.parseInt(IDParametresiniAl(fc));
                con = getConn();
                 ps=con.prepareStatement("select * from araclar left join marka on marka.id=araclar.marka where araclar.id="+aracid);
                 ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                   aracid=rs.getInt("id");
                   marka=rs.getString("marka.ad");
                   model=rs.getString("model");
                   yil=rs.getInt("yil");
                   koltuk=rs.getInt("koltuk");
                   kapi=rs.getInt("kapi");
                   vites=rs.getString("vites");
                   bagaj=rs.getInt("bagaj");
                   yakit=rs.getString("yakit");
                   klima=rs.getString("klima");
                   fiyat=rs.getInt("fiyat");
                   imgyol="assets\\img\\arac\\"+rs.getString("image");

           
                 }
                return "arackiralama";
         
   
                }
                catch(Exception e){
                    System.out.println(e);
                    return "null";
                }
       }
  
     //secilen tarihleri hesaplayıp toplam kiralanmak istenen günü bulan fonk.
     public int THesapla()
     {
         int sonuc=((byil-syil)*365)+((bay-say)*30)+(bgun-sgun);
         return sonuc;
     }

     
     ///kullanıcının seçtiği arac için kiralama isteiği kaydeden fonk.
public String kiraKayıt()
{
      try {
        
        int sonucum=THesapla();
        int fiyatim=sonucum*fiyat;
        con = getConn();
        ps=con.prepareStatement("insert into kiralama(aracid,ad,soyad,tel,mail,yas,starih,btarih,toplam,tfiyat,durum) values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, aracid);
        ps.setString(2, ad);
        ps.setString(3, soyad);
        ps.setString(4, tel);
        ps.setString(5, mail);
        ps.setString(6, yas);
        ps.setString(7, sgun+"-"+say+"-"+syil);
         ps.setString(8, bgun+"-"+bay+"-"+byil);
         ps.setInt(9, sonucum);
         ps.setInt(10, fiyatim);
         ps.setString(11, "Beklemede");
        ps.executeUpdate();
        return "index?faces-redirect=true";
        }
        catch(Exception e){
          System.out.println(e);
               return "hata!"+e.toString();     
        }
}
  public String geri()
  {
      return "index.xhtml";
  }
     //admin panel için kiralama isteklerini datatable'a yükleme fonk.
  public List<kiralama> kiralamaYukle()
  {
       try {
        con = getConn();
         ps=con.prepareStatement("select * from kiralama left join araclar on kiralama.aracid=araclar.id left join marka on marka.id=araclar.marka");
         ResultSet rs=ps.executeQuery();
         List<kiralama> liste=new ArrayList<>();
         while(rs.next())
        {
            kiralama listem=new kiralama();
            listem.setId(rs.getInt("kiralama.id"));
            listem.setAracismi(rs.getString("marka.ad")+" - "+rs.getString("araclar.model"));
            listem.setAd(rs.getString("ad"));
            listem.setSoyad(rs.getString("soyad"));
            listem.setTel(rs.getString("tel"));
            listem.setMail(rs.getString("mail"));
            listem.setYas(rs.getString("yas"));
            listem.setStarih(rs.getString("starih"));
            listem.setBtarih(rs.getString("btarih"));
            listem.setFiyat(rs.getInt("araclar.fiyat"));
            listem.setToplamgun(rs.getString("toplam"));
            listem.setToplamfiyat(rs.getString("tfiyat"));
            listem.setDurum(rs.getString("durum"));
            liste.add(listem);
            
        }
         return liste;
    
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
      
  }
  
  
  //kiralama isteklerini silen  fonk.
 public String kiralamaSil(){
        try {
          FacesContext fc = FacesContext.getCurrentInstance();
        kiraID = Integer.parseInt(KiraIDParametresiniAl(fc));
        con = getConn();
        ps=con.prepareStatement("delete from kiralama where id=?");
               ps.setInt(1, kiraID);
               int i=ps.executeUpdate();
               if(i>0)
                return "kirakontrol?faces-redirect=true";
               else 
                     return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return "hatali";
        }
    }
 
 
 //admin panelinden eğer admin onaylarsa durumunu onaylayan fonk.
  public String kiraGuncelle(){
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
        kiraID = Integer.parseInt(KiraIDParametresiniAl(fc));
        ps=con.prepareStatement("update kiralama set durum='Onaylandi'  where id="+kiraID);
        
        int i=ps.executeUpdate();
        if(i>0)
            return "kirakontrol?faces-redirect=true";
        else 
            return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return e.toString();
        }
    }

   public int getKiraID() {
        return kiraID;
    }

    public void setKiraID(int kiraID) {
        this.kiraID = kiraID;
    }
    public String getStarih() {
        return starih;
    }

    public void setStarih(String starih) {
        this.starih = starih;
    }

    public String getBtarih() {
        return btarih;
    }

    public void setBtarih(String btarih) {
        this.btarih = btarih;
    }
  
  
  public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getYas() {
        return yas;
    }

    public void setYas(String yas) {
        this.yas = yas;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getByil() {
        return byil;
    }

    public void setByil(int byil) {
        this.byil = byil;
    }

    public int getBay() {
        return bay;
    }

    public void setBay(int bay) {
        this.bay = bay;
    }

    public int getBgun() {
        return bgun;
    }

    public void setBgun(int bgun) {
        this.bgun = bgun;
    }

    public int getSyil() {
        return syil;
    }

    public void setSyil(int syil) {
        this.syil = syil;
    }

    public int getSay() {
        return say;
    }

    public void setSay(int say) {
        this.say = say;
    }

    public int getSgun() {
        return sgun;
    }

    public void setSgun(int sgun) {
        this.sgun = sgun;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }
    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public int getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(int koltuk) {
        this.koltuk = koltuk;
    }

    public int getKapi() {
        return kapi;
    }

    public void setKapi(int kapi) {
        this.kapi = kapi;
    }

    public String getVites() {
        return vites;
    }

    public void setVites(String vites) {
        this.vites = vites;
    }

    public int getBagaj() {
        return bagaj;
    }

    public void setBagaj(int bagaj) {
        this.bagaj = bagaj;
    }

    public String getYakit() {
        return yakit;
    }

    public void setYakit(String yakit) {
        this.yakit = yakit;
    }

    public String getKlima() {
        return klima;
    }

    public void setKlima(String klima) {
        this.klima = klima;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public String getImgyol() {
        return imgyol;
    }

    public void setImgyol(String imgyol) {
        this.imgyol = imgyol;
    }
public int[] getGunum() {
        return gunum;
    }

    public void setGunum(int[] gunum) {
        this.gunum = gunum;
    }

    public int[] getAyim() {
        return ayim;
    }

    public void setAyim(int[] ayim) {
        this.ayim = ayim;
    }

    public int[] getYilim() {
        return yilim;
    }

    public void setYilim(int[] yilim) {
        this.yilim = yilim;
    }


    public int getAracid() {
        return aracid;
    }

    public void setAracid(int aracid) {
        this.aracid = aracid;
    }
        public int getId() {
        return id;
    }
          public String getAracismi() {
        return aracismi;
    }

    public void setAracismi(String aracismi) {
        this.aracismi = aracismi;
    }

    public void setId(int id) {
        this.id = id;
    }
     public String getToplamgun() {
        return toplamgun;
    }

    public void setToplamgun(String toplamgun) {
        this.toplamgun = toplamgun;
    }

    public String getToplamfiyat() {
        return toplamfiyat;
    }

    public void setToplamfiyat(String toplamfiyat) {
        this.toplamfiyat = toplamfiyat;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

}
