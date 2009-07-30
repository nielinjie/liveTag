package tagging.ui

import tagging.*
import tagging.ui.*

class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconNames=['search']
    def getPanel(){
        return sb.panel{
            etchedBorder(parent:true)
            label(text:value.name)
            button(icon:this.icons['search'],text:'Search',actionPerformed:{ controller?.selectSearchView(value)})
        }
    }
}
class SearchViewMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
    }
}