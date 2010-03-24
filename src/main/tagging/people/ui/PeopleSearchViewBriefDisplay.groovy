package tagging.people.ui

import tagging.*
import tagging.ui.*

import tagging.people.*
/**
 *
 * @author nielinjie
 */

class PeopleSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{
}
class CreatedBySearchViewButtonDisplay extends SearchViewButtonDisplayAdaptor{}
class PeopleSearchViewProvides{
    def searchViewItems=[
        new SearchViewItem(order:10,group:'Category',
            searchView:new  PeopleSearchView())
    ]
}
