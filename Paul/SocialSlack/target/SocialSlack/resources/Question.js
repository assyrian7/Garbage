var Question = (function(){
	
	function Question(questionID, question, questionType, possibleAnswers, correctAnswers){
		this.questionID = questionID;
		this.question = question;
		this.questionType = questionType;
		this.possibleAnswers = possibleAnswers;
		this.correctAnswers = correctAnswers;
	}
	Question.prototype.JSON = function(){
		var str = "";
		str += "{\"question\":";
		var json = {questionID: parseInt(this.questionID), actualQuestion: this.question, questionType: this.questionType, possibleAnswers: this.possibleAnswers, correctAnswers: this.correctAnswers};
		json = JSON.stringify(json);
		str += json;
		str += "}";
		return str;
	};
	return Question;
})();
