var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";
var userid = "";
var login = "";
var token = "";

$(document).ready(function() {
	userid= $.cookie('userid');
	login = $.cookie('login');
	token = $.cookie('token');
});


$("#obtenerdatosuser").click(function(e) {
	mostrardatos_usuario(userid,login,token);
	
});
function mostrardatos_usuario(userid,login,token)
{ 
	
	alert(userid);
	alert(login);
	alert(token);
	
}
