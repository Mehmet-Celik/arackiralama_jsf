import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.sql.Connection;
import java.util.Map;
import javax.faces.context.FacesContext;




@ManagedBean
@RequestScoped
public class aracislem  extends SqlConnections.JSqlConnection {

private String ad;
private String model;
private int yil;
private int koltuk;
private int kapi;
private String vites;
private int bagaj;
private String yakit;
private String klima;
private int fiyat;
private String imgyol;
private int marka;
private int markaID;
private int aracID;
private PreparedStatement ps=null;
private Connection con=null;
private InputStream in;
private javax.servlet.http.Part image;
private boolean upladed;
private String imgpath;

   
    

    //aracekle ve aracguncelle için resimupload fonk.
    
  public void doUpload(){
        try{
             in=image.getInputStream();
            imgpath=image.getSubmittedFileName();
            File f=new File("C:\\Users\\Galip\\Documents\\NetBeansProjects\\AracKiralama\\web\\assets\\img\\arac\\"+imgpath);
            f.createNewFile();
            FileOutputStream out=new FileOutputStream(f);
            
            byte[] buffer=new byte[1024];
            int length;
            
            while((length=in.read(buffer))>0){
                out.write(buffer, 0, length);
            }
            
            out.close();
            in.close();
            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", f.getAbsolutePath());
            upladed=true;
            
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    
    }

   

//admin panelden araçekleme fonk

    public String aracekle()
    {
        try {
            doUpload();
            //path=file.getName();
        con = getConn();
        ps=con.prepareStatement("insert into araclar(marka,model,yil,koltuk,kapi,vites,bagaj,yakit,klima,fiyat,image) values(?,?,?,?,?,?,?,?,?,?,?)");
                
                ps.setInt(1, marka);
                ps.setString(2, model);
                ps.setInt(3, yil);
                ps.setInt(4, koltuk);
                ps.setInt(5, kapi);
                ps.setString(6, vites);
                ps.setInt(7, bagaj);
                ps.setString(8, yakit);
                ps.setString(9, klima);
                ps.setInt(10, fiyat);
                ps.setString(11, imgpath);
                ps.executeUpdate();
                return "araclar?faces-redirect=true";
        }
        catch(Exception e){ 
        System.out.println(e);
        return "hatali"+e.toString();
        }
    
    }
    
    //admin sayfası için aracsilme fonk.

    public String aracsil(){
        try {
          FacesContext fc = FacesContext.getCurrentInstance();
        this.aracID = Integer.parseInt(IDParametresiniAl(fc));
        con = getConn();
        ps=con.prepareStatement("delete from araclar where id=?");
               ps.setInt(1, aracID);
               int i=ps.executeUpdate();
               if(i>0)
                return "araclar?faces-redirect=true";
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
	return parametreler.get("aracID");
        //IDParametresiniAl(fc)
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    ///güncelleme yapabilmek için arac bilgilerini inputlara doldurma fonk.
      public String aracDoldur() { 
        
    try {
        
        FacesContext fc = FacesContext.getCurrentInstance();
       aracID = Integer.parseInt(IDParametresiniAl(fc));
        con = getConn();
         ps=con.prepareStatement("select * from araclar left join marka on araclar.marka=marka.id where araclar.id="+aracID);
         ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
           marka=rs.getInt("marka");
           model=rs.getString("model");
           yil=rs.getInt("yil");
           koltuk=rs.getInt("koltuk");
           kapi=rs.getInt("kapi");
           vites=rs.getString("vites");
           bagaj=rs.getInt("bagaj");
           yakit=rs.getString("yakit");
           fiyat=rs.getInt("fiyat");
           klima=rs.getString("klima");
           aracID=rs.getInt("id");
           imgpath="assets\\img\\arac\\"+rs.getString("image");
        }
        return "aracguncelle";
         
    
    }
    catch(Exception e){
        System.out.println(e);
        return "null";
    }
   }
   String eskiyol;

 
   //admin sayfasında db için arac güncelleme fonk.
    public String aracGuncelle(){
        try {
           eskiyol=imgpath;
            doUpload();
            con = getConn();
            if(imgpath == null ? eskiyol == null : imgpath.equals(eskiyol))
        ps=con.prepareStatement("update araclar set marka="+marka+", model='"+model+"', yil="+yil+", koltuk="+koltuk+", kapi="+kapi+", vites='"+vites+"', bagaj="+bagaj+", yakit='"+yakit+"', klima='"+klima+"', fiyat="+fiyat+" where id="+aracID);
            else 
               ps=con.prepareStatement("update araclar set marka="+marka+", model='"+model+"', yil="+yil+", koltuk="+koltuk+", kapi="+kapi+", vites='"+vites+"', bagaj="+bagaj+", yakit='"+yakit+"', klima='"+klima+"', fiyat="+fiyat+", image='"+imgpath+"' where id="+aracID);
     
        int i=ps.executeUpdate();
        if(i>0)
            return "araclar?faces-redirect=true";
        else 
            return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return e.toString();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //admin sayfasında marka eklemek için.
    public String markaEkle() throws SQLException{
    
    try {
        con = getConn();
        ps=con.prepareStatement("insert into marka(ad) values(?)");
              
               ps.setString(1, ad);
               ps.executeUpdate();
                
               return "markalar?faces-redirect=true";
        }
        catch(Exception e){ 
                System.out.println(e);
                return "marka eklenmedi";
        }
    finally{
        con.close();
    }
    

    }
//admin sayfasında seçilen markayı silme fonk.
       public String markaSil(){
        try {
          FacesContext fc = FacesContext.getCurrentInstance();
        this.markaID = Integer.parseInt(markaIDParametresiniAl(fc));
        con = getConn();
        ps=con.prepareStatement("delete from marka where id=?");
               ps.setInt(1, markaID);
               int i=ps.executeUpdate();
               if(i>0)
                return "basarili";
               else 
                     return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return "hatali";
        }
    }
       //marka düzenlemek için marka bilgilerini input neesnesine doldurma fonk.
       public String markaDoldur()
       {
            try {
        
        FacesContext fc = FacesContext.getCurrentInstance();
       markaID = Integer.parseInt(markaIDParametresiniAl(fc));
        con = getConn();
         ps=con.prepareStatement("select * from marka where id="+markaID);
         ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
           markaID=rs.getInt("id");
           ad=rs.getString("ad");
           
        }
        return "markaguncelle";
         
   
    }
    catch(Exception e){
        System.out.println(e);
        return "null";
    }
          
       }
       //marka güncelleme işlemi
       public String markaGuncelle(){
        try {
        con = getConn();
        ps=con.prepareStatement("update marka set ad='"+ad+"' where id="+markaID);
     
        int i=ps.executeUpdate();
        if(i>0)
            return "markalar?faces-redirect=true";
        else 
            return "hatali";
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
        return e.toString();
        }
    }
       //markaID isini query olarak alıyoruz.
    public String markaIDParametresiniAl(FacesContext fc)
    {
        Map<String,String> parametreler = fc.getExternalContext().getRequestParameterMap();
	return parametreler.get("markaID");
    }
    private String[][] gelenaraclar =new String[10][5]; 



  //secilen araba sayfasına resmini getirme fonk.
    public void resimGetir()
    {
        
        try {
           int a=0;
        con=getConn();
        ps=con.prepareStatement("select * from araclar left join marka on araclar.marka=marka.id");
         ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
          gelenaraclar[a][0]=rs.getString("id");
          gelenaraclar[a][1]=rs.getString("marka.ad");
          gelenaraclar[a][2]=rs.getString("model");
          gelenaraclar[a][3]=rs.getString("fiyat");
          gelenaraclar[a][4]="assets\\img\\arac\\"+rs.getString("image");
          a++;
           
        }
       
        }
        catch(NumberFormatException | SQLException e){ 
        System.out.println(e);
     
        }
    }
    
    
    //set ve get fonklar
    public String[][] getGelenaraclar() {
        return gelenaraclar;
    }

    public void setGelenaraclar(String[][] gelenaraclar) {
        this.gelenaraclar = gelenaraclar;
    }
     public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public int getAracID() {
        return aracID;
    }

    public void setAracID(int aracID) {
        this.aracID = aracID;
    }


    public int getMarkaID() {
        return markaID;
    }

    public void setMarkaID(int markaID) {
        this.markaID = markaID;
    }


    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getMarka() {
        return marka;
    }

    public void setMarka(int marka) {
        this.marka = marka;
    }

    public String getImgyol() {
        return imgyol;
    }

    public void setImgyol(String imgyol) {
        this.imgyol = imgyol;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
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
    public int getBagaj() {
        return bagaj;
    }

    public void setBagaj(int bagaj) {
        this.bagaj = bagaj;
    }
    public String getVites() {
        return vites;
    }

    public void setVites(String vites) {
        this.vites = vites;
    }
    public int getKapi() {
        return kapi;
    }

    public void setKapi(int kapi) {
        this.kapi = kapi;
    }
    public int getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(int koltuk) {
        this.koltuk = koltuk;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
     public javax.servlet.http.Part getImage() {
        return image;
    }

    public void setImage(javax.servlet.http.Part image) {
        this.image = image;
    }

    public boolean isUpladed() {
        return upladed;
    }

    public void setUpladed(boolean upladed) {
        this.upladed = upladed;
    }

   public String getEskiyol() {
        return eskiyol;
    }

    public void setEskiyol(String eskiyol) {
        this.eskiyol = eskiyol;
    }
   
    
}
