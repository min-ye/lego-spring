package com.lia.lego.data;

import java.util.UUID;
import java.util.List;

import com.lia.common.spring.CommonObject;

public interface SetRepository {

   public void delete(CommonObject obj);
   public void create(CommonObject obj);
   public void update(CommonObject obj);

   public CommonObject retrieveAccordingKey(UUID key);
   public List<CommonObject> retrieve();

   public void initialize() throws Exception;

}
