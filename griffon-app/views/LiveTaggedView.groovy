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
    splitPane(constraints:'h 700px::, w 1200px::',
    leftComponent:
            scrollPane(id:'searchViewPanel',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 200px::, h 600px::'){
                panel(layout:new MigLayout()){
                    viewGroup=new SingleSelectedGroup(selectionChanged:{controller.selectSearchView(viewGroup.selectedValue)})
                    searchViews.each{
                    	def a=aS.getAdaptor(it,'briefDisplay')
						a.group=viewGroup
                        def w=widget(constraints:'wrap,w :150px:',a.getComponent())
                        viewGroup.addItem(w,it)
                        w.mouseClicked={e->viewGroup.select(e.source)}
                    }
                }
            },
            rightComponent:
            splitPane(
                    leftComponent:
                            scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 400px::, h 600px::'){
                                panel(id:'briefPanel',layout:new MigLayout()){
                                    def itemGroup=new SingleSelectedGroup(
                                    selectionChanged:{controller.selectBo(itemGroup.selectedValue)}
                                    )
                                    Bos.each{
                                    	def a=aS.getAdaptor(it,'briefDisplay')
										a.group=itemGroup
                                        def w=widget(constraints:'wrap,w 400px::',a.getComponent())
                                        itemGroup.addItem(w,it)
                                        w.mouseClicked={e->itemGroup.select(e.source)}
                                    }
                                }
                            },
                            rightComponent:
                            scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED,constraints:'w ::'){
                                panel(id:'detailPanel',constraints:'w ::'){
                                    widget(constraints:'w :400px:', aS.getAdaptor(Bos[1],'detailDisplay').getComponent())
                                }
                            }
                            ,/*resizeWeight:0.5f,*/oneTouchExpandable:true)
                    ,/*resizeWeight:0.25f,*/oneTouchExpandable:true)
    
}
class SingleSelectedGroup{
    def items=[]
    def values=[]
    def status=[:]
    def selected=null
    def selectedValue=null
    def selectionChanged={
    }
    def addItem(item,value){
        this.items<<item
        this.values<<value
    }
    def select(item){
        assert (item in items)
        if(selected){
            this.onUnselect(selected)
        }
        selected=item
        selectedValue=values[items.indexOf(item)]
        this.selectionChanged()
        this.onSelect(item)
    }
    def onSelect={obj->
        obj.putClientProperty('originBC',obj.background)
        obj.background=SystemColor.activeCaption
    }
    def onUnselect={obj->
        obj.background=obj.getClientProperty('originBC')
    }
}

