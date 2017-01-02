package com.lia.lego.bee;

import java.util.Map;

public interface Raw {
	public void downloadSet(String url, String folder, Integer begin, Integer end) throws Exception;
	public Map<String, String> getSetIdMap(String folder) throws Exception;
	public void downloadInventory(String url, String folder, String setId, String setNumber) throws Exception;
}