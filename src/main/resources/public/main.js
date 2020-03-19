
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

fetchMap(0, 0, 10, 1)