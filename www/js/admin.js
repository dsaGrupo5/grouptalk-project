var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";
var LOGIN = "";
var USERID = "";
var TOKEN = "";

$(document).ready(function() {
	USERID= $.cookie('userid');
	LOGIN = $.cookie('login');
	TOKEN = $.cookie('token');
});



$("#button_to_create").click(function(e) {
	e.preventDefault();
	var nuevoGrupo	= new Object();
	nuevoGrupo.nombre = $("#nombre_grupo").val();
	crearGrupo(nuevoGrupo);
	
});



function crearGrupo(grupo) {
	var url = API_BASE_URL + '/grupo';
	alert(grupo.nombre);
	var data = JSON.stringify(grupo);
	console.log(data);
	console.log(grupo);	
	
	$.ajax({
		url : url,
		type : 'POST',
		data : $.param(grupo),
		headers: {"X-Auth-Token":TOKEN}
	}).done(function(data, status, jqxhr) {
		 $('<div class="alert alert-danger"> <strong>Grupo creado correctamente</strong>').appendTo($("#resultado"));			
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Este grupo ya existe</strong>').appendTo($("#resultado"));
	});

	

}

