application {
	title="LiveTagged"
	startupGroups=["LiveTagged"]
	autoShutdown=true
}
mvcGroups {
	LiveTagged {
		model="LiveTaggedModel"
		controller="LiveTaggedController"
		view="LiveTaggedView"
	}
}
