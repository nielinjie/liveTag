import net.miginfocom.swing.MigLayout
import static javax.swing.ScrollPaneConstants.*
import javax.swing.event.*
import javax.swing.*
import java.awt.SystemColor
import java.awt.Color
import javax.swing.table.*
import ca.odell.glazedlists.gui.*
import ca.odell.glazedlists.swing.*
import ca.odell.glazedlists.*
import groovy.swing.SwingBuilder
import tag.*
def Bos=new BasicEventList()
Bos.addAll([new TodoTag(name:'a todo'),
new TodoTag(name:'second todo'),
new TodoTag(name:'3rd todo'),
new TodoTag(name:'4th todo')])
def searchViews=[new SearchView(name:'All',description:'All Bos',condition:{true},sortComparator:{a,b->0})]

application(title:'LiveTagged',  size:[320,480], location:[50,50], pack:true, locationByPlatform:true,layout:new MigLayout()) {
    splitPane(
    rightComponent:
        splitPane(
        leftComponent:
        scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_ALWAYS ,constraints:'w 500px::, h 600px::'){
            panel(layout:new MigLayout()){
                itemGroup=new SingleSelectedGroup()
                Bos.each{
                    def w=widget(constraints:'wrap,w :500px:',new DefaultBriefDisplayAdaptor(value:it,group:itemGroup).getComponent())
                    itemGroup.addItem(w)
                    w.mouseClicked={e->itemGroup.select(e.source)}
                }
            }
        },
        rightComponent:
            scrollPane(id:'detailPane',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_ALWAYS,constraints:'w 200px::'){
                widget(constraints:'w :200px:', new DefaultDetailDisplayAdaptor(value:Bos[1]).getComponent())
            }
        ,resizeWeight:0.5f,oneTouchExpandable:true),
    leftComponent:
        scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_ALWAYS ,constraints:'w 150px::, h 600px::'){
            panel(layout:new MigLayout()){
                viewGroup=new SingleSelectedGroup()
                searchViews.each{
                    def w=widget(constraints:'wrap,w :150px:',new DefaultBriefDisplayAdaptor(value:it,group:viewGroup).getComponent())
                    viewGroup.addItem(w)
                    w.mouseClicked={e->viewGroup.select(e.source)}
                }
            }
        }
    ,resizeWeight:0.5f,oneTouchExpandable:true)

}
class SingleSelectedGroup{
    def items=[]
    def status=[:]
    def selected=null
    def addItem(item){
        this.items<<item
    }
    def select(item){
        if(selected){
            selected.onUnselected()
        }
        selected=item
        item.onSelected()
    }
}

