package com.peerbuds.testrestservice.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.peerbuds.testrestservice.ETC.DIV;
import com.peerbuds.testrestservice.ETC.Database;
import com.peerbuds.testrestservice.Models.Question;
import com.peerbuds.testrestservice.Models.QuestionList;
import com.peerbuds.testrestservice.Models.QuestionResult;
import com.peerbuds.testrestservice.Models.QuestionResultList;

public interface QuestionDao {
	
	public List<Question> getQuestions();
	public List<QuestionResult> getQuestionResults();
	public String getAllQuestions();
	public String getAllQuestionResults();
	public List<Question> getQuestionsByTest(int testID);
	public String returnQuestionsByTest(int testID);
	public List<QuestionResult> getQuestionResultsByUser(int userID);
	public String returnQuestionResultsByUser(int userID);
	public List<QuestionResult> getQuestionResultsByUserAndTest(int userID, int testID);
	public List<QuestionResult> getQuestionResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp);
	public String getValidation(int userID, int testID, String timeStamp);
	public String returnQuestionResultsByUserAndTest(int userID, int testID);
	public String returnQuestionResultsByUserAndTestAndTimeStamp(int userID, int testID, String timeStamp);
	public Question getQuestionByID(int testID, int questionID);
	public String returnQuestionByID(int testID, int questionID);
	public int addQuestion(Question question);
	public int updateQuestion(Question question);
	public int deleteQuestion(int testID, int questionID);
	public int deleteTestQuestions(int testID);
	
}
