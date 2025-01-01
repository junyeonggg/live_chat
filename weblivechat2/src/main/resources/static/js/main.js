let ws;

function enter() {
	const target = document.querySelector("#divMsg");
	const divHidden = document.querySelector("#hidden");
	divHidden.removeAttribute("hidden")
	ws = new WebSocket("ws://localhost:8080/chat")
	ws.onopen = () => ws.send("게스트 님이 입장하였습니다.");
	ws.onmessage = (event) => {
		target.innerHTML += `<p>${event.data}<p>`
	}
	ws.onclose = ()=> {
		window.alert("연결이 종료되었습니다.")
		window.location.reload();
	}
}
function exit() {
	ws.send("게스트 님이 퇴장하였습니다.");
	ws.close()
}
function sendMsg() {
	const message = document.querySelector("#inputMsg");
	if(message != null){
		ws.send(message.value)
		message.value = "";
	}
}