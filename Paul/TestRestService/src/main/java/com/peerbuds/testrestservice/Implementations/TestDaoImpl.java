package com.peerbuds.testrestservice.Implementations;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.peerbuds.testrestservice.DAOs.TestDao;
import com.peerbuds.testrestservice.Models.Test;
import com.peerbuds.testrestservice.Models.TestList;
import com.peerbuds.testrestservice.Models.TestResult;
import com.peerbuds.testrestservice.Models.TestResultList;

public class TestDaoImpl implements TestDao{


	private SessionFactory sessionFactory;
	
	
	public List<Test> getTests() {
		
		Session session = sessionFactory.openSession();
		List<Test> tests = null;
		try{
			tests = session.createQuery("FROM Test").list();
		} catch(HibernateException e){
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		for(Test test: tests){
			test.setJSONObject();
		}
		
		return tests;
		/*
		jdbcTemplate = Database.getTemplate();
		List<Test> tests = new ArrayList<Test>();
		String query = "SELECT * FROM protoTests";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			Test test = new Test();
			test.setTestID(row.get("testID"));
			test.setUserID(row.get("userID"));
			test.setNumberOfMC(row.get("numberOfMC"));
			test.setNumberOfMAT(row.get("numberOfMAT"));
			test.setNumberOfFITB(row.get("numberOfFITB"));
			test.setNumberOfTF(row.get("numberOfTF"));
			test.setJSONObject();
			tests.add(test);
		}
	
		
		
		return tests;
		*/
		
		
	}
	
	public List<TestResult> getTestResults() {
		
		Session session = sessionFactory.openSession();
		List<TestResult> testResults = null;
		try{
			testResults = session.createQuery("FROM TestResult").list();
		} catch(HibernateException e){
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		for(TestResult testResult: testResults){
			testResult.setJSONObject();
		}
		
		return testResults;
		/*
		jdbcTemplate = Database.getTemplate();
		List<TestResult> testResults = new ArrayList<TestResult>();
		String query = "SELECT * FROM employeeTestResults";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			TestResult testResult = new TestResult();
			testResult.setTestID(row.get("testID"));
			testResult.setUserID(row.get("userID"));
			testResult.setNumberOfAttempts(row.get("numberOfAttempts"));
			testResult.setNumberOfCorrect(row.get("numberOfCorrect"));
			testResult.setNumberOfWrong(row.get("numberOfWrong"));
			testResult.setTimeStamp(row.get("timestamp"));
			testResult.setJSONObject();
			testResults.add(testResult);
		}
	
		
		
		return testResults;
		*/
		
		
	}
	
	public String getAllTests(){
		TestList list = new TestList(getTests());
		list.setJSONObject();
		return list.toString();
	}
	public String getAllTestResults(){
		TestResultList list = new TestResultList(getTestResults());
		list.setJSONObject();
		return list.toString();
	}
	public Test getTestByID(int testID) {
		List<Test> tests = getTests();
		for(Test test: tests){
			if(testID == test.getTestID()){
				return test;
			}
		}
		
		return null;
	}
	public List<TestResult> getTestResultsByUser(int userID) {
		List<TestResult> testResults = getTestResults();
		for(TestResult testResult: testResults){
			if(userID != testResult.getUserID()){
				testResults.remove(testResult);
			}
		}
		
		return testResults;
	}
	
	public List<TestResult> getTestResultsByUserAndTest(int userID, int testID) {
		List<TestResult> testResults = getTestResults();
		for(TestResult testResult: testResults){
			if(userID != testResult.getUserID() || testID != testResult.getTestID()){
				testResults.remove(testResult);
			}
		}
		
		return testResults;
	}
	
	public List<TestResult> getTestResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp) {
		List<TestResult> testResults = getTestResults();
		for(TestResult testResult: testResults){
			if(userID != testResult.getUserID() || testID != testResult.getTestID() || !timeStamp.equals(testResult.getTimestamp())){
				testResults.remove(testResult);
			}
		}
		
		return testResults;
	}
	
	public String returnTestByID(int testID) {
		List<Test> tests = getTests();
		for(Test test: tests){
			if(testID == test.getTestID()){
				return test.toString();
			}
		}
		
		return null;
	}
	public String returnTestResultsByUser(int userID) {
		TestResultList list = new TestResultList(getTestResultsByUser(userID));
		list.setJSONObject();
		return list.toString();
	}
	
	public String returnTestResultsByUserAndTest(int userID, int testID) {
		TestResultList list = new TestResultList(getTestResultsByUserAndTest(userID, testID));
		list.setJSONObject();
		return list.toString();
	}
	
	public String returnTestResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp) {
		TestResultList list = new TestResultList(getTestResultsByUserAndTestAndTimeStamp(userID, testID, timeStamp));
		list.setJSONObject();
		return list.toString();
	}
	public int addTest(Test test) {
		
		List<Test> tests = getTests();
		for(Test t: tests){
			if(t.getTestID() == test.getTestID()){
				return 0;
			}
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try{
			tx = session.beginTransaction();
			session.save(test);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		return 1;
		
		/*
		List<Test> tests = getTests();
		for(Test existingTest: tests){
			if(existingTest.getTestID() == test.getTestID() && existingTest.getUserID() == test.getUserID()){
				return 0;
			}
		}
		jdbcTemplate = Database.getTemplate();
		String query = "INSERT INTO protoTests (testID, userID, numberOfMC, numberOfFITB, numberOfMAT, numberOfTF)"
									     + " VALUES(?,?,?,?,?,?)";
		
				
		jdbcTemplate.execute(query,new PreparedStatementCallback(){  
			   @Override  
			   public Boolean doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {  
			              
			    	stmt.setInt(1, test.getTestID());
					stmt.setInt(2, test.getUserID());
					stmt.setInt(3, test.getNumberOfMC());
					stmt.setInt(4, test.getNumberOfFITB());
					stmt.setInt(5, test.getNumberOfMAT());
					stmt.setInt(6, test.getNumberOfTF());
			              
			        return stmt.execute();  
			              
			    }  
		});
		
		return 1;
		*/
			
		
		
	}
	
	public int updateTest(Test test) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(test);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		return 1;
		/*
		Test updateTest = getTestByID(test.getTestID());
		if(updateTest == null){
			return 0;
		}
		jdbcTemplate = Database.getTemplate();
		
		String query = "UPDATE protoTests SET numberOfMC = ?, numberOfFITB = ?, numberOfMAT = ?, numberOfTF = ?, userID = ?"
			+ " WHERE testID = ?";
		
		jdbcTemplate.execute(query, new PreparedStatementCallback(){

			@Override
			public Boolean doInPreparedStatement(PreparedStatement stmt)
					throws SQLException, DataAccessException {
				stmt.setInt(1, test.getNumberOfMC());
				stmt.setInt(2, test.getNumberOfFITB());
				stmt.setInt(3, test.getNumberOfMAT());
				stmt.setInt(4, test.getNumberOfTF());
				stmt.setInt(5, test.getUserID());
				stmt.setInt(6, test.getTestID());
				return stmt.execute();
			}
			
		});
		
		return 1;
		*/
	}
	
	
	public int deleteTest(int testID){
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Test test = getTestByID(testID);
		if(test == null){
			return 0;
		}
		try{
			tx = session.beginTransaction();
			session.delete(test);
			tx.commit();
		} catch(HibernateException e){
			if(tx != null){
				tx.rollback();
			}
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		return 1;
		/*
		List<Test> tests = getTests();
		for(Test test: tests){
			if(test.getTestID() == testID){
				break;
			}
			return 0;
		}
		if(tests.size() == 0){
			return 0;
		}
		String query = "DELETE FROM protoTests WHERE testID = ?";
		jdbcTemplate = Database.getTemplate();
		
		jdbcTemplate.execute(query, new PreparedStatementCallback(){

			@Override
			public Boolean doInPreparedStatement(PreparedStatement stmt)
					throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				stmt.setInt(1, testID);
				return stmt.execute();
			}
			
		});
		
		return 1;
		*/
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
