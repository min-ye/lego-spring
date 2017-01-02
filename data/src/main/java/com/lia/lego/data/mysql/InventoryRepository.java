package com.lia.lego.data.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.lia.common.spring.CommonHelper;
import com.lia.common.spring.CommonObject;
import com.lia.common.spring.HibernateHelper;
import com.lia.common.spring.IOHelper;
import com.lia.lego.model.Brick;
import com.lia.lego.model.Category;
import com.lia.lego.model.Color;
import com.lia.lego.model.Inventory;
import com.lia.lego.model.Set;

import com.lia.lego.data.InventoryRepository;

public class InventoryRepositoryImp implements InventoryRepository{

   private IOHelper ioHelper;
   private CommonHelper commonHelper;
   //private RawInventoryRepository rawInventoryRepository;

   public void setIOHelper(IOHelper ioHelper) {
      this.ioHelper = ioHelper;
   }

   public void setCommonHelper(CommonHelper commonHelper) {
      this.commonHelper = commonHelper;
   }

   //public void setRawInventoryRepository(RawInventoryRepository rawInventoryRepository) {
   //   this.rawInventoryRepository = rawInventoryRepository;
   //}

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
      String hql = "from com.lia.lego.Inventory as i where i.Key=:key";
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
      String hql = "from com.lia.lego.Inventory";
      Query query = HibernateHelper.currentSession().createQuery(hql);

      List<Inventory> inventoryList = query.list();
      for (Inventory inventory : inventoryList) {
         output.add(inventory);
      }
      return output;
   }
   
   public void initialize(List<String[]> inventoryList) throws Exception {
      /*Integer count = 0;
      List<String> logList = new ArrayList<String>();
      int index = 0;
      
      Session session = HibernateHelper.currentSession();
      session.beginTransaction();
      String script = "delete from com.lia.lego.model.Inventory";
      Query query = session.createQuery(script);
      query.executeUpdate();
      //List<String[]> inventoryList = rawInventoryRepository.getInventoryMapList();
      
      for (String[] inventoryObject : inventoryList) {
         ioHelper.writeLine(String.format("%d", index));
         index ++;
         Integer quantity = commonHelper.convertToInteger(inventoryObject[2].toString(), 0);
         UUID setKey = commonHelper.convertToUUID(inventoryObject[3].toString(),  null);
         UUID brickKey = commonHelper.convertToUUID(inventoryObject[4].toString(),  null);
            
         if (setKey == null) {
            throw new Exception("Unknown set key[" + inventoryObject[0] + "]");
         }
         if (brickKey == null) {
            throw new Exception("Unknown brick key[" + inventoryObject[1] + "]");
         }
         
         Inventory inventory = new Inventory(setKey, brickKey, quantity);
         session.save(inventory);
      }
      
      session.getTransaction().commit();*/
   }
}
