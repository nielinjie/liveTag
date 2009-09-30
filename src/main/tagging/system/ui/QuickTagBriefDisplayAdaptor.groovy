package tagging.system.ui
import tagging.ui.*
import tagging.*
import net.miginfocom.swing.MigLayout
class QuickTagBriefDisplayAdaptor
	extends DefaultBriefDisplayAdaptor{
	    def getPanel(){
	        def tm=TaggingManagerFactory.getTaggingManager()
			def ui=ServiceFactory.getService(UIMediator.class)
	        sb.panel(layout:new MigLayout()){
				def requested=ui.quickTagRequests[value.type]
	            if(requested)
		            requested.each{
		            	def action=ui.quickTagActions[it]
		            	if(action){
							def appear=action.getAppear(value)
			                label(icon:IconManager.getIcon(appear.icon),mouseClicked:{
			                    event->
			                    action.action(value)
								appear=action.getAppear(value)
			                    event.source.icon=IconManager.getIcon(appear.icon)
			                })
		            	}
		            }
	        }
	    }
	}
class QuickTagMeta{
	static void provideMeta(){
		AdaptorServiceFactory.getAdaptorService().with{
			it.registerAdaptor('quickTag','briefDisplay',QuickTagBriefDisplayAdaptor.class)
		}
	}
}
