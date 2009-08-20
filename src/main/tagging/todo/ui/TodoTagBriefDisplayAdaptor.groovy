package tagging.todo.ui

import tagging.*
import tagging.ui.*
import tagging.todo.*
import net.miginfocom.swing.MigLayout
import java.awt.SystemColor
import com.lavantech.gui.comp.*;

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
        return sb.panel(layout:new MigLayout()){
            label(icon:IconManager.getIcon('todo'),'Todo - ')
			checkBox(id:'doneCheckBox',selected:value.done,text:'Done',mouseClicked:{value.done=doneCheckBox.selected})
			checkBox(id:'deadLineCheckBox',selected:(value.deadline!=null),text:'DeadLine',mouseClicked:{
            	if(deadLineCheckBox.selected){
            		picker1.enabled=true
            	}else{
            		picker1.enabled=false
            		value.deadline=null
            	}
            })
			def cal= new GregorianCalendar()
            if(value.deadline!=null){
            	cal.time=value.deadline
            }
			def picker1 = new DateTimePicker(cal, "dd-MMM-yyyy HH:mm:ss z")
            picker1.actionPerformed={
            	event->
            	if(deadLineCheckBox.selected) 
            		value.deadline=event.source.calendar.time
            }
			picker1.enabled=deadLineCheckBox.selected
            widget(picker1,id:'picker1')
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
		mr.registorSearchView(new SearchViewItem(order:10,group:'Default',searchView:new  NotFinishedTodoSearchView()
                ))
		mr.registorSearchView(new SearchViewItem(order:100,group:'Category',searchView:new  TodoSearchView()
                ))
				
    }
}