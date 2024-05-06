class Message {
    constructor(pieceName, x, y) {
        this.pieceName = pieceName;
        this.x = x;
        this.y = y;
    }

    getPieceName() {
        return this.pieceName;
    }

    setPieceName(pieceName) {
        this.pieceName = pieceName;
    }

    getX() {
        return this.x;
    }

    setX(x) {
        this.x = x;
    }

    getY() {
        return this.y;
    }

    setY(y) {
        this.y = y;
    }
}

const url = "ws://localhost:8080/ws";
var topic = "/topic/greetings";
const client = new StompJs.Client({
    brokerURL: url
});
var buttonConnect;
var buttonDisconnect;
var buttonSend;
var conversationDisplay;
var greetings;
var formInput;
var topicInput;
var messageInput;
var gameID;
const userId = crypto.randomUUID();

client.onConnect = (frame) => {
    console.log('aaaaaaaaaaaaaaaaaaa');

    setConnected(true);
    client.publish({
        destination: '/app/start',
        body: userId,
    });

    console.log('UUID published: ' + userId);

    const userSubscription = client.subscribe('/topic/user/' + userId, (message) => {
        console.log('Received message from user/' + userId + ': ' + message.body);

        if (message.body.includes("/topic/game/")) {
            const gameId = message.body.split('/').pop();
            gameID=gameId;

            console.log('Game ID: ' + gameId);
            console.log('/topic/game/' + gameId);

            const gameSubscription = client.subscribe('/topic/game/' + gameId, (gameMessage) => {
                console.log('Received message from game/' + gameId + ': ' + gameMessage.body);
                showGreeting(JSON.stringify( gameMessage.body));
            });
        }

        showGreeting(message.body);
    });
};

client.onDisconnect = (frame) => {
    client.deactivate();

    disconnect();
};

client.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

client.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    buttonConnect.disabled = connected;
    buttonDisconnect.disabled = !connected;
    buttonSend.disabled = !connected;
    if (connected) {
        conversationDisplay.style.display = "block";
    } else {
        conversationDisplay.style.display = "none";
    }
    greetings.innerHTML = "";
}

function connect() {
    client.activate();
    console.log('Connected');
}

function disconnect() {
    client.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    console.log("hello");
        const pieceName = messageInput.value;
        const x = parseInt(document.getElementById("int1").value);
        const y = parseInt(document.getElementById("int2").value);

        client.publish({
            destination: "/app/makeMove",
            body: JSON.stringify(new Message(pieceName, x, y)),
        });
}


function showGreeting(message) {
    greetings.innerHTML = "";
    greetings.innerHTML += "<tr><td>" + message + "</td></tr>";
}

document.addEventListener("DOMContentLoaded", function () {
    buttonConnect = document.getElementById("connect");
    buttonDisconnect = document.getElementById("disconnect");

    buttonSend = document.getElementById("send");
    conversationDisplay = document.getElementById("conversation");
    greetings = document.getElementById("greetings");
    formInput = document.getElementById("form");
    topicInput = document.getElementById("topic");
    messageInput = document.getElementById("message");

    buttonConnect.addEventListener("click", (e) => {
        connect();
        e.preventDefault();
    });
    buttonDisconnect.addEventListener("click", (e) => {
        disconnect();
        e.preventDefault();
    });
    buttonSend.addEventListener("click", (e) => {
        sendMessage();
        e.preventDefault();
    });
    formInput.addEventListener("submit", (e) => e.preventDefault());
    setConnected(false);
});
