package tagging.ui
import tagging.*
class UIMediator{
	def quickTagActions=[:]
	def quickTagRequests=[:]
}
class QuickTagAction{
	Closure getAppear
	Closure action
}
class QuickTagActionAppear{
	String icon
	String text
	Boolean enable
	Boolean display
}