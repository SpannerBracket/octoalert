definition(
    name: "Octoalert",
    namespace: "SpannerBracket",
    author: "Ben Langridge",
    description: "Sound the Octoalert!",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)


preferences {
	section("Select your button"){
		input "myButton", "capability.button", title: "Choose Button", required: false, multiple: true
	}
	section("Send this command:"){
		input "command", "text", title: "Command to send", required: true
	}
	section("Server address and port number"){
		input "server", "text", title: "Server IP", description: "Server IP", required: true
		input "port", "number", title: "Port", description: "Port Number", required: true
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribeToEvents()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribeToEvents()
}

def subscribeToEvents() {
	subscribe(myButton, "button.pushed", eventHandler)
}

def eventHandler(evt) {
	sendHttp()
}

def sendHttp() {
def ip = "${settings.server}:${settings.port}"
def deviceNetworkId = "1234"
sendHubCommand(new physicalgraph.device.HubAction("""GET /?${settings.params} HTTP/1.1\r\nHOST: $ip\r\n\r\n""", physicalgraph.device.Protocol.LAN, "${deviceNetworkId}"))
}