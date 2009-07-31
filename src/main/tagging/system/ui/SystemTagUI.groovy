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
                label(icon:IconManager.getIcon(SystemTagUI.systemTagIcons."${tagEntry.key}.${tm.hasTagOnTagable(value,tagEntry.key)}"),mouseClicked:{
                    event->
                    def tagClass=ServiceFactory.getService(SystemTagService).singletonSystemTags[tagEntry.key]
                    tagClass.getMethod('taggle',Tagable.class).invoke(tagClass,value)
                    event.source.icon=IconManager.getIcon(SystemTagUI.systemTagIcons."${tagEntry.key}.${tm.hasTagOnTagable(value,tagEntry.key)}")
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
        sts.singletonSystemTags['tag.system.star']=StarTag.class
        SystemTagUI.systemTagIcons['tag.system.unread.true']='unread'
        SystemTagUI.systemTagIcons['tag.system.unread.false']='read'
        SystemTagUI.systemTagIcons['tag.system.star.true']='star'
        SystemTagUI.systemTagIcons['tag.system.star.false']='nostar'
    }
}