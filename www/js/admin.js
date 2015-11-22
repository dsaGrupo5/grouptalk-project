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

//botone para editar grupo.


$("#button_cambiar_nombre_grupo").click(function(e) 
{
	e.preventDefault();
	
	var nombre1= new Object();
	var nombre2= new Object();
	
	nombre1 = $("#nombre_grupo_editar").val();
    nombre2 = $("#nombre_nuevo_a_asignar").val();	
	editarGrupo(nombre1, nombre2);
});
$("#button_obtener_grupos").click(function(e)
{
	e.preventDefault();
	obtenerColccionGrupos();
});

$("#button_obtener_grupos_paginados").click(function(e)
{
	e.preventDefault();
	getPagination();
});

function crearGrupo(grupo) 
{
	console.log(TOKEN);
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
function eliminarGrupo(grupo) 
{
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
function insertarGrupo(grupo) 
{
	console.log(grupo);
	var data = JSON.stringify(grupo);
	var url = API_BASE_URL + '/grupo/editar';
	console.log(data);
	    $.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		dataType : 'json',
		contentType : 'application/vnd.dsa.grouptalk.grupo+json',
		data : data,
		headers: {"X-Auth-Token":TOKEN},		
	}).done(function(data, status, jqxhr){
		alert ('edit ok');
	}).fail(function(){
		alert ('edit mal');
	});
}
function editarGrupo(nombre1, nombre2) 
{
	var url = API_BASE_URL + '/grupo/obtener_grupo';	
	$.ajax(
	{
		url : url,type : 'POST',
		data : { nombre: nombre1},
		headers: {"X-Auth-Token":TOKEN}
	}
	).success(function(data, status, jqxhr)
	{
			var grupo = new Object();			
			grupo.nombre= nombre2;
			grupo.id=data.id;
			grupo.links = data.links;
			insertarGrupo(grupo);
	}
	).fail(function()
	{
			alert ('obtener grupo mal');
	});	
}
function obtenerColccionGrupos() 
{	var url = API_BASE_URL + '/grupo/obtenergrupos';
	$("#repos_result").text('');
    $.ajax({
		url : url,
		type : 'GET',	
		crossDomain : true,	
		dataType : 'json',
		contentType : 'application/vnd.dsa.grouptalk.grupo.collection+json',
		})
	.success(function(data, status, jqxhr)
	{
		console.log(data);
		var grupos = data.grupos;
		$.each(grupos, function(i, v)
		{
			var grupo = v; 
			$('<strong> ID: ' + grupo.id + '<br>').appendTo($('#repos_result'));
			$('<strong> Nombre: </strong> ' + grupo.nombre + '<br>').appendTo($('#repos_result'));
		})
	})
	.fail(function(){$("#result").text("No files.");});
}
function getPagination(url) {
	$("#repos_result").text('');
	var url = API_BASE_URL + '/grupo/obtenergrupos';
	$.ajax({
		url : url,
		type : 'GET',	
		crossDomain : true,	
		dataType : 'json',
		contentType : 'application/vnd.dsa.grouptalk.grupo.collection+json',
	}).done(function(data, status, jqxhr) {
        	var response = data.grupos;
		var grupoCollection = new GrupoCollection(response);
                var linkHeader = jqxhr.getResponseHeader('Link');
                grupoCollection.buildLinks(linkHeader);
				console.log(linkHeader);

		var html = grupoCollection.toHTML();
		$("#repos_result").html(html);

	}).fail(function(jqXHR, textStatus) {
		console.log(textStatus);
	});

}
function GrupoCollection(grupoCollection){
	this.grupos = grupoCollection;
        var href = {};

	var instance = this;

	this.buildLinks = function(header){
		this.links = weblinking.parseHeader(header);
	}

	this.getLink = function(rel){
                return this.links.getLinkValuesByRel(rel);
	}

	this.toHTML = function(){
		var html = '';
		$.each(this.grupos, function(i, v) {
			var grupo = v;
			console.log(grupo);
			html = html.concat('<br><strong> Id: ' + grupo.id + '</strong><br>');
            html = html.concat('<br><strong> Nombre: ' + grupo.nombre + '</strong><br>');
		});
		
		html = html.concat(' <br> ');

                var prev = this.getLink('prev');
		if (prev.length == 1) {
			console.log(prev[0].href);
			html = html.concat(' <a onClick="getPagination(\'' + prev[0].href + '\');" style="cursor: pointer; cursor: hand;">[Prev]</a> ');
		}
                var next = this.getLink('next');
		if (next.length == 1) {
			html = html.concat(' <a onClick="getPagination(\'' + next[0].href + '\');" style="cursor: pointer; cursor: hand;">[Next]</a> ');
		}

 		return html;	
	}

}