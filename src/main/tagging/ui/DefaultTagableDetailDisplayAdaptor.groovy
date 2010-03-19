package tagging.ui
import tagging.*
import tagging.util.*
import javax.swing.*
import net.miginfocom.swing.MigLayout
abstract class DefaultTagableDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    @Override
    def getComponent(){
        return sb.splitPane(orientation:JSplitPane.VERTICAL_SPLIT,constraints:'h :100%:, w :100%:',topComponent:
            sb.panel{
//                etchedBorder(parent:true)
        	widget(this.panel)
            },bottomComponent:
            sb.panel(layout:new MigLayout()){
//                etchedBorder(parent:true)
                def fm=ServiceFactory.getService(FunctionMatrix.class)
                TaggingManagerFactory.getTaggingManager().getTagsForTagable(value.id).sort{a,b->a.type.compareTo(b.type)}.each{
                    def w=fm.getFunction(it.class.simpleName,'detailDisplay')
                    if(!w){
                        widget(this.unknowPanel,constraints:'wrap')
                    }else{
                        w.value=it
                        widget(w.component,constraints:'wrap')
                    }
                }
            }
            ,resizeWeight:0.6,continuousLayout:true,oneTouchExpandable:true)
    }
    def unknowPanel=sb.panel{
        label('I am a placeholder for unknow tag')
    }
    def abstract getPanel()
}