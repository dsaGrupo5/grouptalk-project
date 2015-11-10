var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";
var USERNAME = "";
var PASSWORD = "";

$(document).ready(function() {
});

$("#login").click(function(e) {
	e.preventDefault();
	if($("#login_username").val() == "" || $("#login_password").val() == "")
	{
		if($("#login_username").val() == "")
		{
			document.getElementById('login_username').style.background='#F6B5B5';
			$('#login_username').attr('placeholder','RELLENE EL CAMPO');
		}
		if($("#login_password").val() == "")
		{
			document.getElementById('login_password').style.background='#F6B5B5';
			$('#login_password').attr('placeholder','RELLENE EL CAMPO');
		}
	}
	else
	{
	var login = $("#login_username").val();
	var password = $("#login_password").val();	
	getlogin(login, password);
	}
});

$("#sign").click(function(e) {
	e.preventDefault();
	if($("#new_usuario_nombre").val() == "" || $("#new_usuario_username").val() == "" || $("#new_usuario_password").val() == "" || $("#new_usuario_email").val() == "")
	{
		if($("#new_usuario_nombre").val() == "")
		{
			document.getElementById('new_usuario_nombre').style.background='#F6B5B5';
			$('#new_usuario_nombre').attr('placeholder','RELLENE EL CAMPO');
		}
		if($("#new_usuario_username").val() == "")
		{
			document.getElementById('new_usuario_username').style.background='#F6B5B5';
			$('#new_usuario_username').attr('placeholder','RELLENE EL CAMPO');
		}
		if($("#new_usuario_password").val() == "")
		{
			document.getElementById('new_usuario_password').style.background='#F6B5B5';
			$('#new_usuario_password').attr('placeholder','RELLENE EL CAMPO');
		}
		if($("#new_usuario_email").val() == "")
		{
			document.getElementById('new_usuario_email').style.background='#F6B5B5';
			$('#new_usuario_email').attr('placeholder','RELLENE EL CAMPO');
		}
	}
	else
	{
	
	var login = $("#new_usuario_login").val();
	var password = $("#new_usuario_password").val();
	var email = $("#new_usuario_Email").val();	
	var fullname = $("#new_usuario_fullname").val();		
	registrar_usuario(login, password, email , fullname);
	}
});
function getlogin(login, password)
{ 	
	var url = API_BASE_URL + '/login/login_in';		
	$.post( url,{login: login,password : password})
	.done(function(data, status, jqxhr)
		{
			if(data.role== 'registrado')
			{
				
				$.cookie('login', login);
				$.cookie('userid', data.userid);
				$.cookie('token', data.token);
				
			    window.location = "http://localhost/registered.html" ;
			}
			if(data.role== 'administrador')
			{
				window.location = "http://localhost/admin.html" ;
			}
		})
	    .fail( function( jqXHR, textStatus, errorThrown )
		{ 
		//CAMBIAR EL COLOR
			document.getElementById('login_username').style.background='#F6B5B5';
			document.getElementById('login_username').value=null;
			$('#login_username').attr('placeholder','USUARIO NO REGISTRADO');
		});

}
	
function registrar_usuario(login, password, email , fullname){
var url = API_BASE_URL + '/users';

	$.post(url,{loginid: login,password : password, email:email, fullname:fullname})
	.done(function(data, status, jqxhr){
		getlogin(login, password);
	})
	.fail(function() 
	{
		document.getElementById('new_usuario_login').style.background='#6600FF';
		document.getElementById('new_usuario_login').value=null;
		$('#new_usuario_login').attr('placeholder','YA EXISTE ESTE USERNAME');
		
	});
}