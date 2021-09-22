window.onload=function(){
    var url=window.location.href+'results';
    var mb = document.getElementById("myform");
    mb.addEventListener("submit", (e)=>{
        e.preventDefault();
        const formData= new FormData(e.currentTarget);
        postData(formData);
        console.log("yuju!");
        });
    };
function getData(success){
    //poner los datos en la tabla
}
async function postData(formData){
    const nuser = formData.get("data-name");
    const newMessage = formData.get("data-message");
    // post ajax
    jQuery.ajax({
            url: "/api/addmessage",
            type: "POST",
            data: JSON.stringify({
                        'user' : nuser,
                        'message': newMessage
                        })
        });
    return true;
};