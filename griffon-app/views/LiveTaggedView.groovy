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
import tagging.*
def Bos=MockData.bos
def aS=AdaptorServiceFactory.getAdaptorService()
def searchViews=MockData.searchViews

application(title:'LiveTagged',  size:[320,480], location:[50,50], pack:true, locationByPlatform:true,layout:new MigLayout()) {
    splitPane(
    rightComponent:
        splitPane(
        leftComponent:
        scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 500px::, h 600px::'){
            panel(id:'briefPanel',layout:new MigLayout()){
                def itemGroup=new SingleSelectedGroup(
                		selectionChanged:{controller.selectBo(itemGroup.selectedValue)}
                )
                Bos.each{
                    def w=widget(constraints:'wrap,w :500px:',aS.getAdaptorClass(it.type,'briefDisplay').newInstance(value:it,group:itemGroup).getComponent())
                    itemGroup.addItem(w,it)
                    w.mouseClicked={e->itemGroup.select(e.source)}
                }
            }
        },
        rightComponent:
            scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED,constraints:'w 200px::'){
                panel(id:'detailPanel'){
                    widget(constraints:'w :200px:', aS.getAdaptorClass(Bos[1].type,'detailDisplay').newInstance(value:Bos[1]).getComponent())
                }
            }
        ,resizeWeight:0.5f,oneTouchExpandable:true),
    leftComponent:
        scrollPane(id:'searchViewPanel',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 150px::, h 600px::'){
            panel(layout:new MigLayout()){
                viewGroup=new SingleSelectedGroup(selectionChanged:{controller.selectSearchView(viewGroup.selectedValue)})
                searchViews.each{
                    def w=widget(constraints:'wrap,w :150px:',aS.getAdaptorClass(it.type,'briefDisplay').newInstance(value:it,group:viewGroup).getComponent())
                    viewGroup.addItem(w,it)
                    w.mouseClicked={e->viewGroup.select(e.source)}
                }
            }
        }
    ,resizeWeight:0.25f,oneTouchExpandable:true)

}
class SingleSelectedGroup{
    def items=[]
    def values=[]
    def status=[:]
    def selected=null
    def selectedValue=null
    def selectionChanged={}
    def addItem(item,value){
        this.items<<item
        this.values<<value
    }
    def select(item){
        assert (item in items)
        if(selected){
            //selected.onUnselected()
        }
        selected=item
        selectedValue=values[items.indexOf(item)]
        this.selectionChanged()
        //item.slected()
    }
}

