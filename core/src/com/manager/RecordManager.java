package com.manager;

import java.util.Map;

public class RecordManager {
	
	private Map<String, String> map;
	boolean newRecord;
	boolean check;
	
	public RecordManager() {
		map = System.getenv();
		if(map.get("pandoras_HighUser") != null)
			check = true;
		else
			check = false;
		newRecord = false;
	}
	
	public String getHighUser() {
		if(check) {
			if(newRecord)
				return map.get("pandoras_ActualUser");
			else
				return map.get("pandoras_HighUser");
		}
		else
			return "YOU";
	}

	public String getPoints(String newScore) {
		if(check) {
			if(newRecord)
				return newScore;
			else
				return map.get("pandoras_HighScore");
		}
		else
			return newScore;
	}
	
	public void changeHighScore(Integer newPoints) {
		if(check) {
			String highScore = map.get("pandoras_HighScore");
			if(Integer.parseInt(highScore) < newPoints) {
				newRecord = true;
			}
		}
	}
	
	public void update(String newScore) {
		if(check)
			System.out.println(newScore);
	}
	
}
