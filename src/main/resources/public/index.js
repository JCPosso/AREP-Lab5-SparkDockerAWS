$(document).ready(function(){
  $("submit-button").click(function(){
    alert("u send a message");
    var user = document.getElementById("user").value;
    var message = document.getElementById("message").value;
    jQuery.ajax({
                url: "",
                type: "POST",
                data: JSON.stringify({
                'user' : nombre,
                'message': contenido
                }),});
  });
});