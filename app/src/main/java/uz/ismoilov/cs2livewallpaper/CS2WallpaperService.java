package uz.ismoilov.cs2livewallpaper;
import android.service.wallpaper.*;import android.view.*;import android.graphics.*;import android.os.*;import java.text.*;import java.util.*;

public class CS2WallpaperService extends WallpaperService{
 public Engine onCreateEngine(){return new E();}
 class E extends Engine{Handler h=new Handler();boolean visible=true;Paint p=new Paint(1);long lastWeather=0;Runnable draw=()->{drawFrame();};
  public void onVisibilityChanged(boolean v){visible=v;if(v)drawFrame();else h.removeCallbacks(draw);} public void onSurfaceDestroyed(SurfaceHolder sh){visible=false;h.removeCallbacks(draw);} 
  void drawFrame(){SurfaceHolder sh=getSurfaceHolder();Canvas c=null;try{c=sh.lockCanvas();if(c!=null)paint(c);}finally{if(c!=null)sh.unlockCanvasAndPost(c);}h.removeCallbacks(draw);if(visible)h.postDelayed(draw,33);long now=System.currentTimeMillis();if(now-lastWeather>20*60*1000){lastWeather=now;WeatherStore.refresh(CS2WallpaperService.this);} }
  void paint(Canvas c){int w=c.getWidth(),hh=c.getHeight();float t=(System.currentTimeMillis()%100000)/1000f;Paint g=p;LinearGradient bg=new LinearGradient(0,0,0,hh,0xff01050b,0xff071829,Shader.TileMode.CLAMP);g.setShader(bg);c.drawRect(0,0,w,hh,g);g.setShader(null);
   // smoky blue energy
   for(int i=0;i<55;i++){float x=(float)((Math.sin(t*.7+i*9.1)+1)*.5*w);float y=(float)(((i*97+t*18)%hh));g.setColor(0x2200aaff);g.setStrokeWidth(1.2f);c.drawCircle(x,y,1+(i%4),g);} 
   // soldier silhouette inspired, original artwork
   g.setStyle(Paint.Style.STROKE);g.setStrokeWidth(w*.018f);g.setColor(0xaa0e8fe8);float sx=w*.50f, sy=hh*.28f; c.drawCircle(sx,sy,w*.12f,g); c.drawRoundRect(sx-w*.18f,sy+w*.1f,sx+w*.18f,sy+hh*.26f,w*.05f,w*.05f,g); g.setStrokeWidth(w*.025f); c.drawLine(sx-w*.08f,sy+hh*.20f,sx+w*.28f,sy+hh*.30f,g); g.setStrokeWidth(w*.012f); c.drawLine(sx+w*.1f,sy+hh*.23f,sx+w*.42f,sy+hh*.20f,g); c.drawRect(sx+w*.28f,sy+hh*.16f,sx+w*.58f,sy+hh*.22f,g); g.setStyle(Paint.Style.FILL);
   // top status/time
   g.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL));g.setColor(0xffffffff);g.setTextSize(w*.18f);String time=new SimpleDateFormat("HH:mm",Locale.US).format(new Date());c.drawText(time,w*.06f,hh*.12f,g);g.setTextSize(w*.04f);c.drawText(new SimpleDateFormat("dd MMM yyyy, EEEE",Locale.US).format(new Date()),w*.065f,hh*.155f,g);
   // CS2 label
   g.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC));g.setTextSize(w*.13f);g.setColor(0xff0aa8ff);c.drawText("CS2",w*.68f,hh*.13f,g);g.setTypeface(Typeface.DEFAULT);
   // weather panel
   float x=w*.06f,y=hh*.52f,pw=w*.88f,ph=hh*.16f;round(c,x,y,pw,ph,0x99071322,0xff0ba7ff);g.setTextSize(w*.038f);g.setColor(0xffffffff);String[] lines=WeatherStore.summary(CS2WallpaperService.this).split("\\n");for(int i=0;i<lines.length;i++){g.setTextSize(i==1?w*.064f:w*.034f);c.drawText(lines[i],x+w*.04f,y+w*.045f+i*w*.06f,g);}cloud(c,x+pw-w*.22f,y+ph*.45f,w*.12f,t);
   // app mock panel
   float py=hh*.705f;round(c,w*.06f,py,w*.88f,hh*.17f,0x77081325,0xff075e9c);String[] apps={"Telegram","WhatsApp","YouTube","Instagram","Chrome","Play","Settings","Gmail"};for(int i=0;i<8;i++){float ix=w*(.15f+(i%4)*.22f),iy=py+hh*(.045f+(i/4)*.07f);g.setColor(0xff06213d);c.drawRoundRect(ix-w*.045f,iy-w*.045f,ix+w*.045f,iy+w*.045f,w*.018f,w*.018f,g);g.setStyle(Paint.Style.STROKE);g.setStrokeWidth(2);g.setColor(0xff00aaff);c.drawRoundRect(ix-w*.045f,iy-w*.045f,ix+w*.045f,iy+w*.045f,w*.018f,w*.018f,g);g.setStyle(Paint.Style.FILL);g.setTextSize(w*.021f);g.setTextAlign(Paint.Align.CENTER);g.setColor(0xffffffff);c.drawText(apps[i],ix,iy+w*.071f,g);g.setTextSize(w*.04f);g.setColor(0xff11b9ff);c.drawText(symbol(i),ix,iy+w*.014f,g);g.setTextAlign(Paint.Align.LEFT);} 
   // dock
   round(c,w*.08f,hh*.9f,w*.84f,hh*.07f,0x66081325,0xff0b6fab);for(int i=0;i<4;i++){float ix=w*(.2f+i*.2f),iy=hh*.935f;g.setColor(0xff04172b);c.drawRoundRect(ix-w*.045f,iy-w*.035f,ix+w*.045f,iy+w*.035f,w*.018f,w*.018f,g);g.setStyle(Paint.Style.STROKE);g.setStrokeWidth(2);g.setColor(0xff0aa8ff);c.drawRoundRect(ix-w*.045f,iy-w*.035f,ix+w*.045f,iy+w*.035f,w*.018f,w*.018f,g);g.setStyle(Paint.Style.FILL);} 
  }
  void round(Canvas c,float x,float y,float w,float h,int fill,int stroke){p.setStyle(Paint.Style.FILL);p.setColor(fill);c.drawRoundRect(x,y,x+w,y+h,28,28,p);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setColor(stroke);c.drawRoundRect(x,y,x+w,y+h,28,28,p);p.setStyle(Paint.Style.FILL);} 
  String symbol(int i){return new String[]{"✈","☏","▶","◎","◉","▷","⚙","✉"}[i];}
  void cloud(Canvas c,float x,float y,float r,float t){p.setStyle(Paint.Style.FILL);p.setColor(0xffcfefff);c.drawCircle(x,y,r,p);c.drawCircle(x+r*.7f,y+r*.1f,r*.75f,p);c.drawCircle(x-r*.6f,y+r*.2f,r*.65f,p);p.setColor(0xff0aa8ff);p.setStrokeWidth(4);c.drawLine(x-r*.1f,y+r*1.05f,x-r*.35f,y+r*1.55f,p);c.drawLine(x+r*.35f,y+r*1.0f,x+r*.08f,y+r*1.55f,p);} }
}
