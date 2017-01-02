package com.lia.lego.data;

import java.util.UUID;
import java.util.List;

import com.lia.common.spring.CommonObject;

public interface RawInventoryRepository {

   public void delete(CommonObject obj);
   public void create(CommonObject obj);
   public void update(CommonObject obj);

   public CommonObject retrieveAccordingKey(UUID key);
   public List<CommonObject> retrieve();
   public List<String> getColorNameList();
   public List<String> getCategoryNameList();
   public List<String[]> getBrickList();
   public List<String[]> getInventoryList();
   public List<String[]> getInventoryListAccordingSetNumber(String setNumber);
   public List<String[]> getInventoryMapList();

}
