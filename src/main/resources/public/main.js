
function fetchMap(x, y, radius) {
	fetch("graphql", {
		method: "POST", 
		body: `{getAreaByLocationAndRadius(x: ${x}, y: ${y}, radius: ${radius})}`})
	.then(data => data.json())
	.then(response => {
			if (response.dataPresent) {
				document.getElementById("map").innerText = response.data.getAreaByLocationAndRadius
			} else {
				console.error(response.errors)
			}	
		}
	)
}

fetchMap(0, 0, 10)