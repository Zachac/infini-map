
var x = Number(localStorage.getItem('x')) || 0
var y = Number(localStorage.getItem('y')) || 0
var radius = Number(localStorage.getItem('radius')) || 10
var zoom = Number(localStorage.getItem('zoom')) || 1

function fetchMap(x, y, radius, zoom) {
	fetch("graphql", {
		method: "POST", 
		body: `{display(x: ${x}, y: ${y}, radius: ${radius}, zoom: ${zoom})}`})
	.then(data => data.json())
	.then(response => {
			if (response.dataPresent) {
				document.getElementById("map").innerText = response.data.display
			} else {
				console.error(response.errors)
			}	
		}
	)
}

function updateMap() {
	fetchMap(x, y, radius, zoom)	
}

updateMap()

function save(...args) {
	args.forEach(variable => localStorage.setItem(variable, window[variable]))
}

function processKeyEvent(code) {
	switch (code) {
	case "KeyW": x -= 1; save("x"); break;
	case "KeyA": y -= 1; save("y"); break;
	case "KeyS": x += 1; save("x"); break;
	case "KeyD": y += 1; save("y"); break;
	case "Minus":
	case "KeyZ": zoom = Math.min(zoom * 1.1, 10); save("zoom"); break;
	case "Equal":
	case "KeyX": zoom = Math.max(zoom / 1.1, 1); save("zoom"); break;
	case "KeyC": x = 0, y = 0, zoom = 1; save("x", "y", "zoom"); break;
	default: return;
	}
	updateMap()
}

var log_elem = null;

function registerEventListeners() {
	document.onkeydown = event => processKeyEvent(event.code);
	
	document.getElementById("command_input").onkeydown = function (evt) {
		if (evt.code == "Enter") {
			parse(evt.target.value)
			evt.target.value =  "";
		}
		
		evt.cancelBubble = true  
	}
	
	log_elem = document.getElementById("log")
}

function log(value) {
	let date = new Date();
	log_elem.innerText += `${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")} ${value}\n`
	log_elem.scrollTop = log_elem.scrollHeight;
}
