if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

var ws = new WebSocket("ws://192.168.1.8:9000/wsex");
ws.onopen = function () {
    ws.send("New visitor has joined");
};

ws.onmessage = function (evt) {
    var received_msg = evt.data;
    var mm = $("#messages").val();
    $("#messages").val(mm+"\n"+received_msg);
};
ws.onclose = function () {
    // websocket is closed.
    console.log("Connection is closed...");
};

function submitMessage() {
    var m = $("#newMessage").val();
    $("#newMessage").val("");
    ws.send(m);
}