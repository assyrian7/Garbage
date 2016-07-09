package com.peerbuds.employeerestservice.Implementations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.peerbuds.employeerestservice.DAOs.TestDao;
import com.peerbuds.employeerestservice.ETC.Database;
import com.peerbuds.employeerestservice.Models.Test;
import com.peerbuds.employeerestservice.Models.TestList;
import com.peerbuds.employeerestservice.Models.TestResult;

public class TestDaoImpl implements TestDao{

	private SessionFactory sessionFactory;
	
	public List<Test> getTests(){
		
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
		List<Test> tests = new ArrayList<Test>();
		jdbcTemplate = Database.getTemplate();
		String query = "SELECT * FROM protoTests";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			Test test = new Test();
			test.setTestID(row.get("testID"));
			test.setUserID(row.get("userID"));
			test.setNumberOfMC(row.get("numberOfMC"));
			test.setNumberOfFITB(row.get("numberOfFITB"));
			test.setNumberOfTF(row.get("numberOfTF"));
			test.setNumberOfMAT(row.get("numberOfMAT"));
			test.setJSONObject();
			tests.add(test);
		}
		return tests;
		*/
		
	}
	public String getAllTests(){
		TestList tests = new TestList(getTests());
		tests.setJSONObject();
		return tests.toString();
	}
	
	public Test getTestByID(int testID){
		List<Test> tests = getTests();
		for(Test test: tests){
			if(testID == test.getTestID()){
				return test;
			}
		}
		return null;
	}
	
	public String returnTestByID(int testID){
		
		Test test = getTestByID(testID);
		if(test != null){
			return test.toString();
		}
		return null;
		
	}
	public int updateTestResults(TestResult testResult){
		boolean exists = false;
		String data = getURLText("http://localhost:8080/TestRestService/rest/TestService/testresults/" + testResult.getUserID());
		JSONObject mainObj = null;
		JSONArray objArray = null;
		JSONObject test = null;
		String timeStamp = new SimpleDateFormat("MM-dd-YYYY").format(Calendar.getInstance().getTime());
		int numberOfCorrect = 0;
		int numberOfWrong = 0;
		String answerResult = getURLText("http://localhost:8080/TestRestService/rest/QuestionService/validate/" + testResult.getUserID() + "/" + testResult.getTestID() + "/" + timeStamp);
		JSONObject questionNumber = null;
		try{
			mainObj = new JSONObject(data);
			objArray = mainObj.getJSONArray("tests");
			questionNumber = new JSONObject(answerResult).getJSONObject("testResult");
			numberOfCorrect = questionNumber.getInt("numberOfCorrect");
			numberOfWrong = questionNumber.getInt("numberOfWrong");
			for(int i = 0; i < objArray.length(); i++){
				test = objArray.getJSONObject(i).getJSONObject("test");
				if(test.getInt("userID") == testResult.getUserID() && test.getInt("testID") == testResult.getTestID() && test.getString("timestamp").equals(timeStamp)){
					exists = true;
				}
			}
		} catch(JSONException e){
			e.printStackTrace();
		}
		testResult.setNumberOfCorrect(numberOfCorrect);
		testResult.setNumberOfWrong(numberOfWrong);
		if(exists){
			
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				session.update(testResult);
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
			jdbcTemplate = Database.getTemplate();
			String query = "UPDATE employeeTestResults SET numberOfCorrect = ?, numberOfWrong = ?, numberOfAttempts = ?"
					+ " WHERE userID = ? AND testID = ? AND timestamp = ?";
			jdbcTemplate.execute(query, new PreparedStatementCallback(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement stmt)
						throws SQLException, DataAccessException {
					stmt.setInt(1, testResult.getNumberOfCorrect());
					stmt.setInt(2, testResult.getNumberOfWrong());
					stmt.setInt(3, tries);
					stmt.setInt(4, testResult.getUserID());
					stmt.setInt(5, testResult.getTestID());
					stmt.setString(6, timeStamp);
					return stmt.execute();
				}
				
			});
			
			return 1;
			*/
		}
		else{
			testResult.setTimestamp(timeStamp);
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			
			
			try{
				tx = session.beginTransaction();
				session.save(testResult);
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
			jdbcTemplate = Database.getTemplate();
			String query = "INSERT INTO employeeTestResults (userID, testID, numberOfCorrect, numberOfWrong, numberOfAttempts, timestamp)\n" 
						 + "VALUES(?,?,?,?,?,?);";
			jdbcTemplate.execute(query, new PreparedStatementCallback(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement stmt)
						throws SQLException, DataAccessException {
					stmt.setInt(1, testResult.getUserID());
					stmt.setInt(2, testResult.getTestID());
					stmt.setInt(3, testResult.getNumberOfCorrect());
					stmt.setInt(4, testResult.getNumberOfWrong());
					stmt.setInt(5, 1);
					stmt.setString(6, timeStamp);
					return stmt.execute();
				}
				
			});
			
			return 1;
			*/
		}
	}
	public String getURLText(String url) {
		StringBuilder response = null;
		try{
	        URL website = new URL(url);
	        URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));
	
	        response = new StringBuilder();
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);
	
	        in.close();
		} catch(Exception e){
			
		}
        return response.toString();
    }
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
