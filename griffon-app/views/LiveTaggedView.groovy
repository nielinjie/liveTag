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

def aS=AdaptorServiceFactory.getAdaptorService()
def searchViews=SearchServiceFactory.getSearchService().searchViews
def Bos=[]

def icons=[:].putAll(
['back','forward','more'].collect{
    new MapEntry(it,new ImageIcon(getClass().getResource("/icons/${it.toLowerCase()}.png")))
}
)
println icons
application(title:'LiveTagged',  size:[320,480], location:[50,50], pack:true, locationByPlatform:true,layout:new MigLayout()) {
    splitPane(constraints:'h 700px::, w 1200px::',
    leftComponent:
            scrollPane(id:'searchViewPanel',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 200px::, h 600px::'){
                panel(layout:new MigLayout()){
                    viewGroup=new SingleSelectedGroup(selectionChanged:{controller.selectSearchView(viewGroup.selectedValue)})
                    searchViews.each{
                    	println  aS.adaptorClasses.dump()
						println it.type
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
                    	panel(layout:new MigLayout(),constraints:'w 400px::, h 600px::'){
            				panel(layout:new MigLayout(),constraints:'growx ,wrap'){
            					etchedBorder(parent:true)
            					button(icon:icons['back'],id:'viewFrameBack',text:'back',actionPerformed:{model.currentViewFrame=model.history.back();controller.renderViewFrame(model.currentViewFrame)})
								button(icon:icons['forward'],id:'viewFrameForward',text:'forward',actionPerformed:{model.currentViewFrame=model.history.forward();controller.renderViewFrame(model.currentViewFrame)})
            					label(id:'viewFrameDescription',text:'view frame description here',constraints:'growx')
								bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasBack()},target:viewFrameBack,targetProperty:'enabled')
								bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasForward()},target:viewFrameForward,targetProperty:'enabled')
								bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.current?.description},target:viewFrameDescription,targetProperty:'text')
            				}
            				
                            scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w :420px:,h :100%:, wrap'){
                                panel(id:'briefPanel',layout:new MigLayout()){
                                    itemGroup=new SingleSelectedGroup(
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
                            }
                            panel(){
                            	button(id:'moreButton',icon:icons['more'],text:'More',enabled:false,actionPerformed:controller.&expandViewFrame)
								//bind(source:model.currentViewFrame,sourceEvent:'statusChanged',sourceValue:{model.currentViewFrame.hasMore()},target:moreButton,targetProperty:'enabled')
                            }
                            },
                            rightComponent:
                            scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED,constraints:'w ::'){
                                panel(id:'detailPanel',constraints:'w ::'){
                                    //widget(constraints:'w :400px:', aS.getAdaptor(Bos[1],'detailDisplay').getComponent())
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

