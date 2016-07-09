package com.peerbuds.employeerestservice.Implementations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

import com.peerbuds.employeerestservice.DAOs.QuestionDao;
import com.peerbuds.employeerestservice.Models.Question;
import com.peerbuds.employeerestservice.Models.QuestionList;
import com.peerbuds.employeerestservice.Models.QuestionResult;

public class QuestionDaoImpl implements QuestionDao{

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public List<Question> getQuestions(){
		
		Session session = sessionFactory.openSession();
		List<Question> questions = null;
		try{
			questions = session.createQuery("FROM Question").list();
		} catch(HibernateException e){
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		for(Question question: questions){
			question.setJSONObject();
		}
		
		return questions;
		
		/*
		List<Question> questions = new ArrayList<Question>();
		jdbcTemplate = Database.getTemplate();
		String query = "SELECT * FROM protoQuestions";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			Question question = new Question();
			question.setTestID(row.get("testID"));
			question.setQuestionID(row.get("questionID"));
			question.setQuestion(row.get("question"));
			question.setQuestionType(row.get("questionType"));
			question.setCorrectAnswer(row.get("correctAnswer"));
			question.setPossibleAnswers(row.get("possibleAnswers"));
			question.setJSONObject();
			questions.add(question);
		}
		return questions;
		*/
	}
	public String getQuestionsByTest(int testID){
		List<Question> questions = getQuestions();
		for(Question question: questions){
			if(testID != question.getTestID()){
				questions.remove(question);
			}
		}
		QuestionList list = new QuestionList(questions);
		list.setJSONObject();
		return list.toString();
	}
	public String getAllQuestions(){
		QuestionList list = new QuestionList(getQuestions());
		list.setJSONObject();
		return list.toString();
	}
	public Question getQuestionByID(int testID, int questionID){
		List<Question> questions = getQuestions();
		for(Question question: questions){
			if(testID == question.getTestID() && questionID == question.getQuestionID()){
				return question;
			}
		}
		return null;
	}
	public String returnQuestionByID(int testID, int questionID){
		Question question = getQuestionByID(testID, questionID);
		if(question != null){
			return question.toString();
		}
		else{
			return null;
		}
	}
	public int updateQuestionResults(QuestionResult questionResult){
		boolean exists = false;
		String data = getURLText("http://localhost:8080/TestRestService/rest/QuestionService/questionresults/" + questionResult.getUserID() + "/" + questionResult.getTestID());
		JSONObject mainObj = null;
		JSONArray mainArray = null;
		JSONObject question = null;
		String timeStamp = new SimpleDateFormat("MM-dd-YYYY").format(Calendar.getInstance().getTime());
		try{
			mainObj = new JSONObject(data);
			mainArray = mainObj.getJSONArray("questions");
			for(int i = 0; i < mainArray.length(); i++){
				question = mainArray.getJSONObject(i).getJSONObject("question");
				if(questionResult.getUserID() == question.getInt("userID") && questionResult.getTestID() == question.getInt("testID") && questionResult.getQuestionID() == question.getInt("questionID") && question.getString("timestamp").equals(timeStamp)){
					exists = true;
				}
			}
		} catch(JSONException e){
			e.printStackTrace();
		}
		if(exists){
			
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				session.update(questionResult);
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
			String query = "UPDATE employeeQuestionResults SET possible = ?, answer = ?, numberOfAttempts = ?"
					+ " WHERE userID = ? AND testID = ? AND questionID = ? AND timestamp = ?";
			jdbcTemplate.execute(query, new PreparedStatementCallback(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement stmt)
						throws SQLException, DataAccessException {
					stmt.setString(1, String.join(DIV.DELIMITER, questionResult.getPossibleAnswers()));
					stmt.setString(2, String.join(DIV.DELIMITER, questionResult.getAnswer()));
					stmt.setInt(3, tries);
					stmt.setInt(4, questionResult.getUserID());
					stmt.setInt(5, questionResult.getTestID());
					stmt.setInt(6, questionResult.getQuestionID());
					stmt.setString(7, timeStamp);
					return stmt.execute();
				}
				
			});
			
			return 1;
			*/
			
		}
		else{
			
			questionResult.setTimestamp(timeStamp);
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			
			
			try{
				tx = session.beginTransaction();
				session.save(questionResult);
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
			String query = "INSERT INTO employeeQuestionResults (userID, testID, questionID, answer, possible, numberOfAttempts, timestamp)\n" 
						 + "VALUES(?,?,?,?,?,?,?);";
			jdbcTemplate.execute(query, new PreparedStatementCallback(){

				@Override
				public Boolean doInPreparedStatement(PreparedStatement stmt)
						throws SQLException, DataAccessException {
					stmt.setInt(1, questionResult.getUserID());
					stmt.setInt(2, questionResult.getTestID());
					stmt.setInt(3, questionResult.getQuestionID());
					stmt.setString(4, String.join(DIV.DELIMITER, questionResult.getAnswer()));
					stmt.setString(5, String.join(DIV.DELIMITER, questionResult.getPossibleAnswers()));
					stmt.setInt(6, 1);
					stmt.setString(7, timeStamp);
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
	
	
}
