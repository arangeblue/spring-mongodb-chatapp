const eventSource = new EventSource("http://localhost:8080/sender/wi/receiver/kim");

eventSource.onmessage = (event) => {
    console.log(1, event);
    const data = JSON.parse(event.data);
    console.log(2, data);
    initMessage(data);
}


function getSendMsgBox(msg, time) {
    return `<div class="sent_msg">
    <p>${msg}</p>
    <span class="time_date"> ${time}</span>
    </div>`;
}

function initMessage(data) {
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";


    let y_str = data.createAt.substring(0, 4);
    let m_str = data.createAt.substring(5, 7);
    let d_str = data.createAt.substring(8, 10);
    let hh_str = data.createAt.substring(11, 13);
    let mm_str = data.createAt.substring(14, 16);



    let modifiedDate = hh_str + ":" + mm_str + " | " + d_str + "일 " + m_str + "월 " + y_str + "년";

    chatOutgoingBox.innerHTML = getSendMsgBox(data.msg, modifiedDate);


    chatBox.append(chatOutgoingBox);
    msgInput.value = "";


}




function addMessage() {
    let chatBox = document.querySelector("#chat-box");
    let msgInput = document.querySelector("#chat-outgoing-msg");

    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";

    let date = new Date();
    let now = date.getHours() + ":" + date.getMinutes() + " | " + date.getDate() + "일 " + date.getMonth() + "월 " + date.getFullYear() + "년";


    let chat = {
        sender: "wi",
        receiver: "kim",
        msg: msgInput.value
    };

    fetch("http://localhost:8080/chat", {
        method: "post", // http post method 
        body: JSON.stringify(chat), // JS -> JSON
    })

    chatOutgoingBox.innerHTML = getSendMsgBox(msgInput.value, now);
    chatBox.append(chatOutgoingBox);
    msgInput.value = "";
}


document.querySelector("#chat-outgoing-button").addEventListener("click", () => {
    

    addMessage();
  });

document
  .querySelector("#chat-outgoing-msg")
  .addEventListener("keydown", (e) => {
    if (e.keyCode === 13) {
      addMessage();
    }
  });
