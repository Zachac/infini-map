

window.addEventListener("load", (e) => {
	let name_field = document.getElementById("name_field")
	name_field.onkeydown = function (evt) {
		player.setName(evt.target.value)
	}
	
	name_field.value = player.name
})

function setter(fieldName) {
	let storageField = `player.${fieldName}`;
	return function(value) {
		localStorage.setItem(storageField, value)
		this[fieldName] = value
	}
}

function get(fieldName, defaultValue) {
	return localStorage.getItem(`player.${fieldName}`) || defaultValue
}

var player = {
	name: get("name", "NN"),
	setName: setter("name"),
	
	x: Number(get("x", 0)),
	setX: setter("x"),
	
	y: Number(get("y", 0)),
	setY: setter("y"),
	
	zoom: Number(get("zoom", 1)),
	setZoom: setter("zoom"),
	
	radius: Number(get("radius", 10)),
	setRadius: setter("radius"),
}


