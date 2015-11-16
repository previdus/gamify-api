<div>
	<div style="text-align:center" >
		<h2 class="heading">Profile Update</h2>
		<input type="hidden" id="updateProfileUserId" value="${user.id}"></input>
		<hr>
	</div>
	<div id="updateStatus"></div>
	
	<form  class="form-horizontal">
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
				<label class="control-label " for="displayName">Display Name:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<input type="text" id="displayName" class="form-control"
					placeholder="Your full name ">
					
			</div>
			<div class="col-md-2" style="margin-bottom: 1em;">
				<button id="displayNameUpdateButton" class="btn btn-primary">Update</button>
			</div>
		</div>
		
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
				<label class="control-label " for="loginId">Login Id:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<input type="text" id="loginId" class="form-control"
					placeholder="loginId " readonly >
					
			</div>
			<div class="col-md-2" style="margin-bottom: 1em;">
			 
			</div>
		</div>
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
			 	<label for="email" class="control-label">Your Email:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<input type="email" class="form-control" id="email" placeholder="Your Email" data-error="Bruh, that email address is invalid" required>
				<div class="help-block with-errors"></div>
			</div>
			
			<div class="col-md-2" style="margin-bottom: 1em;">
				<button id="emailButton" class="btn btn-primary">Update</button>
			</div>
		</div>
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
				<label class="control-label " for="parentEmail">Parent's Email:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<input type="email" class="form-control" id="parentEmail" placeholder="Your Parents Email" data-error="Bruh, that email address is invalid" required>
				<div class="help-block with-errors"></div>
			</div>
			</div>
			<div class="col-md-2" style="margin-bottom: 1em;">
				<button id="parentEmailButton" class="btn btn-primary">Update</button>
			</div>
		</div>
		
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
				<label class="control-label " for="phoneNumber">Phone Number:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<input type="text" id="phoneNumber" class="form-control"
					placeholder="Your phone number">
			</div>
			<div class="col-md-2" style="margin-bottom: 1em;">
				<button id="phoneNumberUpdateButton" class="btn btn-primary">Update</button>
			</div>
		</div>
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
				<label class="control-label " for="gender">Gender:</label>
			</div>
			<div class="col-md-7" style="margin-bottom: 1em;">
				<select class="form-control" id="gender">
                    				<option value="1">Male</option>	
                    				<option value="2">Female</option>
                </select>
			</div>
			<div class="col-md-2" style="margin-bottom: 1em;">
				<button id="genderUpdateButton" class="btn btn-primary">Update</button>
			</div>
		</div>
		<div>
			<hr>
		</div>
	</form>
	<div>
		<jsp:include page="change-password.jsp" flush="true"/>
	</div>
</div>
