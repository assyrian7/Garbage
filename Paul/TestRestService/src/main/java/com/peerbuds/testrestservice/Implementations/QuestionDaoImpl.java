package com.peerbuds.testrestservice.Implementations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;

import com.peerbuds.testrestservice.DAOs.QuestionDao;
import com.peerbuds.testrestservice.ETC.DIV;
import com.peerbuds.testrestservice.Models.Question;
import com.peerbuds.testrestservice.Models.QuestionList;
import com.peerbuds.testrestservice.Models.QuestionResult;
import com.peerbuds.testrestservice.Models.QuestionResultList;

public class QuestionDaoImpl implements QuestionDao{

	private SessionFactory sessionFactory;
	
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Question> getQuestions() {
		
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
		jdbcTemplate = Database.getTemplate();
		List<Question> questions = new ArrayList<Question>();
		String query = "SELECT * FROM protoQuestions";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			Question question = new Question();
			question.setTestID(row.get("testID"));
			question.setQuestionID(row.get("questionID"));
			question.setQuestion(row.get("question"));
			question.setQuestionType(row.get("questionType"));
			question.setPossibleAnswers(row.get("possibleAnswers"));
			question.setCorrectAnswer(row.get("correctAnswer"));
			question.setMatComparison();
			question.setJSONObject();
			questions.add(question);
		}
	
		
		
