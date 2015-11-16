<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid lms">
    	<div class="navbar-header">
	      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	        <span class="sr-only"></span>
	        <span class="icon-bar"></span>
	      </button>
	      <div class="logo-section">
                  <h1>lastman<span class="half">standing</span></h1>
          </div>
 		</div>
 		<!-- Collect the nav links, forms, and other content for toggling -->
 		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
     		<ul class="nav navbar-nav navbar-right">
        		<li><a class="clearTimeInterval" href="#" onClick="$('#backToMainRoom').submit()"><span class="leave-game">
        			Welcome ${user.displayName}&nbsp;&nbsp;&nbsp;<img src="${user.imageUrl}"></img>
        		</span></a></li>
        		<li class="dropdown">
          			<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          				<span id="displayUserName"></span> 
          				<span class="caret"></span>
         			 </a>
          			<ul class="dropdown-menu">
            			<li class="clearTimeInterval"><a href="#" onClick="$('#profileupdate').submit()"><i class="glyphicon glyphicon-user">&nbsp;Profile</i></a></li>
            			<li class="clearTimeInterval"><a href="#" onClick="$('#backToMainRoom').submit()"><i class="glyphicon glyphicon-cog">&nbsp;Game Room</i></a></li>
            			<li><a  href="#menu-toggle" id="menu-toggle"><i class="glyphicon glyphicon-indent-left" >&nbsp;Toggle Pane</i></a></li>
            			<li role="separator" class="divider"></li>
            			<li class="clearTimeInterval"><a  href="#" onClick="$('#logoutform').submit()"><i class="glyphicon glyphicon-log-out">&nbsp;Logout</i></a></li>
          			</ul>
        		</li>
      		</ul>
  		</div><!-- /.navbar-collapse -->    
	</div><!-- /.container-fluid -->
</nav>

<form id="profileupdate"  action="${pageContext.request.contextPath}/profile/update"></form>
<form id="backToMainRoom"  action="${pageContext.request.contextPath}/rooms/changeroom"></form>
<form id="logoutform"  action="${pageContext.request.contextPath}/logout"></form>
<form id="reviewPage"  action="${pageContext.request.contextPath}/review-game/${gameId}"></form>


 
