let websocket;

window.addEventListener('load',initPage,false);


function initPage(){
    console.log("at least this works");
    openSocket();
    let sendbuttons = Array.from(document.querySelectorAll('button[type="submit"]'));
    sendbuttons.forEach(element => element.addEventListener("click",send,false));
}


function openSocket(){
    websocket = new WebSocket("ws://localhost:8080/forum");
    websocket.onopen = function(event){
        console.log('connection opened');
        // writeResponse('Connection opened');
    };
    websocket.onmessage = function(event) {
        writeResponse(event.data);
    };
    websocket.onclose = function(event) {
        console.log('connection closed')
        // writeResponse('Connection closed');
    };
}

function send(event){
    let text = "test";
    websocket.send(text);
}


function writeResponse(text){
    let topics = Array.from(document.querySelectorAll(".topic"));
    let p = document.createElement('p');
    p.innerText = text;
    topics.forEach(element => element.insertAfter(p,element.querySelector('h5')));
}