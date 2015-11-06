var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";



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
	var log = new Object();
	var loginid = $("#login_username").val();
	var password = $("#login_password").val();	
	getlogin(loginid, password);
	}
});

function getlogin(loginid, password)
{ 	
	var url = API_BASE_URL + '/login/login_in';		
	alert(url);
	$.post( url,{login: loginid,password : password})
		.done(function(data, status, jqxhr)
		{ alert('todo ok');
		})
		.fail( function( jqXHR, textStatus, errorThrown )
		{ 
		});

}
	
