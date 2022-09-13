package odev;

import java.io.*;
import java.util.*;

/** 
 * 
 * @author Ýsmail Buðan   / g201210304 / ismail.bugan2@ogr.sakarya.edu.tr
 * @since 04.04.2021-11.04.2021
 * <p> 
 *  BSM208 PROGRAMLAMA DÝLLERÝNÝN PRENSÝPLERÝ DERSÝ 1. ÖDEV  / ÞUBE:  2. ÖÐRETÝM A GRUBU
 * </p> 
 */ 

public class odev {
	 public static void main(String[] args) throws IOException {
	        
	        File file = new File("program.cpp"); 
	        BufferedReader br;
	        br = new BufferedReader(new FileReader(file));
	        String satir = br.readLine();
	        ArrayList<String> satirlar = new ArrayList<String>();
	        while(satir != null){
	            satirlar.add(satir);
	            satir = br.readLine();
	        }
	        br.close();
	        System.out.println("Sýnýf Adý: "+SinifAdi(satirlar));
	        System.out.println("Alt Elemanlar: "  + SinifinAltElemanlari(satirlar).size() + " adet");
	        for (String SinifAltElemanYaz : SinifinAltElemanlari(satirlar)) {
	            System.out.println(SinifAltElemanYaz);
	        }
	        System.out.println("Üye Fonksiyonlar: " + SinifinUyeFonksiyonlari(satirlar).size() + " adet");
	        for(String SinifUyeFonkYaz : SinifinUyeFonksiyonlari(satirlar)){
	            System.out.println(SinifUyeFonkYaz);
	            System.out.println("------------------");
	        }
	        
	    }  
	  public enum tipBelirle {
		    CLASS, METHOD, VARIABLE, PARAMETER, KAPATMA
		}
		    
