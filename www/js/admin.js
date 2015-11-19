var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";
var LOGIN = "";
var USERID = "";
var TOKEN = "";

$(document).ready(function() {
	USERID= $.cookie('userid');
	LOGIN = $.cookie('login');
	TOKEN = $.cookie('token');
});



$("#button_crear_grupo").click(function(e) {
	e.preventDefault();
	var nuevoGrupo	= new Object();
	nuevoGrupo.nombre = $("#nombre_grupo_crear").val();
	crearGrupo(nuevoGrupo);
	
});

$("#button_eliminar_grupo").click(function(e) {
	e.preventDefault();
	var nuevoGrupo	= new Object();
	nuevoGrupo.nombre = $("#nombre_grupo_eliminar").val();
	eliminarGrupo(nuevoGrupo);
	
});

//botones para editar grupo. Pendiente de crear las 2 funciones y configurarlos bien
$("#button_obtener_grupo_edit").click(function(e) {
	e.preventDefault();
	getGrupoToEdit($("#nombre_grupo_editar").val());
	
});

$("#button_editar_grupo").click(function(e) {
	e.preventDefault();
	var nuevoGrupo	= new Object();
	nuevoGrupo.nombre = $("#nombre_grupo_editar").val();
	var nuevoNombre = $("#nombre_nuevo_a_asignar").val();
	editarGrupo(nuevoGrupo);
	
});


function crearGrupo(grupo) {
	var url = API_BASE_URL + '/grupo';
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
function eliminarGrupo(grupo) {
	var url = API_BASE_URL + '/grupo/eliminar/'+ grupo.nombre;
	console.log(url);	
	$.ajax({
		url : url,
		type : 'DELETE',
		headers: {"X-Auth-Token":TOKEN}
	}).done(function(data, status, jqxhr) {
		 $('<div class="alert alert-danger"> <strong>Grupo eliminado correctamente</strong>').appendTo($("#resultado_elim"));			
  	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Este grupo no existe</strong>').appendTo($("#resultado_elim"));
	});

}

//Esta funcion utilizará un metodo GET de la API que devuelva el objeto JSON tipo grupo, que no esta hecho...
function getGrupoToEdit(nombre_grupo) {
	var url = API_BASE_URL + '/repos/' + USERNAME + '/' + repository_name;
	$("#update_result").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
		
				var repo = data;
				

				$("#update_result").text('');
				$("#repository_name_to_edit").val(repo.name);
				$("#description_to_edit").val(repo.description);

	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Repository not found </div>').appendTo($("#update_result"));
	});

}
