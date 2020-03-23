
window.addEventListener("load", (e) => {
	document.onkeydown = event => {
		if (event.target.type != "text") {
			processKeyEvent(event.code);
		}
	}
})

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
	).catch(console.error)
}

function updateMap() {
	fetchMap(player.x, player.y, player.radius, player.zoom)	
}

updateMap()

function processKeyEvent(code) {
	switch (code) {
	case "KeyW": player.setX(player.x - 1); break;
	case "KeyA": player.setY(player.y - 1); break;
	case "KeyS": player.setX(player.x + 1); break;
	case "KeyD": player.setY(player.y + 1); break;
	case "Minus":
	case "KeyZ": player.setZoom(Math.min(player.zoom * 1.1, 10)); break;
	case "Equal":
	case "KeyX": player.setZoom(Math.max(player.zoom / 1.1, 1)); break;
	case "KeyC": player.setX(0), player.setY(0), player.setZoom(1), player.setRadius(10); break;
	default: return;
	}
	updateMap()
}