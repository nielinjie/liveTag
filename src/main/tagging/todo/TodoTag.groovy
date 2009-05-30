package tagging.todo
import tagging.*
import groovy.swing.*
import net.miginfocom.swing.MigLayout
import java.awt.SystemColor
class TodoTag extends Tag{
    Date deadline
    Boolean done
    String type='tag.todo'
}
class TodoTagBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:value.name)
        }
    }
}
class TodoTagDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel{
            label("${value.name}")
        }
    }
}
class TodoTagMeta{
	static void provideMeta(){
		def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('tag.todo','briefDisplay',TodoTagBriefDisplayAdaptor.class)
        aS.registerAdaptor('tag.todo','detailDisplay',TodoTagDetailDisplayAdaptor.class)
    }
}
