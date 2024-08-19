/*!
* Start Bootstrap - Blog Post v5.0.9 (https://startbootstrap.com/template/blog-post)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-blog-post/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

var stompClient = null;

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (messageOutput) {
            showMessage(JSON.parse(messageOutput.body));
        });
    });
}

function sendMessage() {
    var messageContent = document.getElementById("chatInput").value;
    if(messageContent && stompClient) {
        stompClient.send("/app/sendMessage", {}, JSON.stringify({'content': messageContent, 'sender': 'User'}));
        document.getElementById("chatInput").value = '';
    }
}

function showMessage(message) {
    var chatArea = document.getElementById("chatArea");
    var messageElement = document.createElement('div');
    messageElement.className = 'chat-message';
    messageElement.textContent = message.sender + ": " + message.content;
    chatArea.appendChild(messageElement);
    chatArea.scrollTop = chatArea.scrollHeight;
}

window.onload = connect;
