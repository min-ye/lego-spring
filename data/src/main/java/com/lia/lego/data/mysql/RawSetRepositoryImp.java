package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonObject;
import com.lia.common.spring.FileHelper;
import com.lia.common.spring.Cookie;
import com.lia.common.spring.WebHelper;
import com.lia.common.spring.HibernateHelper;
//import com.lia.lego.business.Handler;
import com.lia.lego.model.RawSet;

import com.lia.lego.data.RawSetRepository;

public class RawSetRepositoryImp implements RawSetRepository{
   
   private WebHelper webHelper;
   private FileHelper fileHelper;

   public void setWebHelper(WebHelper webHelper) {
      this.webHelper = webHelper;
   }

   public void setFileHelper(FileHelper fileHelper) {
      this.fileHelper = fileHelper;
   }

   public void delete(CommonObject obj) {
      RawSet set = (RawSet) obj;
      HibernateHelper.currentSession().delete(set);
   }

   public void create(CommonObject obj) {
      RawSet set = (RawSet) obj;
      HibernateHelper.currentSession().save(set);
   }

   public void update(CommonObject obj) {
      RawSet set = (RawSet) obj;
      HibernateHelper.currentSession().update(set);
   }

   public CommonObject retrieveAccordingKey(UUID key) {
      CommonObject output = null;
      String hql = "from com.lia.lego.brickset.Set as s where s.Key=:key";
      Query query = HibernateHelper.currentSession().createQuery(hql);
      query.setString("key", key.toString());
            
      List<RawSet> setList = query.list();
      if (setList.size() > 0){
         output = setList.get(0);
      }
      return output;
   }

   public List<CommonObject> retrieve() {
      List<CommonObject> output = new ArrayList<CommonObject>();
      String hql = "from com.lia.lego.brickset.Set";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<RawSet> setList = query.list();
      for (RawSet set : setList) {
         output.add(set);
      }
      return output;
   }

   public List<String> getThemeNameList() {
      List<String> themeList = new ArrayList<String>();
      List setList = HibernateHelper.currentSession().createQuery("select distinct s.Theme FROM com.lia.lego.brickset.model.Set s").list();
      for (Iterator iterator = setList.iterator(); iterator.hasNext();){
         String theme = (String) iterator.next();
         if (!themeList.contains(theme)) {
            themeList.add(theme);
         }
      }
      return themeList;
   }
   
   public List<String> getSubThemeNameList(String themeName){
      List<String> subThemeList = new ArrayList<String>();
      List setList = HibernateHelper.currentSession().createQuery("select distinct s.SubTheme FROM com.lia.lego.brickset.model.Set s where s.Theme = '" + themeName + "'").list();
      for (Iterator iterator = setList.iterator(); iterator.hasNext();){
         String theme = (String) iterator.next();
         if (!subThemeList.contains(theme)) {
            subThemeList.add(theme);
         }
      }
      return subThemeList;
   }

   public List<CommonObject> getSetAccordingTheme(String themeName, String subThemeName) {
      List<CommonObject> output = new ArrayList<CommonObject>();
      List setList = HibernateHelper.currentSession().createQuery("FROM com.lia.lego.brickset.model.Set s where s.Theme = '" + themeName + "' and s.SubTheme = '" + subThemeName + "'").list();
      for (Iterator iterator = setList.iterator(); iterator.hasNext();) {
         RawSet set = (RawSet) iterator.next();
         output.add(set);
      }
      return output;
   }
}
