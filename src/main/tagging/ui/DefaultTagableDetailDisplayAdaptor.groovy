package tagging.ui
import tagging.*
import javax.swing.*
import net.miginfocom.swing.MigLayout
abstract class DefaultTagableDetailDisplayAdaptor extends DefaultDetailDisplayAdaptor{
	@Override
	def getComponent(){
		def aS=AdaptorServiceFactory.getAdaptorService()
        return sb.splitPane(orientation:JSplitPane.VERTICAL_SPLIT,constraints:'h :100%:, w :100%:',topComponent:
        	sb.panel{
			etchedBorder(parent:true)
        	widget(this.panel)
		},bottomComponent:
			sb.panel(layout:new MigLayout()){
        		etchedBorder(parent:true)
        		TaggingManagerFactory.getTaggingManager().getTagsForTagable(value.id).sort{a,b->a.type.compareTo(b.type)}.each{
        			widget(aS.getAdaptor(it.type,it,'detailDisplay')?.component?:this.unknowPanel,constraints:'wrap')
        		}
        	}
		,resizeWeight:0.6,continuousLayout:true,oneTouchExpandable:true)
    }
	def unknowPanel=sb.panel{
		label('I am a placeholder for unknow tag')
	}
	def abstract getPanel()
}