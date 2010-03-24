package tagging.ui

import tagging.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout
class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconLabel
    def getPanel(){
        def re= sb.panel(layout:new MigLayout()){
            def iconString=this.hasProperty('icon')?this.icon:'search'
            iconLabel=label(icon:IconManager.getIcon(iconString))//,mouseClicked:{ controller?.selectSearchView(value)})
            label(text:value.name)
        }
        return re
    }
}
class SearchViewButtonDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconLabel
    def getPanel(){
        def re= sb.panel(layout:new MigLayout()){
            def iconString=this.hasProperty('icon')?this.icon:'search'
            button(label:value.name,icon:IconManager.getIcon(iconString),mouseClicked:{ ServiceFactory.getService('controller')?.selectSearchView(value)})
            //label(text:value.name)
        }
        return re
    }
}
