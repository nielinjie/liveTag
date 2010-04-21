package tagging.ui
import tagging.*
import tagging.util.*
import javax.swing.*
import net.miginfocom.swing.MigLayout
abstract class DefaultTagableDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    @Override
    def getComponent(){
        return sb.splitPane(orientation:JSplitPane.VERTICAL_SPLIT,constraints:'h :100%:, w :100%:',topComponent:
            sb.panel(layout:new MigLayout('fill')){
        	widget(this.panel,constraints:'grow')
            },bottomComponent:
            sb.panel(layout:new MigLayout()){
                def fm=ServiceFactory.getService(FunctionMatrix.class)
                TaggingManagerFactory.getTaggingManager().getTagsForTagable(value.id)
                .sort{a,b->a.type.compareTo(b.type)}.each{
                    if(!this.skip(it)){
                        def w=fm.getFunction(it.class.simpleName,'detailDisplay')
                        if(!w){
                            widget(this.getUnknowPanel(it),constraints:'wrap')
                        }else{
                            w.value=it
                            widget(w.component,constraints:'wrap')
                        }
                    }
                }
            }
            ,resizeWeight:0.6,continuousLayout:true,oneTouchExpandable:true)
    }
    Boolean skip(Tag tag){
        return [tagging.keyword.KeywordTag.class,tagging.system.SystemTag.class,tagging.people.CreatedByTag.class].any{it.isAssignableFrom(tag.class)}
    }
    def getUnknowPanel(Tag tag){
        sb.panel{
            label("I am a placeholder for unknow tag- ${tag.dump()}")
        }
    }
    def abstract getPanel()
}