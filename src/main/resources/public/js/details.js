
var details = {
	fetch: function(x, y) {
		fetch("graphql", {
			method: "POST", 
			body: `{getTile(x: ${JSON.stringify(x)}, y:${JSON.stringify(y)}) {name, description}}`})
		.then(data => data.json())
		.then(response => {
				if (response.dataPresent) {
					let tile = response.data.getTile
					document.getElementById("tile_name").innerText = `${tile.name}:`
					document.getElementById("tile_description").innerText = tile.description
				} else {
					console.error(response.errors)
				}
			}
		).catch(console.error)
	}
}