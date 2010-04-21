import net.miginfocom.swing.MigLayout
import static javax.swing.ScrollPaneConstants.*
import javax.swing.event.*
import javax.swing.*
import java.awt.*
import javax.swing.table.*

import com.l2fprod.common.swing.JOutlookBar;

import ca.odell.glazedlists.gui.*
import ca.odell.glazedlists.swing.*
import ca.odell.glazedlists.*
import groovy.swing.SwingBuilder
import tagging.*
import tagging.ui.*
import tagging.util.*

//def aS=AdaptorServiceFactory.getAdaptorService()
def fm=ServiceFactory.getService(FunctionMatrix.class)
//def Bos=[]


def icons=[:].putAll(
    ['back','forward','more'].collect{
        new MapEntry(it, IconManager.getIcon(it))
    }
)

println icons
def mr=ServiceFactory.getService(UIMediator.class)
//mr.init(controller.functionMatrix)
def rootFrame=application(title:'LiveTag - Tag your life, and color it.',id:'rootFrame',
    //size:[320,480],
    pack:true,
    layout:new MigLayout('fill','[fill]','[][fill,:100%:]'),
    //location:[50,50],
    locationByPlatform:true,
    iconImage: imageIcon('/teabag.gif').image,
)
{



    panel(constraints:'wrap',layout:new MigLayout('fill','[fill,:60%:][fill]') ){
        toolBar(constraints:''){
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
                def a=DisplayAdaptor.getAdaptor(it,'briefDisplay')
                def w=widget(a,constraints:'wrap')
                viewGroup.addItem(w,it)
                w.mouseClicked={e->viewGroup.select(e.source)}
            }
        }
        outlook.addTab(it, outlook.makeScrollPane(outlookPanel));
    }
    splitPane(constraints:'',
        leftComponent:

    	widget(outlook)
        ,
        rightComponent:
        splitPane(
            leftComponent:
            panel(layout:new MigLayout('fill','[fill]','[][fill,grow][]'),constraints:''){
                panel(layout:new MigLayout('fill,ins 0'),constraints:'growx ,wrap'){
                    label(id:'viewFrameDescription',text:'view frame description here',constraints:'growx')
                    panel(layout:new MigLayout('insets 0'),constraints:'x 1al'){
                        button(icon:icons['back'],id:'viewFrameBack',text:'back',
                            enabled:false,
                            actionPerformed:{
                                doOutside{
                                    model.currentViewFrame=model.history.back()
                                    controller.renderViewFrame(model.currentViewFrame)
                                }
                            })
                        button(icon:icons['forward'],id:'viewFrameForward',text:'forward',
                            enabled:false,
                            actionPerformed:{
                                doOutside{
                                    model.currentViewFrame=model.history.forward()
                                    controller.renderViewFrame(model.currentViewFrame)
                                }
                            })
                    }
                    model.history.addStatusChangedListener([
                            statusChanged:{
                                viewFrameBack.enabled=model.history.hasBack()
                                viewFrameForward.enabled=model.history.hasForward()
                                viewFrameDescription.text=model.history.current?.description
                            }
                        ] as StatusChangedListener)
                    //                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasBack()},target:viewFrameBack,targetProperty:'enabled')
                    //                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.hasForward()},target:viewFrameForward,targetProperty:'enabled')
                    //                    bind(source:model.history,sourceEvent:'statusChanged',sourceValue:{model.history.current?.description},target:viewFrameDescription,targetProperty:'text')
                }

                def boScrollPane=scrollPane(id:'boScrollPane',horizontalScrollBarPolicy:HORIZONTAL_SCROLLBAR_NEVER,verticalScrollBarPolicy:VERTICAL_SCROLLBAR_AS_NEEDED ,constraints:' wrap'){
                    panel(id:'briefPanel',layout:new MigLayout('','[fill]')){
                        itemGroup=new SingleSelectedGroup(
                            selectionChanged:{controller.selectBo(itemGroup.selectedValue)}
                        )
                    }
                }
                boScrollPane.getVerticalScrollBar().setUnitIncrement(50);
                panel(){
                    button(id:'moreButton',icon:icons['more'],text:'More',enabled:false,
                        actionPerformed:controller.&expandViewFrame)
                }
            },
            rightComponent:
            panel(id:'detailPanel',layout:new MigLayout('fill')){
                //widget(constraints:'w :400px:', aS.getAdaptor(Bos[1],'detailDisplay').getComponent())
            }
            ,dividerLocation:460 as Integer,resizeWeight:0.3f,oneTouchExpandable:true)
        ,dividerLocation:200 as Integer,resizeWeight:0.15f,oneTouchExpandable:true)

}
rootFrame.setExtendedState(rootFrame.getExtendedState() | Frame.MAXIMIZED_BOTH);
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

