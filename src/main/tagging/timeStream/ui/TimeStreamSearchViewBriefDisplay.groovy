package tagging.timeStream.ui

import tagging.timeStream.*
import tagging.*
import tagging.ui.*
/**
 *
 * @author nielinjie
 */

class TimeStreamSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
    String icon='todo'
}
class TimeStreamSearchViewProvides {
    def searchViewItems=[
        new SearchViewItem(order:1,group:'Default',searchView:new  TimeStreamSearchView())
    ]
}
