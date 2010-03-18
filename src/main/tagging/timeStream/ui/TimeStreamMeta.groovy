package tagging.timeStream.ui

import tagging.timeStream.*
import tagging.*
import tagging.ui.*
/**
 *
 * @author nielinjie
 */
class TimeStreamMeta {

    static void provideMeta(){
        //def aS=AdaptorServiceFactory.getAdaptorService()
        //aS.registerAdaptor('searchView.timeStream','briefDisplay',new SearchViewBriefDisplayAdaptor(icon:'todo'))

        //def mr=ServiceFactory.getService(UIMediator.class)
        //mr.registorSearchView(new SearchViewItem(order:1,group:'Default',searchView:new  TimeStreamSearchView()))

    }
}
class TimeStreamSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    String icon='todo'
}
class TimeStreamSearchViewProvides {
    def searchViewItems=[
        new SearchViewItem(order:1,group:'Default',searchView:new  TimeStreamSearchView())
    ]
}
