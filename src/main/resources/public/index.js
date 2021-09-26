window.onload=function(){

    var url=window.location.href+'results';
    var mb = document.getElementById("myform");
    getData();
    mb.addEventListener("submit", (e)=>{
        e.preventDefault();
        const formData= new FormData(e.currentTarget);
        postData(formData);
        });
    function getData(){
        $.ajax({
            url: "/api/getmessages",
            type: 'GET',
            success:renderList
                });
    }

    function renderList(data) {
        $("#demo").empty();
        str ="";
        num =0;
        data.map(message=>{
        num+=1
        str+="<tr>" + "<th>" + num + "</th>" +
            "<th>" + message.user + "</th>" +
             "<th>" + message.message + "</th>" +
            "<th>" + message.date + "</th> " +
            "</tr>"
        });
        document.getElementById("demo").innerHTML = str;
        console.log("ok");
    }

async function postData(formData){
       const nuser = formData.get("data-name");
       const newMessage = formData.get("data-message");
                jQuery.ajax({
                        url: "/api/addmessage",
                        type: "POST",
                        data: JSON.stringify({
                                    'user' : nuser,
                                    'message': newMessage
                                    })
                    }).then( e => {
                        getData();
                    });
                return true;
            };

}