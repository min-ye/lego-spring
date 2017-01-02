package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonObject;
import com.lia.common.spring.HibernateHelper;
import com.lia.lego.model.Category;

import com.lia.lego.data.CategoryRepository;

public class CategoryRepositoryImp implements CategoryRepository{

   public void delete(CommonObject obj) {
      Category category = (Category) obj;
      HibernateHelper.currentSession().delete(category);
   }

   public void create(CommonObject obj) {
      Category category = (Category) obj;
      HibernateHelper.currentSession().save(category);
   }

   public void update(CommonObject obj) {
      Category category = (Category) obj;
      HibernateHelper.currentSession().update(category);
   }

   public CommonObject retrieveAccordingKey(UUID key) {
      CommonObject output = null;
      String hql = "from com.lia.lego.model.Category as c where c.Key=:key";
      Query query = HibernateHelper.currentSession().createQuery(hql);
      query.setString("key", key.toString());
            
      List<Category> categoryList = query.list();
      if (categoryList.size() > 0){
         output = categoryList.get(0);
      }
      return output;
   }

   public List<CommonObject> retrieve() {
      List<CommonObject> output = new ArrayList<CommonObject>();
      String hql = "from com.lia.lego.model.Category";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<Category> categoryList = query.list();
      for (Category category : categoryList) {
         output.add(category);
      }
      return output;
   }

   public void initialize(List<String> categoryNameList){

      //com.lia.lego.business.brickset.InventoryHandler inventoryHandler = new com.lia.lego.business.brickset.InventoryHandler();
      //List<String> categoryNameList = inventoryHandler.getCategoryNameList();
      /*Session session = HibernateHelper.currentSession();
      session.beginTransaction();

      if (categoryNameList.size() > 0) {
         String script = "delete from Category";
         Query query = session.createQuery(script);
         query.executeUpdate();

         for (String categoryName : categoryNameList) {
            UUID key = UUID.randomUUID();
            Category category = new Category(categoryName, key);
            session.save(category);
         }
      }
      session.getTransaction().commit();*/
   }
   
   public CommonObject retrieveAccordingName(String name) throws Exception {
      CommonObject output = null;
      
      Session session = HibernateHelper.currentSession();
      String hql = "from com.lia.lego.model.Category as c where c.Name=:name";
      Query query = session.createQuery(hql);
      query.setString("name", name);

      List<CommonObject> colorList = query.list();
      if (colorList.size() > 1) {
         throw new Exception("Duplicated Color Name:" + name);
      }
      if (colorList.size() > 0) {
         output = colorList.get(0);
      }

      return output;
   }
}
