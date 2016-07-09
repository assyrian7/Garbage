package com.peerbuds.employeerestservice.ETC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Randomizer {
	
	public static String[] shuffle(String[] entries){
		List<String> listEntries = new ArrayList<String>();
		for(int i = 0; i < entries.length; i++){
			listEntries.add(entries[i]);
		}
		Collections.shuffle(listEntries);
		String[] resultArray = new String[entries.length];
		for(int i = 0; i < entries.length; i++){
			resultArray[i] = listEntries.get(i);
		}
		
		return resultArray;
		
	}

}
