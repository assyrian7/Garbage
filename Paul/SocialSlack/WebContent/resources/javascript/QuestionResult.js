var QuestionResult = (function(){
	
	function QuestionResult(questionID, testID, answer){
		this.testID = testID;
		this.questionID = questionID;
		this.answer = answer;
	}
	QuestionResult.prototype.JSON = function(){
		var str = "";
		str += "{\"question\":";
		var json = {questionID: this.questionID, testID: this.testID, answer: this.answer};
		json = JSON.stringify(json);
		str += json;
		str += "}";
		return str;
	};
	return QuestionResult;
})();