package tagging.ui

class MagicTextRegistor{
	def entries=[:]
	def registor(String name,MagicTextEntry entry){
		this.entries[name]=entry
	}
}
class MagicTextEntry{
	String icon
	Closure enableFilter
	Closure activity
}