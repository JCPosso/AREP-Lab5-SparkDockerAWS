window.onload=function(){
    var url=window.location.href+'results';
    var mb = document.getElementById("myform");
    mb.addEventListener("submit", (e)=>{
        e.preventDefault();
        const formData= new FormData(e.currentTarget)
        postData(formData).then (success => {
            getData(success);
            console.log("yuju!");
        })
    });
};
function getData(success){
    var num=1;
    var url=window.location.href+'results';
        $.get(url).then(res=>{
            console.log(res.data)
            res.data.map(message=>{
                console.log(message)
            })
        })
    let text = ''
    for (let x in obj) {
      var v1= x;
      text += '<tr><th scope="row">' + x + '</th>';
      for ( z in  obj[x] ){
                       text += '<th scope="row">' + obj[x][z] + '</th>';
      }
      text+="</tr>"
    }
    text += ""
    document.getElementById("demo").innerHTML = text;
}
async function postData(formData){
    var url=window.location.href+'results';
    const newMessage = formData.get("data-message");
    console.log(newMessage);
    // post ajax
    $.post(url, {
          message: newMessage,
        },
        function(informacion, estado){
            alert("Información: " + informacion + "\nEstado: " + estado);
        });
    return true;
};