        <div class="bs-example">
			<h3>Update  ${topicName} Questions</h3>
			<div id="updateStatus"></div>
		
		<form class="form-horizontal">
		
				<div class="form-group">
            			<label class="control-label col-xs-2" for="displayQuestionIdOnupdate">Question ID:</label>
            			<div class="col-xs-9">
            				<input class="form-control" id="displayQuestionIdOnupdate" type="text" placeholder="" disabled>
            			</div>
        		</div>
        		<div class="form-group">
            			<label class="control-label col-xs-2" for="displayQuestionIdOnupdate">Question Status:</label>
            			<div class="col-xs-9">
            				<input class="form-control" id="stateOnQuestionUpdate" type="text" placeholder="" disabled>
            				<button type="button" id="updateStateEnable" value="enable" class="btn btn-primary btn-xs">Enable</button>
            				<button type="button" id="updateStateDisable" value="disable" class="btn btn-primary btn-xs">Disable</button>
            				<button type="button" id="updateStateSoftDelete" value="softdelete" class="btn btn-primary btn-xs">Soft-Delete</button>
            			</div>
            			
        		</div>
        		<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMQuestionText">Question Text:</label>
            			<div class="col-xs-9">
                			<textarea id="updateMQuestionText" class="form-control" placeholder="Type Question Text Here"></textarea>
            				<button type="button" id="questiontextUpdate" class="btn btn-primary btn-xs">Update</button>
            			</div>
        		</div>
        	<div class="form-group">
            			<label class="control-label col-xs-2">Difficulty Level:</label>
            			<div class="col-xs-3">
                			<select class="form-control" id="updateMdifficulty">
                    				<option value="1">Easy</option>
						<option value="2">Moderate</option>	
						<option value="3">Tough</option>
                			</select>
                			<button type="button" id="updateDiffLevelButton" class="btn btn-primary btn-xs">Update</button>
            			</div>
				<div class="form-group">
					<div class="col-xs-4">
                				<div class="input-group">
                    					<span class="input-group-addon">Time Allocation</span>
                    					<input type="text" id="updatetimeAllocated" class="form-control" placeholder="max time allowed ">
                    					<span class="input-group-addon">seconds.</span>
                				</div>
                				<button type="button" id="updateTimeAllocationButton" class="btn btn-primary btn-xs">Update</button>
            				</div>
				</div>
			</div>	
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt1">Option 1:</label>
            			<div class="col-xs-9">
            				<input type="hidden" id="updateOption1Id"  value=""></input> 
							<input type="radio" name="updateCorrectOpt" id="updateCorrectOption1" onclick="updateAns(1)" value="1">  is Correct!        
                			<textarea id="updateinputMOpt1" class="form-control" placeholder="Type 1st Option Here"></textarea>
                			<button type="button" id="updateOption1Text" class="btn btn-primary btn-xs">Update Text/ Create</button>
                			<label > State: </label>
                			<input  type="text" disabled="disabled" id="updateOption1State" value="">
                			<button type="button" id="updateOption1Enable" class="btn btn-primary btn-xs">Enable</button>
                			<button type="button" id="updateOption1Disable"  class="btn btn-primary btn-xs">Disable</button>
                			<button type="button" id="updateOption1SoftDelete"  class="btn btn-primary btn-xs">Soft Delete</button>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt2">Option 2:</label>
				<div class="col-xs-9">
				<input type="hidden" id="updateOption2Id"  value=""></input> 
					<input type="radio" name="updateCorrectOpt" onclick="updateAns(2)" id="updateCorrectOption2" value="2">  is Correct!
                			<textarea id="updateinputMOpt2" class="form-control" placeholder="Type 2nd Option Here"></textarea>
            			<button type="button" id="updateOption2Text" class="btn btn-primary btn-xs">Update Text/ Create</button>
            			<label > State: </label>
                			<input  type="text" disabled="disabled" id="updateOption2State" value="">
                			<button type="button" id="updateOption2Enable" class="btn btn-primary btn-xs">Enable</button>
                			<button type="button" id="updateOption2Disable"  class="btn btn-primary btn-xs">Disable</button>
                			<button type="button" id="updateOption2SoftDelete"  class="btn btn-primary btn-xs">Soft Delete</button>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt3">Option 3:</label>
            			<div class="col-xs-9">
            			<input type="hidden" id="updateOption3Id"  value=""></input> 
						<input type="radio" name="updateCorrectOpt" onclick="updateAns(3)" id="updateCorrectOption3" value="3">  is Correct!
                			<textarea  id="updateinputMOpt3" class="form-control" placeholder="Type 3rd Option Here"></textarea>
            			<button type="button" id="updateOption3Text" class="btn btn-primary btn-xs">Update Text/ Create</button>
            			<label > State: </label>
                			<input  type="text" disabled="disabled" id="updateOption3State" value="">
                			<button type="button" id="updateOption3Enable" class="btn btn-primary btn-xs">Enable</button>
                			<button type="button" id="updateOption3Disable"  class="btn btn-primary btn-xs">Disable</button>
                			<button type="button" id="updateOption3SoftDelete"  class="btn btn-primary btn-xs">Soft Delete</button>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputEmail">Option 4:</label>
            			<div class="col-xs-9">
            			<input type="hidden" id="updateOption4Id"  value=""></input> 
					<input type="radio" name="updateCorrectOpt" onclick="updateAns(4)" id="updateCorrectOption4" value="4">  is Correct!
                			<textarea id="updateinputMOpt4" class="form-control" placeholder="Type 4th Option Here"></textarea>
            			<button type="button" id="updateOption4Text" class="btn btn-primary btn-xs">Update Text/ Create</button>
            			<label > State: </label>
                			<input  type="text" disabled="disabled" id="updateOption4State" value="">
                			<button type="button" id="updateOption4Enable" class="btn btn-primary btn-xs">Enable</button>
                			<button type="button" id="updateOption4Disable"  class="btn btn-primary btn-xs">Disable</button>
                			<button type="button" id="updateOption4SoftDelete"  class="btn btn-primary btn-xs">Soft Delete</button>
            			</div>
        		</div>
			<div class="form-group">
            			<label class="control-label col-xs-2" for="inputMOpt5">Option 5:</label>
            			<div class="col-xs-9">
            			<input type="hidden" id="updateOption5Id"  value=""></input> 
					<input type="radio" name="updateCorrectOpt" onclick="updateAns(5)" id="updateCorrectOption5" value="5">  is Correct!                			
					<textarea id="updateinputMOpt5" class="form-control" placeholder="Type 5th Option Here"></textarea>	
            			<button type="button" id="updateOption5Text" class="btn btn-primary btn-xs">Update Text/ Create</button>
            			<label > State: </label>
                			<input  type="text" disabled="disabled" id="updateOption5State" value="">
                			<button type="button" id="updateOption5Enable" class="btn btn-primary btn-xs">Enable</button>
                			<button type="button" id="updateOption5Disable"  class="btn btn-primary btn-xs">Disable</button>
                			<button type="button" id="updateOption5SoftDelete"  class="btn btn-primary btn-xs">Soft Delete</button>
            			</div>
        		</div>
        		<div class="form-group">
        		<label class="control-label col-xs-2" for="inputMOpt5">Deleted Options:</label>
        		<div id="deletedOptions"></div>
        		</div>
		</form>
	    </div>