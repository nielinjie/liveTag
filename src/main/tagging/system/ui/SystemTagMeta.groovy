package tagging.system.ui

import tagging.*
import tagging.system.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout

import tagging.*


class SystemTagMeta{
    static void provideMeta(){
//        AdaptorServiceFactory.getAdaptorService().with{
//            it.registerAdaptor('tag.system.unread','detailDisplay',UnreadTagDetailDisplayAdaptor.class)
//            it.registerAdaptor('tag.system.star','detailDisplay',StarTagDetailDisplayAdaptor.class)
//        }
//        BoServiceFactory.getBoService().with{
//            it.registerBo('tag.system.unread','tag',UnreadTag.class)
//            it.registerBo('tag.system.star','tag',StarTag.class)
//        }
    }
}
class UnreadSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    def icon='unread'
}
class StarSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    def icon='star'
}
class SystemSearchViewProvides{
    def searchViewItems=[
//        new SearchViewItem(
//            order:100,
//            group:'System',
//            searchView:
//            new SearchView(
//                name:'All',
//                description:'All Bos',
//                condition:{
//                    TaggingManagerFactory.getTaggingManager().findTagable({true})
//                },
//                sortComparator:{
//                    a,b->0
//                }
//            )
//        ),
        new SearchViewItem(order:100,group:'Default',searchView:new  UnreadSearchView()),
        new SearchViewItem(order:101,group:'Default',searchView:new  StarSearchView())
    ]
}
class UnreadTagQuickTagProvides{
    def tm=TaggingManagerFactory.getTaggingManager()
    def quickTagProvides=[
        new QuickTagProvide(
            name:'unread',
            action:
            new QuickTagAction(
                getAppear:{new QuickTagActionAppear(text:'toggle read or not',icon:tm.hasTagOnTagable(it,'tag.system.unread')?'unread_s':'read_s')},
                action:{UnreadTag.toggle(it)}
            )
        )
    ]
}
class StarTagQuickTagProvides{
    def tm=TaggingManagerFactory.getTaggingManager()
    def quickTagProvides=[
        new QuickTagProvide(
            name:'star',
            action:
            new QuickTagAction(
                getAppear:{new QuickTagActionAppear(text:'toggle star',icon:tm.hasTagOnTagable(it,'tag.system.star')?'star_s':'nostar_s')},
                action:{StarTag.toggle(it)}
            )
        )
    ]
}
class UnreadTagDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel{
            label("I am a unread tag")
        }
    }
}
class StarTagDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel{
            label("I am a star tag")
        }
    }
}
