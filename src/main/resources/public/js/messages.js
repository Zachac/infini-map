

function generateSecret() {
	let secretBytes = window.crypto.getRandomValues(new Uint8Array(32));
	return btoa(String.fromCharCode.apply(null, secretBytes));
}

async function calculateId(secret) {
	let encoder = new TextEncoder();
	let domainSecret = `${window.location.host}:${secret}`;

	let effectiveSecret = await crypto.subtle.digest('SHA-256', encoder.encode(domainSecret));
	let userId = await crypto.subtle.digest('SHA-256', encoder.encode(effectiveSecret));
	
	return {
		secret: btoa(String.fromCharCode.apply(null, new Uint8Array(effectiveSecret))),
		userId: btoa(String.fromCharCode.apply(null, new Uint8Array(userId)))
	}
}

var secret = localStorage.getItem("secret");
if (! secret) {
	secret = generateSecret();
	localStorage.setItem("secret", secret)
}

var id = null;

window.addEventListener("load", (e) => {
	calculateId(secret).then(id => {
		window.id = id;
	}).catch(console.error)
	setInterval(() => messages.getMessages(), 500)
});
	
messages = {
	getMessages: function (channel="general") {
		let tsFieldName = `messages.lastRead.${channel}`;
		let effectiveTs = Number(localStorage.getItem(tsFieldName)) || 0
		
		fetch("graphql", {
			method: "POST", 
			body: `{getMessages(channel:"${channel}",effectiveTs:${effectiveTs}){effectiveTs, value {userId, name, message}}}`})
		.then(data => data.json())
		.then(response => {
				if (response.dataPresent) {
					let messages = response.data.getMessages;
					
					effectiveTs = Number(localStorage.getItem(tsFieldName)) || effectiveTs
					
					for (let i = messages.length - 1; i >= 0; i--) {
						let m = messages[i];
						if (effectiveTs < m.effectiveTs) {
							log(`${m.value.name}: ${m.value.message}`, new Date(m.effectiveTs))
							effectiveTs = m.effectiveTs
						}
					}
					
					if (messages.length > 0) {
						localStorage.setItem(tsFieldName, effectiveTs)
					}
				} else {
					console.error(response.errors)
				}
			}
		).catch(console.error)
	},
	
	sendMessage: function(text, channel="general") {
		fetch("graphql", {
			method: "POST", 
			body: `{sendMessage(channel: ${JSON.stringify(channel)}, userId: ${JSON.stringify(text)}, name: ${JSON.stringify(player.name)},  message: ${JSON.stringify(text)})}`})
		.then(data => data.json())
		.then(response => {
				if (response.errors.length) {
					console.error(response.errors)
				} else {
					messages.getMessages()
				}
			}
		).catch(console.error)
	}
}

