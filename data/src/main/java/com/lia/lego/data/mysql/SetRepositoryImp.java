package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonHelper;
import com.lia.common.spring.CommonObject;
import com.lia.common.spring.FileHelper;
import com.lia.common.spring.HibernateHelper;
import com.lia.lego.model.Set;
import com.lia.lego.model.SubTheme;
import com.lia.lego.model.Theme;

import com.lia.lego.data.SetRepository;

public class SetRepositoryImp implements SetRepository{

   private CommonHelper commonHelper;
   private FileHelper fileHelper;

   public void setCommonHelper(CommonHelper commonHelper) {
      this.commonHelper = commonHelper;
   }

   public void setFileHelper(FileHelper fileHelper) {
      this.fileHelper = fileHelper;
   }

   public void delete(CommonObject obj) {
      Set set = (Set) obj;
      HibernateHelper.currentSession().delete(set);
   }

   public void create(CommonObject obj) {
      Set set = (Set) obj;
      HibernateHelper.currentSession().save(set);
   }

   public void update(CommonObject obj) {
      Set set = (Set) obj;
      HibernateHelper.currentSession().update(set);
   }

   public CommonObject retrieveAccordingKey(UUID key) {
      CommonObject output = null;
      String hql = "from com.lia.lego.model.Set as s where s.Key=:key";
      Query query = HibernateHelper.currentSession().createQuery(hql);
      query.setString("key", key.toString());
            
      List<Set> setList = query.list();
      if (setList.size() > 0){
         output = setList.get(0);
      }
      return output;
   }

   public List<CommonObject> retrieve() {
      List<CommonObject> output = new ArrayList<CommonObject>();
      String hql = "from com.lia.lego.model.Set";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<Set> setList = query.list();
      for (Set set : setList) {
         output.add(set);
      }
      return output;
   }

   public void initialize() throws Exception{
      /*Integer count = 0;
      List<String> logList = new ArrayList<String>();

      com.lia.lego.data.RawSetRepository setHandler = new com.lia.lego.data.mysql.RawSetRepositoryImp();
      ThemeHandler themeHandler = new ThemeHandler();
      SubThemeHandler subThemeHandler = new SubThemeHandler();

      Session session = HibernateHelper.currentSession();

      session.beginTransaction();
      String script = "delete from com.lia.lego.model.Set";
      Query query = session.createQuery(script);
      query.executeUpdate();
      List<CommonObject> themeList = themeHandler.retrieve();
      for (CommonObject themeObject : themeList) {
         Theme theme = (Theme) themeObject;
         String themeName = theme.getName();
         UUID themeKey = theme.getKey();
         logList.add(String.format("%s,[%s]", themeName, themeKey.toString()));
         List<CommonObject> subThemeList = subThemeHandler.getSubThemeAccordingThemeKey(theme.getKey());

         for (CommonObject subThemeObject : subThemeList) {
            SubTheme subTheme = (SubTheme) subThemeObject;
            String subThemeName = subTheme.getName();
            UUID subThemeKey = subTheme.getKey();
            logList.add(String.format("   %s,[%s]", subThemeName, subThemeKey.toString()));
            List<CommonObject> setList = setHandler.getSetAccordingTheme(themeName, subThemeName);
            count += setList.size();
            for (CommonObject setObject : setList) {
               com.lia.lego.brickset.model.Set brickSet = (com.lia.lego.brickset.model.Set) setObject;
               UUID key = UUID.randomUUID();

               try {
                  String number = brickSet.getNumber();
                  Integer variant = Integer.parseInt(brickSet.getVariant());
                  Short year = Short.parseShort(brickSet.getYear());
                  String name = brickSet.getName();
                  Short defaultMinifigs = 0;
                  
                  Short minifigs = commonHelper.convertToShort(brickSet.getMinifigs(), defaultMinifigs);
                  Integer pieces = commonHelper.convertToInteger(brickSet.getPieces(), 0);
                  Float priceUK = commonHelper.convertToFloat(brickSet.getPriceUK(), 0f);
                  Float priceUS = commonHelper.convertToFloat(brickSet.getPriceUS(), 0f);
                  Float priceCA = commonHelper.convertToFloat(brickSet.getPriceCA(), 0f);
                  Float priceEU = commonHelper.convertToFloat(brickSet.getPriceEU(), 0f);
                  String imageURL = brickSet.getImageURL();
                  logList.add(String.format("      %s,[%s]", number, name));
                  Set set = new Set(brickSet.getNumber(), variant, subThemeKey, year, name, minifigs, pieces, priceUK,
                        priceUS, priceCA, priceEU, imageURL, key);
                  session.save(set);
               } catch (Exception ex) {
                  System.out.println(ex.toString());
               }
            }
         }
      }
      session.getTransaction().commit();

      String log = "";
      for (String content: logList) {
         log += content + "\r\n";
      }
      
      fileHelper.saveContent(log, "/Users/yemin/workspace/lego/initial.log");*/
   }
}
