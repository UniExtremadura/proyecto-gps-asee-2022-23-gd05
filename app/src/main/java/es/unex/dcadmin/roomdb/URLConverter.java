package es.unex.dcadmin.roomdb;

import androidx.room.TypeConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter {
    @TypeConverter
    public static URL str2URL(String url){
        if(url == null) return null;
        else{
            URL urlstring = null;
            try {
                urlstring = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return urlstring;
        }
    }
    @TypeConverter
    public static String URL2Str(URL url){
        return url == null ? null : url.toString() ;
    }
}
