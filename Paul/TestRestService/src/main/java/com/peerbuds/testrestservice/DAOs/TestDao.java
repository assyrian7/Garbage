package com.peerbuds.testrestservice.DAOs;

import java.util.List;

import com.peerbuds.testrestservice.Models.Test;
import com.peerbuds.testrestservice.Models.TestResult;


public interface TestDao {
		
	public List<Test> getTests();
	public List<TestResult> getTestResults();
	public String getAllTests();
	public String getAllTestResults();
	public Test getTestByID(int testID);
	public List<TestResult> getTestResultsByUser(int userID);
	public List<TestResult> getTestResultsByUserAndTest(int userID, int testID);
	public List<TestResult> getTestResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp);
	public String returnTestByID(int testID);
	public String returnTestResultsByUser(int userID);
	public String returnTestResultsByUserAndTest(int userID, int testID);
	public String returnTestResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp);
	public int addTest(Test test);
	public int updateTest(Test test);
	public int deleteTest(int testID);
	
	
}
