package tagging.system.ui

import tagging.*
import tagging.system.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout

import tagging.*

class SystemTagUI{
	static def systemTagIcons=[:]
}
class SystemTagBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        def sts=ServiceFactory.getService(SystemTagService)
		def tm=TaggingManagerFactory.getTaggingManager()
        sb.panel(layout:new MigLayout()){
            sts.singletonSystemTags.each{
            	tagEntry->
                button(text:"${tagEntry.key}",actionPerformed:{
                	event->
                	event.source.text=tm.hasTagOnTagable(value,tagEntry.key).toString()
                })
            }
        }
    }
}
class SystemTagMeta{
    static void provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('systemTag','briefDisplay',SystemTagBriefDisplayAdaptor.class)
        def sts=ServiceFactory.getService(SystemTagService)
        sts.singletonSystemTags['tag.system.unread']=UnreadTag.class
        SystemTagUI.systemTagIcons['tag.system.unread.true']='unread'
        SystemTagUI.systemTagIcons['tag.system.unread.false']='read'
    }
}