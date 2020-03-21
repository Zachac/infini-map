let log_elem = null;

window.addEventListener("load", (e) => {
	log_elem = document.getElementById("log")	
})

function log(value, date=new Date()) {
	let formatted_date = "";
	if (date) {
		formatted_date = `${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")} `
	}
	
	log_elem.innerText += `${formatted_date}${value}\n`
	log_elem.scrollTop = log_elem.scrollHeight
}