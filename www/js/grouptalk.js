var API_BASE_URL = "http://127.0.0.1:8080/grouptalk";

$(document).ready(function() {
});

function previousRespuestas(){
  loadRespuestas($('#formPrevious').attr('action'), function(respuestas){
    processStingCollection(respuestas);
  });
}

function processRespuestaCollection(respuestas){
  var lastIndex = respuestas["respuestas"].length - 1;
  $.each(respuestas["respuestas"], function(i,resp){
      resp.links=linksToMap(respuesta.links);
      var edit = resp.userid ==JSON.parse(sessionStorage["auth-token"]).userid; //obtener id de usuario de las cookies
      $("#respuestas-list").append(listItemHTML(resp.links["self"].uri, resp.respuesta, resp.userid));
      if(i==0)
        $("#buttonUpdate").click(function(){alert("este usuario no tiene respuestas creadas")});
      if(i==lastIndex){
        $('#formPrevious').attr('action', resp["links"].previous.uri);
      }
  });

   $("#formPrevious").submit(function(e){
      e.preventDefault();
      e.stopImmediatePropagation();
      previousRespuestas();
      $("#buttonPrevious").blur();
    });

  $("a.list-group-item").click(function(e){
    e.preventDefault();
    e.stopImmediatePropagation();
    var uri = $(this).attr("href");
    getRespuesta(uri, function(resp){
      // In this example we only log the sting
      console.log(respuesta);
    });
  });
  $(".glyphicon-pencil").click(function(e){
    e.preventDefault();
    alert("This should open a sting editor. But this is only an example.");});
}

/*$("#aCloseSession").click(function(e){
  e.preventDefault();
  logout(function(){
    window.location.replace('login.html');
  });
});*/

function listItemHTML(uri, respuesta, userid){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  return a + p +  '</a>';
}
