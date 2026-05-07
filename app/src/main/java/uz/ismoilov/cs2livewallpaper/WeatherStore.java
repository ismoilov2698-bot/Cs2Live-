package uz.ismoilov.cs2livewallpaper;

import android.content.*;import org.json.*;import java.io.*;import java.net.*;import java.util.*;
public class WeatherStore{
 static final String P="cs2prefs";
 static final Map<String,double[]> C=new HashMap<>(); static{C.put("Tashkent",new double[]{41.3111,69.2797});C.put("Andijan",new double[]{40.7821,72.3442});C.put("Asaka",new double[]{40.6415,72.2387});C.put("Fergana",new double[]{40.3734,71.7978});C.put("Samarkand",new double[]{39.6270,66.9750});}
 public static String city(Context c){return c.getSharedPreferences(P,0).getString("city","Tashkent");}
 public static void city(Context c,String v){c.getSharedPreferences(P,0).edit().putString("city",v).apply();}
 public static String summary(Context c){SharedPreferences p=c.getSharedPreferences(P,0);return p.getString("summary", city(c)+", Uzbekistan\n--°  Loading weather");}
 public static void refresh(Context ctx){new Thread(()->{try{String city=city(ctx);double[] ll=C.get(city);String u="https://api.open-meteo.com/v1/forecast?latitude="+ll[0]+"&longitude="+ll[1]+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m,pressure_msl,weather_code&timezone=auto";HttpURLConnection con=(HttpURLConnection)new URL(u).openConnection();con.setConnectTimeout(8000);con.setReadTimeout(8000);BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));StringBuilder sb=new StringBuilder();String line;while((line=br.readLine())!=null)sb.append(line);JSONObject cur=new JSONObject(sb.toString()).getJSONObject("current");int temp=(int)Math.round(cur.getDouble("temperature_2m"));int hum=cur.getInt("relative_humidity_2m");int wind=(int)Math.round(cur.getDouble("wind_speed_10m"));int press=(int)Math.round(cur.getDouble("pressure_msl"));String text=code(cur.getInt("weather_code"));String sum=city+", Uzbekistan\n"+temp+"°  "+text+"\nHumidity "+hum+"%   Wind "+wind+" km/h\nPressure "+press+" hPa";ctx.getSharedPreferences(P,0).edit().putString("summary",sum).putLong("weather_time",System.currentTimeMillis()).apply();}catch(Exception e){}}).start();}
 static String code(int c){if(c==0)return "Clear"; if(c<4)return "Cloudy"; if(c<50)return "Fog"; if(c<70)return "Rain"; if(c<80)return "Snow"; if(c<90)return "Rain"; return "Storm";}
}
