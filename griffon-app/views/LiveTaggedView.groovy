import net.miginfocom.swing.MigLayout
import static javax.swing.ScrollPaneConstants.*
import javax.swing.event.*
import javax.swing.*
import java.awt.SystemColor
import java.awt.Color
import javax.swing.table.*

import com.l2fprod.common.swing.JOutlookBar;

import ca.odell.glazedlists.gui.*
import ca.odell.glazedlists.swing.*
import ca.odell.glazedlists.*
import groovy.swing.SwingBuilder
import tagging.*
import tagging.ui.*
import tagging.util.*

def aS=AdaptorServiceFactory.getAdaptorService()
def fm=ServiceFactory.getService(FunctionMatrix.class)
def Bos=[]


def icons=[:].putAll(
    ['back','forward','more'].collect{
        new MapEntry(it, IconManager.getIcon(it))
    }
)

println icons
def mr=ServiceFactory.getService(UIMediator.class)
//mr.init(controller.functionMatrix)
application(title:'LiveTag - Tag your life, and color it.',  size:[320,480], location:[50,50], pack:true, locationByPlatform:true,layout:new MigLayout()) {
	
    panel(constraints:'wrap, h :100%:',layout:new MigLayout() ){
        toolBar(constraints:'w 60%::'){
            textField(id:'magicText',columns:50)
            mr.getMagicTextProvides().each{
                me->
                def appear=me.value.getAppear(magicText.text)
                button(text:appear.text,icon:IconManager.getIcon(appear.icon),actionPerformed:{
                        me.value.action(magicText.text)
                    })
            }
        }
        label(icon:IconManager.getIcon('banner'),constraints:'wrap')
    }
    JOutlookBar outlook = new JOutlookBar();
    outlook.setTabPlacement(JTabbedPane.LEFT);
    viewGroup=new SingleSelectedGroup(selectionChanged:{controller.selectSearchView(viewGroup.selectedValue)})
    def searchViewGroups=mr.getSearchViewGroups()
    searchViewGroups.each{
    	def searchViews=mr.getSearchViews(it)
        def outlookPanel=panel(layout:new MigLayout()){
            searchViews.each{
                println it.class.simpleName
                def a=fm.getFunction(it.class.simpleName,'briefDisplay')
                a.group=viewGroup
                a.value=it
                def w=widget(constraints:'wrap,w :150px:',a.getComponent())
                viewGroup.addItem(w,it)
                w.mouseClicked={e->viewGroup.select(e.source)}
            }
        }
        outlook.addTab(it, outlook.makeScrollPane(outlookPanel));
    }
    splitPane(constraints:'h 600px::, w 1200px::',
        leftComponent:
        //            scrollPane(id:'searchViewPanel',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w 200px::, h 600px::'){
        //
        //            }
    	
    	widget(outlook)
        ,
        rightComponent:
        splitPane(
            leftComponent:
            panel(layout:new MigLayout(),constraints:'w :500px:, h 600px::'){
                panel(layout:new MigLayout(),constraints:'growx ,wrap'){
                    etchedBorder(parent:true)
                    label(id:'viewFrameDescription',text:'view frame description here',constraints:'growx')
                    panel(layout:new MigLayout('insets 0'),constraints:'x 1al'){
                        button(icon:icons['back'],id:'viewFrameBack',text:'back',actionPerformed:{model.currentViewFrame=model.history.back();controller.renderViewFrame(model.currentViewFrame)})
                        button(icon:icons['forward'],id:'viewFrameForward',text:'forward',actionPerformed:{model.currentViewFrame=model.history.forward();controller.renderViewFrame(model.currentViewFrame)})
                    }
                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasBack()},target:viewFrameBack,targetProperty:'enabled')
                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasForward()},target:viewFrameForward,targetProperty:'enabled')
                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.current?.description},target:viewFrameDescription,targetProperty:'text')
                }
            				
                def boScrollPane=scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_NEVER,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:'w :420px:,h :90%:, wrap'){
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
                boScrollPane.getVerticalScrollBar().setUnitIncrement(50);
                panel(){
                    button(id:'moreButton',icon:icons['more'],text:'More',enabled:false,actionPerformed:controller.&expandViewFrame)
                    //bind(source:model.currentViewFrame,sourceEvent:'statusChanged',sourceValue:{model.currentViewFrame.hasMore()},target:moreButton,targetProperty:'enabled')
                }
            },
            rightComponent:
            //panel{
            //                            		panel(id:'navigate',layout:new MigLayout(),constraints:'wrap'){
            //
            //                            		}
            //scrollPane(horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_AS_NEEDED,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED,constraints:'w ::'){
            panel(id:'detailPanel',constraints:'w :100%:, h :100%:',layout:new MigLayout()){
                //widget(constraints:'w :400px:', aS.getAdaptor(Bos[1],'detailDisplay').getComponent())
            }
            //}
            //}
            ,/*resizeWeight:0.5f,*/oneTouchExpandable:true)
        ,/*resizeWeight:0.25f,*/oneTouchExpandable:true)
    
}
class SingleSelectedGroup{
    def selectedColor=new Color(121,168,219)
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
        obj.background=selectedColor
    }
    def onUnselect={obj->
        obj.background=obj.getClientProperty('originBC')
    }
}

