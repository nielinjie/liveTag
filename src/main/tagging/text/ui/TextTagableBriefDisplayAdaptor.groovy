package tagging.text.ui

import tagging.text.*
import tagging.ui.*
import tagging.*
import net.miginfocom.swing.MigLayout

class TextTagableBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:"${value.text}")
            def aS=AdaptorServiceFactory.getAdaptorService()
            widget(aS.getAdaptor('quickTag',value,'briefDisplay').component,constraints:'x 1al')
        }
    }
}
class TextTagableDetailDisplayAdaptor extends DefaultTagableDetailDisplayAdaptor{
    @Override def getPanel(){
        return sb.panel{
            label(text:"${value.name} - ${value.text}")
        }
    }
}
class TextTagableMeta{
    static def provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('tagable.text','detailDisplay',TextTagableDetailDisplayAdaptor.class)
        aS.registerAdaptor('tagable.text','briefDisplay',TextTagableBriefDisplayAdaptor.class)
		ServiceFactory.getService(UIMediator.class).with{
        	it.quickTagRequests['tagable.text']=['tag.system.unread','tag.system.star']
        }
    }
}
