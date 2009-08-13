package tagging.ui

import tagging.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout
class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconNames=['search']
    String icon
    def iconLabel
    def getPanel(){
        def re= sb.panel(layout:new MigLayout()){
            etchedBorder(parent:true)
            iconLabel=label(/*constraints:'w ::24px, h ::24px',*/icon:this.icons['search'],mouseClicked:{ controller?.selectSearchView(value)})
            label(text:value.name)
        }
        if (icon){
        	this.setIcon(icon)
        }
        return re
    }
    def setIcon(String icon){
    	this.iconLabel.icon=IconManager.getIcon(icon)
    }
}
class SearchViewMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
    }
}