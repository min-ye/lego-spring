package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonObject;
import com.lia.common.spring.HibernateHelper;
import com.lia.lego.business.Handler;
import com.lia.lego.model.Inventory;

import com.lia.lego.data.RawInventoryRepository;

public class RawInventoryRepositoryImp implements RawInventoryRepository{

   public void delete(CommonObject obj) {
      Inventory inventory = (Inventory) obj;
      HibernateHelper.currentSession().delete(inventory);
   }

   public void create(CommonObject obj) {
      Inventory inventory = (Inventory) obj;
      HibernateHelper.currentSession().save(inventory);
   }

   public void update(CommonObject obj) {
      Inventory inventory = (Inventory) obj;
      HibernateHelper.currentSession().update(inventory);
   }

   public CommonObject retrieveAccordingKey(UUID key) {
      CommonObject output = null;
      String hql = "from com.lia.lego.brickset.Inventory as i where i.Key=:key";
      Query query = HibernateHelper.currentSession().createQuery(hql);
      query.setString("key", key.toString());
            
      List<Inventory> inventoryList = query.list();
      if (inventoryList.size() > 0){
         output = inventoryList.get(0);
      }
      return output;
   }

   public List<CommonObject> retrieve() {
      List<CommonObject> output = new ArrayList<CommonObject>();
      String hql = "from com.lia.lego.brickset.Inventory";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<Inventory> inventoryList = query.list();
      for (Inventory inventory : inventoryList) {
         output.add(inventory);
      }
      return output;
   }
   
   public List<String> getColorNameList() {
      List<String> output = new ArrayList<String>();
      
      List colorList = HibernateHelper.currentSession().createQuery("select distinct i.Colour FROM com.lia.lego.brickset.model.Inventory i").list();
      for (Iterator iterator = colorList.iterator(); iterator.hasNext();){
         String color = (String) iterator.next();
         if (!output.contains(color)) {
            output.add(color);
         }
      }
      return output;
   }
   
   public List<String> getCategoryNameList() {
      List<String> output = new ArrayList<String>();
      
      List categoryList = HibernateHelper.currentSession().createQuery("select distinct i.Category FROM com.lia.lego.brickset.model.Inventory i").list();
      for (Iterator iterator = categoryList.iterator(); iterator.hasNext();){
         String category = (String) iterator.next();
         if (!output.contains(category)) {
             output.add(category);
         }
      }
      return output;
   }
   
   public List<String[]> getBrickList() {
      List<String[]> output = new ArrayList<String[]>();
      List brickList = HibernateHelper.currentSession()
            .createQuery(
                  "select distinct i.PartID, i.DesignID, i.PartName, i.ImageURL, i.Colour, i.Category FROM com.lia.lego.brickset.model.Inventory i")
            .list();
      for (Iterator iterator = brickList.iterator(); iterator.hasNext();) {
         Object[] brick = (Object[]) iterator.next();
         String[] brickString = new String[6];
         for (Integer index = 0; index < 6; index++) {
            brickString[index] = brick[index].toString();
         }

         output.add(brickString);
      }

      return output;
   }
   
   public List<String[]> getInventoryList() {
      List<String[]> output = new ArrayList<String[]>();
      String query = "select i.SetNumber, i.PartID, s.Key as SetKey, b.Key as BrickKey, i.Quantity FROM com.lia.lego.brickset.model.Inventory i ";
      query += " left join com.lia.lego.model.Set s on (s.Number + '-' + s.Variant = i.SetNumber) ";
      query += " left join com.lia.lego.model.Brick b on (b.ElementID = i.PartID)";
      List brickList = HibernateHelper.currentSession()
            .createQuery(query)
            .list();
      for (Iterator iterator = brickList.iterator(); iterator.hasNext();) {
         Object[] inventory = (Object[]) iterator.next();
         String[] inventoryString = new String[5];
         for (Integer index = 0; index < 5; index++) {
            inventoryString[index] = inventory[index].toString();
         }

         output.add(inventoryString);
      }

      return output;
   }
   
   public List<String[]> getInventoryListAccordingSetNumber(String setNumber) {
      List<String[]> output = new ArrayList<String[]>();
      String query = "select i.SetNumber, i.PartID, s.Key as SetKey, b.Key as BrickKey, i.Quantity FROM com.lia.lego.brickset.model.Inventory i ";
      query += " left join com.lia.lego.model.Set s on (concat(s.Number, '-', s.Variant) = i.SetNumber) ";
      query += " left join com.lia.lego.model.Brick b on (b.ElementID = i.PartID) ";
      query += " where i.SetNumber = '" + setNumber + "'";
      List brickList = HibernateHelper.currentSession()
            .createQuery(query)
            .list();
      for (Iterator iterator = brickList.iterator(); iterator.hasNext();) {
         Object[] inventory = (Object[]) iterator.next();
         String[] inventoryString = new String[5];
         for (Integer index = 0; index < 5; index++) {
            inventoryString[index] = inventory[index].toString();
         }

         output.add(inventoryString);
      }

      return output;
   }
   
   public List<String[]> getInventoryMapList() {
      List<String[]> output = new ArrayList<String[]>();
      String query = "select i.SetNumber, i.ElementID, i.Quantity, i.SetKey, i.BrickKey FROM com.lia.lego.model.InventoryMap i ";
      List brickList = HibernateHelper.currentSession()
            .createQuery(query)
            .list();
      for (Iterator iterator = brickList.iterator(); iterator.hasNext();) {
         Object[] inventory = (Object[]) iterator.next();
         String[] inventoryString = new String[5];
         for (Integer index = 0; index < 5; index++) {
            inventoryString[index] = inventory[index].toString();
         }

         output.add(inventoryString);
      }

      return output;
   }

}
