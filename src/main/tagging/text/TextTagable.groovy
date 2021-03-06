package tagging.text
import tagging.*
import groovy.swing.*
import net.miginfocom.swing.MigLayout
class TextTagable extends Tagable{
    String text
    String type='tagable.text'
}
class TextTagableBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:"${value.name} - ${value.text}")
        }
    }
}
class TextTagableDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    def getPanel(){
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
	}
}
