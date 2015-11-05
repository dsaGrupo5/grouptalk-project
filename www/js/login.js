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
	login.username = $("#login_username").val();
	login.password = $("#login_password").val();	
	getlogin(login);
	}
});

function getlogin(login)
{ 	
	var url = API_BASE_URL + '/login/login_in';	
    var data = JSON.stringify(login);	
	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		dataType : 'json',
		contentType : 'application/vnd.dsa.grouptalk.auth-token+json',
		data : data,
	}).done(function(data, status, jqxhr){}).fail( function( jqXHR, textStatus, errorThrown ) {

        if (jqXHR.status === 0) {
    
            alert('Not connect: Verify Network.');

        } else if (jqXHR.status == 404) {

            alert('Requested page not found [404]');

        } else if (jqXHR.status == 500) {

            alert('Internal Server Error [500].');

        } else if (textStatus === 'parsererror') {

            alert('Requested JSON parse failed.');

        } else if (textStatus === 'timeout') {

            alert('Time out error.');

        } else if (textStatus === 'abort') {

            alert('Ajax request aborted.');

        } else {

            alert('Uncaught Error: ' + jqXHR.responseText);

        }

    });

}
	
