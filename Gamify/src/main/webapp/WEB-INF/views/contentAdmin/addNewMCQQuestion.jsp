        <div class="bs-example">
			<h3>Add a new Multiple Choice Question for ${topicName} </h3>
			<div id="mFormStatus"></div>
		<form class="form-horizontal">
        		<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMQuestionText">Question Text:</label>
            			<div class="col-xs-9">
                			<textarea id="inputMQuestionText" class="form-control" placeholder="Type Question Text Here"></textarea>
            			</div>
        		</div>
        		<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMQuestionText">Option free text display:</label>
            			<div class="col-xs-2">
                			<textarea id="mcqPreTextForFreeTextQustion" class="form-control" placeholder="Pre Text"></textarea>
            			</div>
            			<div class="col-xs-2">
                			<textarea id="mcqPostTextForFreeTextQustion" class="form-control" placeholder="Post Text"></textarea>
            			</div>
            			<label class="control-label col-xs-2" for="mcqQuestionType"> Question presented as:</label>
            			<div class="col-xs-3">
                			<select class="form-control" id="mcqQuestionType">
                    				<option value="MCQ">MCQ Only</option>	
                    				<option value="FREE_TEXT">Free Text Only</option>
									<option value="MCQ_OR_FREE_TEXT">MCQ/ Free Text Any</option>
                			</select>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt1">Option 1:</label>
            			<div class="col-xs-9">
					<input type="radio" name="mcqcorrectOpt" value="1">  is Correct!
                			<textarea id="inputMOpt1" class="form-control" placeholder="Type 1st Option Here"></textarea>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt2">Option 2:</label>
				<div class="col-xs-9">
					<input type="radio" name="mcqcorrectOpt" value="2">  is Correct!
                			<textarea id="inputMOpt2" class="form-control" placeholder="Type 2nd Option Here"></textarea>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt3">Option 3:</label>
            			<div class="col-xs-9">
					<input type="radio" name="mcqcorrectOpt" value="3">  is Correct!
                			<textarea  id="inputMOpt3" class="form-control" placeholder="Type 3rd Option Here"></textarea>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputEmail">Option 4:</label>
            			<div class="col-xs-9">
					<input type="radio" name="mcqcorrectOpt" value="4">  is Correct!
                			<textarea id="inputMOpt4" class="form-control" placeholder="Type 4th Option Here"></textarea>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt5">Option 5:</label>
            			<div class="col-xs-9">
					<input type="radio" name="mcqcorrectOpt" value="5">  is Correct!                			
					<textarea id="inputMOpt5" class="form-control" placeholder="Type 5th Option Here"></textarea>	
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2">Difficulty Level:</label>
            			<div class="col-xs-3">
                			<select class="form-control" id="Mdifficulty">
                    				<option value="1">Easy</option>
						<option value="2">Moderate</option>	
						<option value="3">Tough</option>
                			</select>
            			</div>
				<div class="form-group">
					<div class="col-xs-4">
                				<div class="input-group">
                    					<span class="input-group-addon">Time Allocation</span>
                    					<input type="text" id="timeAllocated" class="form-control" placeholder="max time allowed ">
                    					<span class="input-group-addon">seconds.</span>
                				</div>
            				</div>
				</div>
			</div>
			<div class="form-group">
            			<div class="col-xs-offset-5 col-xs-15">
                			<input id="mSubmit" name="mSubmit" type="button" class="btn btn-primary" value="Submit">				
                			<input type="reset" class="btn btn-default" value="Reset">
            			</div>
        		</div>

  
		</form>
	    </div>