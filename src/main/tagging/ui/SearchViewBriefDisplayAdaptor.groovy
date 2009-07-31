package tagging.ui

import tagging.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout
class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconNames=['search']
    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            etchedBorder(parent:true)
            label(text:value.name)
            button(constraints:'w ::24px, h ::24px',icon:this.icons['search'],actionPerformed:{ controller?.selectSearchView(value)})
        }
    }
}
class SearchViewMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
    }
}