		    public static tipBelirle TipiVer(String satir){
		       
		        tipBelirle tip = null;
		        if(satir.contains("class"))
		            {
		                tip = tipBelirle.CLASS;
		            }
		        else if(satir.contains("}"))
		            {
		                tip = tipBelirle.KAPATMA;
		            }
		            else if(satir.contains("()") ||satir.contains("int") || (satir.contains("String")) || satir.contains("boolean") || satir.contains("double") || satir.contains("void") || satir.contains("float"))  //Tanýmlanan tipler buradan eklenip çýkartýlabilir.
		            {
		                if( satir.contains("{") || satir.contains("(") || (!satir.contains("=") && !satir.contains(";"))) //method
		                {
		                    tip = tipBelirle.METHOD;
		                }
		             
		                
		                
		                else{ 
		                	
		                	
		                    tip = tipBelirle.VARIABLE;
		                }
		            }
		            
		        return tip;
		    }
		    public static String SinifAdi(ArrayList<String> satirlar) {
		        //Tüm satýrlarý alýp class adý geçen satýrý bulup gerekli string iþlemleri yaparak ismi döndürür.
		        String ad = null;
		        tipBelirle usttip1 = null; //en üstü
		        tipBelirle usttip2 = null; //þimdiki
		        for (int i = 0; i < satirlar.size(); i++) {
		            String ss = satirlar.get(i);
		            tipBelirle simdikitip = TipiVer(ss);
		            if(simdikitip == tipBelirle.CLASS && usttip2 == null)
		            {
		                ad = ss.substring(ss.indexOf("class") + 5,ss.indexOf("{")).trim();
		            }
		            if(simdikitip != null && simdikitip != tipBelirle.KAPATMA && (simdikitip == tipBelirle.CLASS || simdikitip == tipBelirle.METHOD)) {usttip1 = (usttip1 == null) ? simdikitip : usttip1; usttip2 = simdikitip;}
		            else if(simdikitip == tipBelirle.KAPATMA) usttip2 = usttip1;
		        }
		        return ad;
		    }
		    public static ArrayList<String> SinifinAltElemanlari(ArrayList<String> satirlar){
		        //Tüm satýrlarý alýp deðiþken isimleri geçenleri bulup gerekli string iþlemleri yaparak bir ArrayList döndürür, size’ý bize kaç adet alt eleman olduðu bilgisini verir.
		        ArrayList<String> elemanlar = new ArrayList<>();
		        tipBelirle usttip1 = null; //en üstü
		        tipBelirle usttip2 = null; //þimdiki
		        for (int i = 0; i < satirlar.size(); i++) {
		            String ss = satirlar.get(i);
		            tipBelirle simdikitip = TipiVer(ss);
		            if(simdikitip == tipBelirle.VARIABLE && usttip2 == tipBelirle.CLASS)
		            {
		                String tip = ss.split(" ")[(ss.split(" ").length-2)];
		                String isim = ss.split(" ")[(ss.split(" ").length-1)].substring(0,ss.split(" ")[(ss.split(" ").length-1)].length()-1);
		                elemanlar.add(isim + " - " + tip);
		            }
		            if(simdikitip != null && simdikitip != tipBelirle.KAPATMA && (simdikitip == tipBelirle.CLASS || simdikitip == tipBelirle.METHOD)) {usttip1 = (usttip1 == null) ? simdikitip : usttip1; usttip2 = simdikitip;}
		            else if(simdikitip == tipBelirle.KAPATMA) usttip2 = usttip1;
		        }
		        return elemanlar;
		    }
		    public static ArrayList<String> MethodunAldigiParametreler(String satir){
		        //Eðer verilen satir bir method tanýmlama satýrý ise methodun aldýðý parametreleri ArrayList olarak döndürür.
		        ArrayList<String> elemanlar = new ArrayList<>();
		        if(TipiVer(satir)==tipBelirle.METHOD){
		        String parametrebolum = satir.substring(satir.indexOf("(")+1,satir.indexOf(")"));
		        if(satir.contains("()")) return elemanlar;
		        String parametreler[] = parametrebolum.trim().split(",");
		        if(parametreler.length > 0){
		        for (String param : parametreler) {
		            elemanlar.add(param); //int deger
		        }
		        }
		        else {
		            if(parametrebolum != "" && parametrebolum != " ")
		            elemanlar.add(parametrebolum);
		     
		        }
		        }
		        if(elemanlar.size() > 0){
		            if(elemanlar.get(0) == "" || elemanlar.get(0).trim() == ""){
		                elemanlar.clear();
		            }
		        }
		        return elemanlar;
		    }
		    public static ArrayList<String> SinifinUyeFonksiyonlari(ArrayList<String> satirlar){
		        //MethodunAldigiParametreler adlý method ile çalýþarak string iþlemleri yapýp bize gerekli tüm verileri toplayýp bir ArrayList döndürür.
		        ArrayList<String> elemanlar = new ArrayList<>();
		        tipBelirle usttip1 = null; //en üstü
		        tipBelirle usttip2 = null; //þimdiki
		        for (int i = 0; i < satirlar.size(); i++) {
		            String ss = satirlar.get(i);
		            tipBelirle gunceltip = TipiVer(ss);
		            String isim = "";
		            if(gunceltip == tipBelirle.METHOD && usttip2 == tipBelirle.CLASS)
		            {
		                int boslukBeforeName = 0;
		                for(int j = 0; j<ss.split(" ").length; j++){
		                    if(ss.split(" ")[j].contains("(")){
		                        boslukBeforeName = ss.indexOf(ss.split(" ")[j]);
		                        break;
		                    }
		                }
		                isim = ss.substring(boslukBeforeName, ss.indexOf("(")).trim();
		                int parSayisi = MethodunAldigiParametreler(ss).size();
		                int tipOncekiAd = 0;
		                if(ss.contains("public"))
		                    tipOncekiAd = ss.indexOf("public") + "public".length();
		                else if(ss.contains("private"))
		                    tipOncekiAd = ss.indexOf("private") + "private".length();
		                else if(ss.contains("protected"))
		                    tipOncekiAd = ss.indexOf("protected") + "protected".length();
		                if(ss.contains("static"))
		                    tipOncekiAd = ss.indexOf("static") + "static".length(); 
		                String tip = ss.substring(tipOncekiAd, boslukBeforeName).trim();
		                if("".equals(tip)) tip = "Yok";
		                String eleman = isim + "\nDönüþ Türü: " + tip + "\nAldýðý Parametre: " + MethodunAldigiParametreler(ss).size();
		                for (String met : MethodunAldigiParametreler(ss)) {
		                    if(met.split(" ").length > 1){
		                    eleman+= "\n" + met.split(" ")[met.split(" ").length-1] + " - " + met.split(" ")[met.split(" ").length-2];
		                    }
		                }
		                elemanlar.add(eleman);
		            }
		            if(gunceltip != null && gunceltip != tipBelirle.KAPATMA && (gunceltip == tipBelirle.CLASS || gunceltip == tipBelirle.METHOD)) { usttip1 = (usttip1 == null) ? gunceltip : usttip1; usttip2 = gunceltip;}
		            else if(gunceltip == tipBelirle.KAPATMA ) { usttip2 = usttip1;}
		        }
		        return elemanlar;
		    }
		   
}