package com.lia.lego.bee;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import com.lia.common.spring.FileHelper;
import com.lia.common.spring.Cookie;
import com.lia.common.spring.WebHelper;
import com.lia.common.spring.IOHelper;

public class BrickSetRawImp implements Raw {
	private WebHelper webHelper;
   private FileHelper fileHelper;
   private IOHelper iOHelper;

   public void setWebHelper(WebHelper webHelper) {
      this.webHelper = webHelper;
   }

   public void setFileHelper(FileHelper fileHelper) {
      this.fileHelper = fileHelper;
   }

   public void setIOHelper(IOHelper iOHelper) {
      this.iOHelper = iOHelper;
   }

	public void downloadSet(String url, String folder, Integer begin, Integer end) throws Exception{
		try {
         for (Integer year = begin; year <= end; year++) {
            Integer index = 1;
            while (index != 0) {
               String setUrl = String.format(url, year, index);
               ArrayList<Cookie> multi_cookie = new ArrayList<Cookie>();
               multi_cookie.add(new Cookie("setsListFormat", "CSV", "/", "brickset.com"));
               String content = webHelper.getContent(setUrl, multi_cookie);
               String beginTag = "<textarea name=\"ctl00$mainContent$list1$ctl01\">";
               String endTag = "</textarea>";
               content = fileHelper.getContentAccordingTag(content, beginTag, endTag);
               if (content.length() > 125) {
                  String fileName = folder + String.valueOf(year) + "-" + String.valueOf(index) + ".csv";
                  fileHelper.saveContent(content, fileName);
                  System.out.println(fileName);
                  index++;
               } else {
                  index = 0;
               }
            }
         }
      }
      catch (Exception ex) {
         System.out.println(ex.getMessage());
      }
      finally {

      }
	}

	public Map<String, String> getSetIdMap(String folder) throws Exception{
		File root = new File(folder);
      File[] multiFile = root.listFiles();
      Map<String, String> setIdMap = new HashMap<String, String>();
      for (File file : multiFile) {
         if (file.isFile() && file.toString().contains(".csv")) {
         	String fileName = file.toString();
         	FileReader fileReader = new FileReader(fileName);
         	BufferedReader bufferReader = new BufferedReader(fileReader);
         	String line = "";
         	while ((line = bufferReader.readLine()) != null) {
         		String[] arrayItem = line.split(",");
         		if (!arrayItem[0].toLowerCase().equals("setid")) {
         			line = line.replace("&quot;", "\"");
         			line += ",";
         			List<String> itemList = getItem(line);
         			if (itemList.size() != 14) {
         				throw new Exception(String.format("Invalid row[%s];", line));
         			}
         			else {
         				setIdMap.put(itemList.get(0), itemList.get(1) + "-" + itemList.get(2));
         			}
         		}
         	}
         	bufferReader.close();
         	fileReader.close();
         }
      }
      return setIdMap;
	}

	public void downloadInventory(String url, String folder, String setId, String setNumber) throws Exception{
		if (!folder.endsWith("/")) {
			folder += "/";
		}
		File path = new File(folder);
      Boolean validPath = false;
		if (!path.isDirectory())
      {
         try {
            path.mkdir();
         }
         catch (Exception e) {
            iOHelper.writeLine("Invalid path.");
            return;
         }
      }
		String fileName = folder + setId + ".csv";
      File file = new File(fileName);
      if (!file.exists()) {
         String inventoryUrl = String.format(url, setNumber.trim());
         String content = webHelper.getContent(inventoryUrl);

         fileHelper.saveContent(content, fileName);
         System.out.println(fileName);
      }
	}

	private List<String> getItem(String input) {
      Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
      Matcher mCells = pCells.matcher(input);
      ArrayList<String> output = new ArrayList<String>();
      while (mCells.find()) {
         String item = mCells.group();
         item = item.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
         item = item.replaceAll("(?sm)(\"(\"))", "$2");
         output.add(item);
      }
      return output;
   }
}