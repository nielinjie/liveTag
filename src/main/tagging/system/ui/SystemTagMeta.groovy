package tagging.system.ui

import tagging.*
import tagging.system.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout

import tagging.*


class SystemTagMeta{
    static void provideMeta(){
//        def aS=AdaptorServiceFactory.getAdaptorService()
//        aS.registerAdaptor('systemTag','briefDisplay',SystemTagBriefDisplayAdaptor.class)
		BoServiceFactory.getBoService().with{
        	it.registerBo('tag.system.unread','tag',UnreadTag.class)
			it.registerBo('tag.system.star','tag',StarTag.class)
        }
        def tm=TaggingManagerFactory.getTaggingManager()
        ServiceFactory.getService(UIMediator.class).with{
        	it.quickTagActions['tag.system.unread']=new QuickTagAction(
        			getAppear:{new QuickTagActionAppear(icon:tm.hasTagOnTagable(it,'tag.system.unread')?'unread':'read')},
					action:{UnreadTag.toggle(it)}
        	)
        	it.quickTagActions['tag.system.star']=new QuickTagAction(
        			getAppear:{new QuickTagActionAppear(icon:tm.hasTagOnTagable(it,'tag.system.star')?'star':'nostar')},
                    action:{StarTag.toggle(it)}
        	)
        }
    }
}