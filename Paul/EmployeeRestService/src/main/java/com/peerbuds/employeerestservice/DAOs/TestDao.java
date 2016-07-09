package com.peerbuds.employeerestservice.DAOs;

import java.util.List;

import com.peerbuds.employeerestservice.Models.Test;
import com.peerbuds.employeerestservice.Models.TestResult;



public interface TestDao {
	
	public List<Test> getTests();
	public String getAllTests();
	public Test getTestByID(int testID);
	public String returnTestByID(int testID);
	public int updateTestResults(TestResult testResult);
	public String getURLText(String url);
}
