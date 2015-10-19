  <div class="bs-example">
		<form class="form-horizontal" id="freeTextQuestionForm" >
			    <div id="searchStatus"></div>
				<div class="form-group">
            			<div class="col-xs-4">
                			<div class="input-group">
                    			<input type="text" id="questionIdForSearch" class="form-control" placeholder="Question Id&hellip;">
                    				<span class="input-group-btn">
                        				<button type="button" id="questionIdSearchButton" class="btn btn-default">Go</button>
                    				</span>
                			</div>
            			</div>
						<div class="form-group">
							<label class="control-label col-xs-2">Question Status:</label>
            					<div class="col-xs-3">
                					<select class="form-control" id="searchStateOnUpdateScreen">
                    					<option value="REVIEW_PENDING">Review Pending</option>
										<option value="ACTIVE">Active</option>	
										<option value="INACTIVE">Inactive</option>
										<option value="SOFT_DELETE">Deleted</option>
                					</select>
            					</div>
						</div>
				</div>
				
		<ul class="pager">
    		<li class="previous"><a id="updateScreenPreviousSearchButton" href="#">&larr; Previous Question</a></li>
    		<li class="next"><a id="updateScreenNextSearchButton" href="#">Next Question&rarr;</a></li>
		</ul>
		<input type="hidden" id="updateScreenPageNo" value="0"></input>
		<input type="hidden" id="updateScreenFetchSize" value="1"></input>		
		</form>
	    </div>