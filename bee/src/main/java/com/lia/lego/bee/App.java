package com.lia.lego.bee;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;

import com.lia.common.spring.exception.CancelInputException;
import com.lia.common.spring.IOHelper;
import com.lia.common.spring.Profile;
import com.lia.common.spring.FileHelper;

public class App 
{
   public static void main( String[] args )
   {
      ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
      FileHelper fileHelper = (FileHelper) context.getBean("fileHelper");
      IOHelper ioHelper = (IOHelper) context.getBean("iOHelper");
      Profile profile = (Profile) context.getBean("profile");
      Raw raw = (BrickSetRawImp) context.getBean("raw");
      String jarPath = getJarDir();
      String configFile = String.format("%s/bee.config", jarPath);
      try{
         int choice = -1;
         Map<Integer, String> option = new HashMap<Integer, String>();
         option.put(1, "Set output folder");
         option.put(2, "Get set raw data from brickset.com");
         option.put(3, "Get brick raw data from brickset.com");
         option.put(5, "Convert set data from csv format to json format");
         option.put(6, "Convert set data from json format to database");
         option.put(7, "Convert brick data from csv format to json format");
         option.put(8, "Convert inventory data from json format to database");
         option.put(9, "Check set");

         do {
            try {
               choice = ioHelper.readStringChoose(option);
               System.out.println(String.format("%d", choice));
            }
            catch (CancelInputException ex) {
               choice = 0;
            }
            switch(choice){
            case 1:
               String outputPath = ioHelper.readLine("Please input the ouput folder");
               File path = new File(outputPath);
               Boolean validPath = false;
               if (!path.isDirectory())
               {
                  try {
                     path.mkdir();
                  }
                  catch (Exception e) {
                     ioHelper.writeLine("Invalid path.");
                     break;
                  }
               }
               
               profile.writeProperty(configFile, "RawSetFolder", outputPath);
               break;
            case 2:
               {
                  String url = profile.readProperty(configFile, "SourceSetUrl");
                  String folder = profile.readProperty(configFile, "RawSetFolder");
                  ioHelper.writeLine(url);
                  ioHelper.writeLine(folder);
                  raw.downloadSet(url, folder, 1949, 2016);
               }
               break;
            case 3:
               try {
                  String url = profile.readProperty(configFile, "SourceInventoryUrl");
                  String setFolder = profile.readProperty(configFile, "RawSetFolder");
                  String inventoryFolder = setFolder + profile.readProperty(configFile, "RawInventoryFolder");
                  ioHelper.writeLine(url);
                  ioHelper.writeLine(inventoryFolder);
                  Map<String, String> setIdMap = raw.getSetIdMap(setFolder);
                  int index = 1;
                  for (Map.Entry<String, String> obj : setIdMap.entrySet()) {
                     raw.downloadInventory(url, inventoryFolder, obj.getKey(), obj.getValue());
                     System.out.println(String.valueOf(index));
                     index++;
                  }
                  
               }
               catch (Exception ex) {
                  ioHelper.writeLine(ex.getMessage());
               }
               break;
            case 5:
               //db.convertSetFromJsonToMySQL();
               break;
            case 6:
               //raw.downloadInventoryRawToCSV();
               break;
            case 7:
               //json.convertInventoryFromRawToJson();
               break;
            case 8:
               //db.convertInventoryFromJsonToMySQL();
               break;
            }
         } while (choice != 0);
      }
      catch (CancelInputException ex){
         System.exit(0);
      }
      catch (Exception ex){
         ioHelper.writeLine(ex.getMessage());
      }
   }

   public static String getJarPath()
   {
      File file = getFile();
      if (file == null)
         return null;
      return file.getAbsolutePath();
   }

   public static String getJarDir()
   {
      File file = getFile();
      if (file == null)
         return null;
      return getFile().getParent();
   }

   public static String getJarName()
   {
      File file = getFile();
      if (file == null)
         return null;
      return getFile().getName();
   }

   private static File getFile()
   {
      String path = App.class.getProtectionDomain().getCodeSource().getLocation().getFile();
      try {
         path = java.net.URLDecoder.decode(path, "UTF-8");
      }
      catch (java.io.UnsupportedEncodingException e)
      {
         return null;
      }
      return new File(path);
   }
}