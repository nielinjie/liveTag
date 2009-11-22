package tagging.people.ui

import tagging.*
import tagging.ui.*

import tagging.people.*
/**
 *
 * @author nielinjie
 */
class PeopleMeta {
    static void provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView.people','briefDisplay',new SearchViewBriefDisplayAdaptor())
        def mr=ServiceFactory.getService(UIMediator.class)
        mr.registorSearchView(new SearchViewItem(order:10,group:'Category',
                searchView:new  PeopleSearchView()
            ))
    }
}

