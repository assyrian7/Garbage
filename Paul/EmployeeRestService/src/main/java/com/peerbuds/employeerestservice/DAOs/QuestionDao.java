package com.peerbuds.employeerestservice.DAOs;

import java.util.List;

import com.peerbuds.employeerestservice.Models.Question;
import com.peerbuds.employeerestservice.Models.QuestionResult;

public interface QuestionDao {
		
	public List<Question> getQuestions();
	public String getQuestionsByTest(int testID);
	public String getAllQuestions();
	public Question getQuestionByID(int testID, int questionID);
	public String returnQuestionByID(int testID, int questionID);
	public int updateQuestionResults(QuestionResult questionResult);
	public String getURLText(String url);
	
}


