function addBasket(id) {
    var data = {id: id, command: "basketAdd"};

    $.ajax({
        type: 'POST',
        data: data,
        url: '/FinalProject/basket',
        success: [function (serverData) {
            console.log(serverData)
            var element = document.getElementById("ajaxInfo");
            element.className = "";
            element.classList.add("alert");
            if (serverData.successMessage) {
                element.classList.add("alert-success");
                element.html(serverData.successMessage);
            }
            if (serverData.errorMessage) {
                element.classList.add("alert-danger");
                element.html(serverData.errorMessage);
            }
            element.classList.add("w-50");
            element.classList.add("mx-auto");
            element.classList.add("my-auto");
            element.classList.add("text-center");
        }],
        error: [function (serverData) {
            console.log(serverData)
        }]
    });
}

function validateForm(){
    var temp = document.getElementById("searchQuery").value;
    var reg = /^[0-9a-zA-Z]+$/;
    if(temp.match(reg)){
        alert("Yeah, bitch");
    }
    else alert("No, bitch");
}