		return questions;
		*/
		
		
	}
	@Override
	public List<QuestionResult> getQuestionResults(){
		
		Session session = sessionFactory.openSession();
		List<QuestionResult> questionResults = null;
		try{
			questionResults = session.createQuery("FROM QuestionResult").list();
		} catch(HibernateException e){
			e.printStackTrace();
		} finally{
			session.flush();
			session.close();
		}
		for(QuestionResult questionResult: questionResults){
			questionResult.setJSONObject();
		}
		
		return questionResults;
		/*
		jdbcTemplate = Database.getTemplate();
		List<QuestionResult> questionResults = new ArrayList<QuestionResult>();
		String query = "SELECT * FROM employeeQuestionResults";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
		for(Map<String, Object> row: results){
			QuestionResult questionResult = new QuestionResult();
			questionResult.setUserID(row.get("userID"));
			questionResult.setQuestionID(row.get("questionID"));
			questionResult.setTestID(row.get("testID"));
			questionResult.setAnswer(row.get("answer"));
			questionResult.setNumberOfAttempts(row.get("numberOfAttempts"));
			questionResult.setPossibleAnswers(row.get("possible"));
			questionResult.setTimeStamp(row.get("timestamp"));
			questionResult.setJSONObject();
			questionResults.add(questionResult);
		}
		
		return questionResults;
		*/
	}
	@Override
	public String getAllQuestions(){
		QuestionList list = new QuestionList(getQuestions());
		list.setJSONObject();
		return list.toString();
	}
	@Override
	public String getAllQuestionResults(){
		QuestionResultList list = new QuestionResultList(getQuestionResults());
		list.setJSONObject();
		return list.toString();
	}
	@Override
	public List<Question> getQuestionsByTest(int testID) {
		List<Question> questions = getQuestions();
		for(Question question: questions){
			if(testID != question.getTestID()){
				questions.remove(question);
			}
		}
		return questions;
	}
	@Override
	public String returnQuestionsByTest(int testID){
		QuestionList list = new QuestionList(getQuestionsByTest(testID));
		list.setJSONObject();
		return list.toString();

	}
	@Override
	public List<QuestionResult> getQuestionResultsByUser(int userID) {
		List<QuestionResult> questionResults = getQuestionResults();
		for(QuestionResult questionResult: questionResults){
			if(userID != questionResult.getTestID()){
				questionResults.remove(questionResult);
			}
		}
		return questionResults;
	}
	@Override
	public String returnQuestionResultsByUser(int userID){
		QuestionResultList list = new QuestionResultList(getQuestionResultsByUser(userID));
		list.setJSONObject();
		return list.toString();
	}
	@Override
	public List<QuestionResult> getQuestionResultsByUserAndTest(int userID, int testID) {
		List<QuestionResult> questionResults = getQuestionResults();
		for(QuestionResult questionResult: questionResults){
			if(userID != questionResult.getTestID() || testID != questionResult.getTestID()){
				questionResults.remove(questionResult);
			}
		}
		return questionResults;
	}
	@Override
	public List<QuestionResult> getQuestionResultsByUserAndTestAndTimeStamp(int userID, int testID, String timestamp) {
		List<QuestionResult> questionResults = getQuestionResults();
		for(QuestionResult questionResult: questionResults){
			if(userID != questionResult.getTestID() || testID != questionResult.getTestID() || !timestamp.equals(questionResult.getTimestamp())){
				questionResults.remove(questionResult);
			}
		}
		return questionResults;
	}
	@Override
	public String getValidation(int userID, int testID, String timeStamp){
		int numberOfCorrect = 0;
		int numberOfWrong = 0;
		JSONObject inner = null;
		JSONObject wrapper = null;
		List<QuestionResult> questionResults = getQuestionResultsByUserAndTestAndTimeStamp(userID, testID, timeStamp);
		for(int i = 0; i < questionResults.size(); i++){
			QuestionResult result = questionResults.get(i);
			Question question = getQuestionByID(testID, result.getQuestionID());
			/*
			if(question.getQuestionType().equalsIgnoreCase("MAT")){
				boolean correct = true;
				String[] possibles = result.getPossibleAnswers();
				String[] corrects = result.getAnswer();
				Map<String, String> comparison = question.getMatComparison();
				for(int j = 0; j < result.getPossibleAnswers().length; j++){
					if(!corrects[j].equalsIgnoreCase(comparison.get(possibles[j]))){
						correct = false;
					}
				}
				if(correct){
					numberOfCorrect++;
				}
				else{
					numberOfWrong++;
				}
				
			}
			*/
		
			if(Arrays.equals(result.getAnswer().split(DIV.DELIMITER), question.getCorrectAnswers().split(DIV.DELIMITER))){
				numberOfCorrect++;
			}
			else{
				numberOfWrong++;
			}
			
		}
		try{
			inner = new JSONObject();
			wrapper = new JSONObject();
			inner.put("numberOfCorrect", numberOfCorrect);
			inner.put("numberOfWrong", numberOfWrong);
			wrapper.put("testResult", inner);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return wrapper.toString();
	}
	@Override
	public String returnQuestionResultsByUserAndTest(int userID, int testID){
		QuestionResultList list = new QuestionResultList(getQuestionResultsByUserAndTest(userID, testID));
		list.setJSONObject();
		return list.toString();
	}
	
	@Override
	public String returnQuestionResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp){
		QuestionResultList list = new QuestionResultList(getQuestionResultsByUserAndTestAndTimeStamp(userID, testID, timeStamp));
		list.setJSONObject();
		return list.toString();
	}
	@Override
	public Question getQuestionByID(int testID, int questionID) {
		List<Question> questions = getQuestions();
		for(Question question: questions){
			if(testID == question.getTestID() && questionID == question.getQuestionID()){
				return question;
			}
		}
		
		return null;
	}
	@Override
	public String returnQuestionByID(int testID, int questionID) {
		Question question = getQuestionByID(testID, questionID);
		if(question != null){
			return question.toString();
		}
		else{
			return null;
		}

	}
	@Override
	public int addQuestion(Question question) {
		
		List<Question> questions = getQuestions();
		for(Question q: questions){
			if(q.getQuestionID() == question.getQuestionID() && q.getTestID() == question.getTestID()){
				return 0;
			}
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		
		try{
			tx = session.beginTransaction();
			session.save(question);
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
		List<Question> questions = getQuestions();
		for(Question existingQuestion: questions){
			if(existingQuestion.getTestID() == question.getTestID() && existingQuestion.getQuestionID() == question.getQuestionID()){
				return 0;
			}
		}
		jdbcTemplate = Database.getTemplate();
		String query = "INSERT INTO protoQuestions (testID, questionID, question, questionType, possibleAnswers, correctAnswer)"
									     + " VALUES(?,?,?,?,?,?)";
		
				
		jdbcTemplate.execute(query,new PreparedStatementCallback(){  
			   @Override  
			   public Boolean doInPreparedStatement(PreparedStatement stmt) throws SQLException, DataAccessException {  
			              
			    	stmt.setInt(1, question.getTestID());
					stmt.setInt(2, question.getQuestionID());
					stmt.setString(3, question.getQuestion());
					stmt.setString(4, question.getQuestionType());
					stmt.setString(5, String.join(DIV.DELIMITER, question.getPossibleAnswers()));
					stmt.setString(6, String.join(DIV.DELIMITER, question.getCorrectAnswers()));  
			              
			        return stmt.execute();  
			              
			    }  
		});
		
		return 1;
			
		*/
		
	}
	
	@Override
	public int updateQuestion(Question question) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(question);
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
		Question updateQuestion = getQuestionByID(question.getTestID(), question.getQuestionID());
		if(updateQuestion == null){
			return 0;
		}
		jdbcTemplate = Database.getTemplate();
		
		String query = "UPDATE protoQuestions SET question = ?, SET questionType = ?, possibleAnswers = ?, correctAnswer = ?"
			+ " WHERE testID = ? && questionID = ?";
		
		jdbcTemplate.execute(query, new PreparedStatementCallback(){

			@Override
			public Boolean doInPreparedStatement(PreparedStatement stmt)
					throws SQLException, DataAccessException {
				stmt.setString(1, question.getQuestion());
				stmt.setString(2, question.getQuestionType());
				stmt.setString(3, String.join(DIV.DELIMITER, question.getPossibleAnswers()));
				stmt.setString(4, String.join(DIV.DELIMITER, question.getCorrectAnswers()));
				stmt.setInt(5, question.getTestID());
				stmt.setInt(6, question.getQuestionID());
				return stmt.execute();
			}
			
		});
		
		return 1;
		*/
	}
	
	@Override
	public int deleteQuestion(int testID, int questionID) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Question question = getQuestionByID(testID, questionID);
		if(question == null){
			return 0;
		}
		try{
			tx = session.beginTransaction();
			session.delete(question);
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
		Question question = getQuestionByID(testID, questionID);
		if(question == null){
			return 0;
		}
		String query = "DELETE FROM protoQuestions WHERE testID = ? && questionID = ?";
		jdbcTemplate = Database.getTemplate();
		
		jdbcTemplate.execute(query, new PreparedStatementCallback(){

			@Override
			public Boolean doInPreparedStatement(PreparedStatement stmt)
					throws SQLException, DataAccessException {
				stmt.setInt(1, testID);
				stmt.setInt(2, questionID);
				return stmt.execute();
			}
			
		});
		
		return 1;
		*/
		
	}
	@Override
	public int deleteTestQuestions(int testID){
		List<Question> questions = getQuestions();
		for(Question question: questions){
			if(question.getTestID() == testID){
				deleteQuestion(testID, question.getQuestionID());
			}
		}
		/*
		String query = "DELETE * FROM protoQuestions WHERE testID = ?";
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
		*/
		return 1;
		
		
	}
	
}
