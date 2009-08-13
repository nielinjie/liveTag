package tagging.ui
import tagging.*
class UIMediator{
	def quickTagActions=[:]
	def quickTagRequests=[:]
	@Delegate
	MagicTextRegistor magicText=new MagicTextRegistor()
	void registorQuickTagProvide(String name,QuickTagAction action){
		this.quickTagActions[name]=action
	}
	QuickTagAction getQuickTagProvide(String name){
		return this.quickTagActions[name]
	}
	void registorQuickTagRequest(String type,String tagNames){
		this.quickTagRequests[type]=tagNames
	}
	List<String> getQuickTagRequests(String type){
		return this.quickTagRequests[name]
	}
}
class Action{
	Closure getAppear
	Closure action
}
class QuickTagAction extends Action{}

class ActionAppear{
	String icon
	String text
	Boolean enable
	Boolean display
}
class QuickTagActionAppear extends ActionAppear{}

class MagicTextAction extends Action{}
class MagicTextActionAppear extends ActionAppear{}
class MagicTextRegistor{
    private entries=[:]
    void registorMagicTextProvide(String name,MagicTextAction entry){
        this.entries[name]=entry
    }
    MagicTextAction getMagicTextProvide(String name){
    	return this.entries[name]
    }
    Map<String,MagicTextAction> getMagicTextProvides(){
    	return this.entries
    }
}