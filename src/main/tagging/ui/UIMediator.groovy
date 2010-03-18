package tagging.ui
import tagging.*
import tagging.util.*
import org.apache.commons.collections.MultiMap
import org.apache.commons.collections.MultiHashMap
class UIMediator{
    def init(FunctionMatrix fm){
        fm.getAllFunctions('quickTagProvides').each{
            it.getQuickTagProvides().each{
                provide->
                this.registorQuickTagProvide(provide)
            }
        }
        fm.getAllFunctions('quickTagRequests').each{
            it.getQuickTagRequests().each{
                request->
                this.registorQuickTagRequest(request)
            }
        }
    }
    def quickTagActions=[:]
    def quickTagRequests=[:]
    @Delegate
    MagicTextRegistor magicText=new MagicTextRegistor()
    @Delegate
    SearchViewRegistor searchViewRegistor= new SearchViewRegistor()
    void registorQuickTagProvide(QuickTagProvide quickTagProvide){
        this.quickTagActions[quickTagProvide.name]=quickTagProvide.action
    }
    QuickTagAction getQuickTagProvide(String name){
        return this.quickTagActions[name]
    }
    void registorQuickTagRequest(QuickTagRequest quickTagRequest){
        this.quickTagRequests[quickTagRequest.type]=quickTagRequest.tagNames
    }
    List<String> getQuickTagRequests(String type){
        return this.quickTagRequests[name]
    }
    def viewElementIds=[:]
    String getViewElementId(obj){
        def id=viewElementIds.get(obj,null)
        if(id)
        return id
        else{
            id=UUID.randomUUID().toString()
            viewElementIds[obj]=id
            return id
        }
    }
    Object getViewElement(String id){
    	return viewElementIds.find{
            it.value==id
    	}?.key
    }
}
class QuickTagProvide{
    def name
    def action
}
class QuickTagRequest {
    def type
    def tagNames
}
class Action{
    Closure getAppear
    Closure action
}
class QuickTagAction extends Action{
}

class ActionAppear{
    String icon
    String text
    Boolean enable
    Boolean display
}
class QuickTagActionAppear extends ActionAppear{
}

class MagicTextAction extends Action{
}
class MagicTextActionAppear extends ActionAppear{
}

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
class SearchViewRegistor{
    private MultiMap items = new MultiHashMap() 
    Set<String> getSearchViewGroups(){
        return items.keySet()
    }
    List getSearchViews(String group){
        return items.get(group).sort{ a,b->
            a.order-b.order
        }.collect{ it.searchView }
    }
    void registorSearchView(SearchViewItem item){
        items.put(item.group,item)
    }
}
class SearchViewItem{
    SearchView searchView
    int order
    String group
}