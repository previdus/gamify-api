<div>
	<form data-toggle="validator" class="form-horizontal" >
		<div>
			<input type="hidden" id="changePasswordUserId" value="${user.id}"></input>
			<hr>
		</div>
		<div style="text-align:center" >
			<div class="col-md-12" style="margin-bottom: 1em;">
				<hr>
				<h2 class="heading">Change Password</h2>
				<hr>
			</div>	
		</div>
		<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
    			<label for="inputPassword" class="control-label">Current Password</label>
    		</div>
    		<div class="col-md-7" style="margin-bottom: 1em;">
      			<input type="password" data-minlength="6" class="form-control" id="currentPassword" placeholder="Password" required>
      			<span class="help-block">Minimum of 6 characters</span>
    		</div>
    		<div class="col-md-2" style="margin-bottom: 1em;">
    		</div>
    	</div>
    	<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
    			<label for="inputPassword" class="control-label">New Password</label>
    		</div>
    		<div class="col-md-7" style="margin-bottom: 1em;">
      			<input type="password" data-minlength="6" class="form-control" id="newPassword" placeholder="New Password" required>
      			<span class="help-block">Minimum of 6 characters</span>
    		</div>
    		<div class="col-md-2" style="margin-bottom: 1em;">
    		</div>
    	</div>
    	<div>
			<div class="col-md-3" style="margin-bottom: 1em;">
    			<label for="inputPassword" class="control-label">Confirm New Password</label>
    		</div>
    		<div class="col-md-7" style="margin-bottom: 1em;">
      			<input type="password" data-minlength="6" class="form-control" id="confirmNewPassword" placeholder="Confirm new Password" required>
      			<span class="help-block">Minimum of 6 characters</span>
    		</div>
    		<div class="col-md-2" style="margin-bottom: 1em;">
    		</div>
    	</div>
    	<div>
    		<div class="col-md-12"  style="margin-bottom: 1em; text-align:center">
    			<button type="button" id="updatePasswordButton" class="btn btn-primary">Update password</button>
    		</div>
    	</div>	
	</form>
</div>	

