var BASE_URI="http://127.0.0.1:8080/grouptalk"

function linksToMap(links){
	var map = {};
	$.each(links, function(i, link){
		$.each(link.rels, function(j, rel){
			map[rel] = link;
		});
	});

	return map;
}

function loadAPI(complete){
	$.get(BASE_URI)
		.done(function(data){
			var api = linksToMap(data.links);
			sessionStorage["api"] = JSON.stringify(api);
			complete();
		})
		.fail(function(data){
		});
}

/*function getCurrentUserProfile(complete){
	var authToken = JSON.parse(sessionStorage["auth-token"]);
	var uri = authToken["links"]["user-profile"].uri;
	$.get(uri)
		.done(function(user){
			user.links = linksToMap(user.links);
			complete(user);
		})
		.fail(function(){});
}*/


//Posible incluir login y logout



function loadRespuestas(uri, complete){
	// var authToken = JSON.parse(sessionStorage["auth-token"]);
	// var uri = authToken["links"]["current-stings"].uri;
	$.get(uri)
		.done(function(respuestas){ //¿que es lo que va entre parentesis, un metodo java o es una variable js?
			respuestas.links = linksToMap(respuestas.links);
			complete(respuestas);
		})
		.fail(function(){});
}

function getRespuesta(uri, complete){
	$.get(uri)
		.done(function(resp){
			complete(resp);
		})
		.fail(function(data){
		});
}