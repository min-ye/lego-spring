package com.lia.lego.data;

import java.util.UUID;
import java.util.List;

import com.lia.common.spring.CommonObject;

public interface ColorRepository{

   public void delete(CommonObject obj);
   public void create(CommonObject obj);
   public void update(CommonObject obj);

   public CommonObject retrieveAccordingKey(UUID key);
   public List<CommonObject> retrieve();
   public CommonObject retrieveAccordingName(String name) throws Exception;

   public void initialize(List<String> colorNameList);

}
