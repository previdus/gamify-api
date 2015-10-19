  <div class="bs-example">
		<h3>Add a new Free Text Question</h3>
		<div id="freeStatus"></div>
		<form class="form-horizontal" id="freeTextQuestionForm" >
        		<div class="form-group">
            			<label class="control-label col-xs-3" for="inputEmail">Question Text:</label>
            			<div class="col-xs-9">
                			<textarea id="freeQuestionText" class="form-control" placeholder="Type Question Text Here"></textarea>
            			</div>
        		</div>
				<div class="form-group">
            			<label class="control-label col-xs-3" for="inputEmail">Correct Answer:</label>
            			<div class="col-xs-9">
                			<textarea id="freeAnswer" name="freeAnswer" class="form-control" placeholder="Type Correct Answer Here"></textarea>
            			</div>
        		</div>
				<div class="form-group">
            			<label class="control-label col-xs-3">Difficulty Level:</label>
            			<div class="col-xs-3">
                			<select id="freedifficulty" class="form-control">
                    				<option value="1">Easy</option>
									<option value="2">Moderate</option>	
									<option value="3">Tough</option>
                			</select>
            			</div>
						<div class="form-group">
							<div class="col-xs-4">
                				<div class="input-group">
                    				<span class="input-group-addon">Time Allocation</span>
                    				<input id="ffftimeAllocated" name="ffftimeAllocated" type="text" class="form-control" placeholder="max time allowed ">
                    				<span class="input-group-addon">seconds.</span>
                				</div>
            				</div>
						</div>
				</div>
				<div class="form-group">
            			<div class="col-xs-offset-5 col-xs-15">
                			<input id="ffSubmit" name="ffSubmit" type="button" class="btn btn-primary" value="Submit">
                			<input type="reset" class="btn btn-default" value="Reset">
            			</div>
        		</div>
		</form>
	    </div>