package tagging.system.ui
import tagging.ui.*
import tagging.*
import net.miginfocom.swing.MigLayout
class QuickTagBriefDisplay
extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        def tm=TaggingManagerFactory.getTaggingManager()
        def ui=ServiceFactory.getService(UIMediator.class)
        sb.panel(layout:new MigLayout('insets 0')){
            def requested=ui.quickTagRequests[value.class.simpleName]
            if(requested)
            requested.eachWithIndex{
                r,i->
                def action=ui.quickTagActions[r]
                if(action){
                    def appear=action.getAppear(value)
                    label(icon:IconManager.getIcon(appear.icon),mouseClicked:{
                            event->
                            action.action(value)
                            appear=action.getAppear(value)
                            event.source.icon=IconManager.getIcon(appear.icon)
                        },toolTipText:appear.text,constraints:(i%2)!=0?'wrap':'')
                }
            }
        }
    }
}

