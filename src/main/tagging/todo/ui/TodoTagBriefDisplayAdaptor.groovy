package tagging.todo.ui

import tagging.*
import tagging.ui.*
import tagging.todo.*
import net.miginfocom.swing.MigLayout
import java.awt.SystemColor

class TodoTagBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:value.name)
        }
    }
}
class TodoTagDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel{
            label("${value.name}")
        }
    }
}

class TodoTagMeta{
    static void provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('tag.todo','briefDisplay',TodoTagBriefDisplayAdaptor.class)
        aS.registerAdaptor('tag.todo','detailDisplay',TodoTagDetailDisplayAdaptor.class)
		aS.registerAdaptor('searchView.todo','briefDisplay',new SearchViewBriefDisplayAdaptor(icon:'todo'))
		aS.registerAdaptor('searchView.unfinishedTodo','briefDisplay',new SearchViewBriefDisplayAdaptor(icon:'unfinishedtodo'))
		
		def mr=ServiceFactory.getService(UIMediator.class)
		mr.registorMagicTextProvide('todo',new MagicTextAction(
			getAppear:{text->
				return new ActionAppear(icon:'newTodo',enable:true)
			},
			action:{text->
				TodoTag.newTodoTag(text)
			}
		))
    }
}