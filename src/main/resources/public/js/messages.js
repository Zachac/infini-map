
window.addEventListener("load", (e) => {
	setInterval(() => messages.getMessages(), 500)	
});
	
messages = {
	getMessages: function (channel="general") {
		let tsFieldName = `messages.lastRead.${channel}`;
		let effectiveTs = Number(localStorage.getItem(tsFieldName)) || 0
		
		fetch("graphql", {
			method: "POST", 
			body: `{getMessages(channel:"${channel}",effectiveTs:${effectiveTs}){message, effectiveTs}}`})
		.then(data => data.json())
		.then(response => {
				if (response.dataPresent) {
					let messages = response.data.getMessages;
					
					effectiveTs = Number(localStorage.getItem(tsFieldName)) || effectiveTs
					
					for (let i = messages.length - 1; i >= 0; i--) {
						let m = messages[i];
						if (effectiveTs < m.effectiveTs) {
							log(m.message, new Date(m.effectiveTs))
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
			body: `{sendMessage(channel: ${JSON.stringify(channel)}, message: ${JSON.stringify(text)})}`})
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

