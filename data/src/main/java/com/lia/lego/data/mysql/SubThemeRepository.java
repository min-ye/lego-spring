package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonObject;
import com.lia.common.spring.HibernateHelper;
import com.lia.lego.model.SubTheme;
import com.lia.lego.model.Theme;

import com.lia.lego.data.SubThemeRepository;
import com.lia.lego.data.mysql.SubThemeRepositoryImp;

public class SubThemeRepositoryImp implements SubThemeRepository{

   private RawSetRepository rawSetRepository;

   public void setRawSetRepository(RawSetRepository rawSetRepository) {
      this.rawSetRepository = rawSetRepository;
   }

   public void delete(CommonObject obj) {
      SubTheme subTheme = (SubTheme) obj;
      HibernateHelper.currentSession().delete(subTheme);
   }

   public void create(CommonObject obj) {
      SubTheme subTheme = (SubTheme) obj;
      HibernateHelper.currentSession().save(subTheme);
   }

   public void update(CommonObject obj) {
      SubTheme subTheme = (SubTheme) obj;
      HibernateHelper.currentSession().update(subTheme);
   }

   public CommonObject retrieveAccordingKey(UUID key) {
      CommonObject output = null;
      String hql = "from com.lia.lego.SubTheme as s where s.Key=:key";
      Query query = HibernateHelper.currentSession().createQuery(hql);
      query.setString("key", key.toString());
            
      List<SubTheme> subThemeList = query.list();
      if (subThemeList.size() > 0){
         output = subThemeList.get(0);
      }
      return output;
   }

   public List<CommonObject> retrieve() {
      List<CommonObject> output = new ArrayList<CommonObject>();
      String hql = "from com.lia.lego.SubTheme";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<SubTheme> subThemeList = query.list();
      for (SubTheme subTheme : subThemeList) {
         output.add(subTheme);
      }
      return output;
   }
   
   public void initialize(){
      
      /*SubThemeRepository themeHandler = new SubThemeRepositoryImp();
      List<CommonObject> themeList = themeHandler.retrieve();
      Session session = HibernateHelper.currentSession();

      session.beginTransaction();
      String script = "delete from SubTheme";
      Query query = session.createQuery(script);
      query.executeUpdate();

      for (CommonObject themeObject : themeList) {
         Theme theme = (Theme) themeObject;
         List<String> subThemeNameList = setHandler.getSubThemeNameList(theme.getName());
         UUID themeKey = theme.getKey();
         for (String subThemeName : subThemeNameList) {
            UUID key = UUID.randomUUID();
            SubTheme subTheme = new SubTheme(subThemeName, themeKey, key);
            create(subTheme);
         }
      }
      session.getTransaction().commit();*/

   }
   
   public List<CommonObject> getSubThemeAccordingThemeKey(UUID themeKey){
      List<CommonObject> subThemeList = new ArrayList<CommonObject>();

      Session session = HibernateHelper.currentSession();

      String hql = "from SubTheme where ThemeKey = '" + themeKey.toString() + "'";
      Query query = session.createQuery(hql);

      subThemeList = query.list();

      return subThemeList;
   }
}
