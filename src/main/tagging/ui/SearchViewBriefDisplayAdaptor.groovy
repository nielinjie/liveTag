package tagging.ui

import tagging.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout
class SearchViewBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconLabel
    def getPanel(){
        def re= sb.panel(layout:new MigLayout()){
            etchedBorder(parent:true)
            def iconString=this.hasProperty('icon')?this.icon:'search'
            iconLabel=label(icon:IconManager.getIcon(iconString),mouseClicked:{ controller?.selectSearchView(value)})
            label(text:value.name)
        }
        return re
    }
}
