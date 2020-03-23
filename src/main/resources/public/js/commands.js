
window.addEventListener("load", (e) => {
	document.getElementById("command_input").onkeydown = function (evt) {
		if (evt.code == "Enter") {
			parse(evt.target.value)
			evt.target.value =  "";
		}
	}
})

function say(text) {
	messages.sendMessage(text);
}

function parse(text) {
	text = text.trim();
	
	let c = text.charAt(0)
	switch(c) {
	case '"': say(text.substr(1)); return;
	case "": log("", null); return;
	}
	
	let words = text.split(/\s+/);
	let command = words.shift();
	
	switch(command) {
	case "s":
	case "say": say(words.join(" ")); return;
	}
	
	log(text, null)
	log(`Unkown command: ${command}`, null)
}
