package com.lia.lego.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.lia.common.spring.CommonObject;
import com.lia.common.spring.mysql.FieldModel;

public class Inventory extends CommonObject implements Serializable{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private UUID _setKey = null;
   private UUID _brickKey = null;
   private Integer _quantity = 0;

   
   public Inventory() { }

   public Inventory(UUID setKey,
                    UUID brickKey,
                    Integer quantity){
      this._setKey = setKey;
      this._brickKey = brickKey;
      this._quantity = quantity;
   }
   
   public Inventory(CommonObject commonObject){
      Object[] object = commonObject.fetchObject();
      this._setKey = convertToUUID(object[0]);
      this._brickKey = convertToUUID(object[1]);
      this._quantity = convertToInteger(object[2]);
   }
   
   public UUID getSetKey() {
      return this._setKey;
   }

   public void setSetKey(UUID setKey) {
      this._setKey = setKey;
   }

   public UUID getBrickKey() {
      return this._brickKey;
   }

   public void setBrickKey(UUID brickKey) {
      this._brickKey = brickKey;
   }

   public Integer getQuantity() {
      return this._quantity;
   }

   public void setQuantity(Integer quantity) {
      this._quantity = quantity;
   }


   
   @Override
   public String fetchObjectName(){
      return "Inventory";
   }
   
   @Override
   public String getPropertyValue(String fieldName) throws Exception{
      switch (fieldName){
      case "SetKey":
         return this._setKey.toString();
      case "BrickKey":
         return this._brickKey.toString();
      case "Quantity":
         return this._quantity.toString();

      default:
         throw new Exception(String.format("Unknown Field Name:[%s]", fieldName));
      }
   }
   
   @Override
   public void setValue(String fieldName, String fieldValue) throws Exception
   {
      switch (fieldName) {
      case "SetKey":
         this._setKey = convertToUUID(fieldValue);
         break;
      case "BrickKey":
         this._brickKey = convertToUUID(fieldValue);
         break;
      case "Quantity":
         this._quantity = convertToInteger(fieldValue);
         break;

      default:
         throw new Exception(String.format("Unknown Field Name:[%s]", fieldName));
      }
   }
   
   @Override
   public void importModel(Map<String, Object> item) throws Exception{
      for (Entry<String, Object> entry : item.entrySet()) {
         setValue(entry.getKey(), entry.getValue().toString());
      }
   }
   
   public Map<String, FieldModel> exportModel(){
      Map<String, FieldModel> modelMap = new HashMap<String, FieldModel>();
      modelMap.put("SetKey", new FieldModel("UUID", this._setKey.toString(), true));
      modelMap.put("BrickKey", new FieldModel("UUID", this._brickKey.toString(), true));
      modelMap.put("Quantity", new FieldModel("Integer", this._quantity.toString(), false));

      return modelMap;
   }
   
   @Override
   public Map<String, String> exportPropertyMap(){
      Map<String, String> modelMap = new HashMap<String, String>();
      modelMap.put("SetKey", getPropertyValueString(this._setKey));
      modelMap.put("BrickKey", getPropertyValueString(this._brickKey));
      modelMap.put("Quantity", getPropertyValueString(this._quantity));

      return modelMap;
   }
   
   @Override
   public Map<String, String> exportKeyPropertyMap(){
      Map<String, String> modelMap = new HashMap<String, String>();
      modelMap.put("SetKey", getPropertyValueString(this._setKey));
      modelMap.put("BrickKey", getPropertyValueString(this._brickKey));

      return modelMap;
   }
   
   @Override
   public Map<String, String> exportValuePropertyMap(){
      Map<String, String> modelMap = new HashMap<String, String>();
      modelMap.put("Quantity", getPropertyValueString(this._quantity));

      return modelMap;
   }
   
   @Override
   public ArrayList<String> fetchPropertyName(){
      ArrayList<String> fieldNameList = new ArrayList<String>();
      fieldNameList.add("SetKey");
      fieldNameList.add("BrickKey");
      fieldNameList.add("Quantity");

      return fieldNameList;
   }
   
   @Override
   public Object[] fetchObject() {
      Object[] obj = new Object[3];
      obj[0] = this._setKey;
      obj[1] = this._brickKey;
      obj[2] = this._quantity;

      return obj;
   }

   @Override
   public String fetchDescription() {
      return String.format("%s(%s)", this._setKey.toString(), this._brickKey.toString());
   }
   
   @Override
   public int hashCode()
   {
       final int prime = 31;
       int result = 1;
       result = prime * result + ((_setKey == null) ? 0 : _setKey.hashCode());
       result = prime * result + ((_brickKey == null) ? 0 : _brickKey.hashCode());
       return result;
   }

   @Override
   public boolean equals(Object obj)
   {
       if (this == obj)
           return true;
       if (obj == null)
           return false;
       if (getClass() != obj.getClass())
           return false;
       Inventory other = (Inventory) obj;
       if (_setKey == null)
       {
           if (other._setKey != null)
               return false;
       }
       else if (!_setKey.equals(other._setKey))
           return false;
       if (_brickKey == null)
       {
           if (other._brickKey != null)
               return false;
       }
       else if (!_brickKey.equals(other._brickKey))
           return false;
       return true;
   }
}
