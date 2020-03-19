
var x = 0
var y = 0
var radius = 10
var zoom = 1

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

document.onkeydown = function(event) {
	
	switch (event.code) {
	case "KeyW": x -= 1; break;
	case "KeyA": y -= 1; break;
	case "KeyS": x += 1; break;
	case "KeyD": y += 1; break;
	case "KeyZ": zoom *= 1.1; break;
	case "KeyX": zoom /= 1.1; break;
	case "KeyC": x = 0, y = 0, zoom = 1; break;
	}
	
	updateMap()
}