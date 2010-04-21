package tagging.todo.ui

import tagging.*
import tagging.ui.*
import tagging.todo.*
import net.miginfocom.swing.MigLayout
import java.awt.SystemColor
import com.michaelbaranov.microba.calendar.DatePicker


class TodoTagBriefDisplay extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
//            etchedBorder(parent:true)
            label(text:value.name)
        }
    }
}
class TodoTagDetailDisplay extends DefaultDetailDisplayAdaptor{
    def xb=new groovy.swing.SwingXBuilder()
    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            label(icon:IconManager.getIcon('todo'),'Todo - ')
            checkBox(id:'doneCheckBox',selected:value.done,text:'Done',mouseClicked:{value.done=doneCheckBox.selected})
            checkBox(id:'deadLineCheckBox',selected:(value.deadline!=null),text:'DeadLine',mouseClicked:{
                    if(deadLineCheckBox.selected){
            		dataPicker.enabled=true
                    }else{
            		dataPicker.enabled=false
            		value.deadline=null
                    }
                })
            //def picker1 = new DatePicker(value.deadline, new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm"))
            def dataPicker=xb.datePicker(date:value.deadline?:new Date(),id:'dataPicker')
            dataPicker.actionPerformed={
            	event->
            	if(deadLineCheckBox.selected) 
                value.deadline=event.source.date
            }
            dataPicker.enabled=deadLineCheckBox.selected
            widget(dataPicker,id:'dataPicker')
        }
    }
}


class NotFinishedTodoSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    def icon='unfinishedtodo'
}
class TodoSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    def icon='todo'
}
class TodoSearchViewProvides{
    def searchViewItems=[
        new SearchViewItem(order:10,group:'Default',searchView:new  NotFinishedTodoSearchView()),
        new SearchViewItem(order:100,group:'Category',searchView:new  TodoSearchView())
    ]
}
class TodoMagicTextProvides{
    def magicTextProvides=[
        new MagicTextProvide(
            name:'todo',
            action:new MagicTextAction(
                getAppear:{text->
                    return new ActionAppear(icon:'newTodo',enable:true)
                },
                action:{text->
                    TodoTag.newTodoTag(text)
                }
            )
        )
    ]
}