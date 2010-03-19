package tagging.text.ui

import tagging.text.*
import tagging.ui.*
import tagging.*
import net.miginfocom.swing.MigLayout

class TextTagableBriefDisplay extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            label(text:"${value.text}")
            
        }
    }
}
class TextTagableDetailDisplay extends DefaultTagableDetailDisplayAdaptor{
    @Override def getPanel(){
        return sb.panel{
            label(text:"${value.name} - ${value.text}")
        }
    }
}
class TextTagableQuickTagRequests {
    def quickTagRequests=[
        new QuickTagRequest(type:'TextTagable',tagNames:['unread','star'])
    ]
}

