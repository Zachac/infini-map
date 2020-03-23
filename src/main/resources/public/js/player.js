

window.addEventListener("load", (e) => {
	let name_field = document.getElementById("name_field")
	name_field.onkeydown = function (evt) {
		player.setName(evt.target.value)
	}
	
	name_field.value = player.name
})

var player = {
	name: "NN",
	x: 0,
	y: 0,
	radius: 10,
	zoom: 1,
}

// create setters
Object.keys(player).forEach((k) => {
	let storageField = `player.${k}`;
	let storedValue = localStorage.getItem(storageField);
	
	if (storedValue) {
		let defaultValue = player[k];
		if (typeof defaultValue == "number") {
			player[k] = Number(storedValue)
		} else {
			player[k] = storedValue
		}
	}
	
	let setter = `set${k.charAt(0).toUpperCase() + k.slice(1)}`;
	
	player[setter] = function(value) {
		localStorage.setItem(storageField, value)
		this[k] = value
	}
});



