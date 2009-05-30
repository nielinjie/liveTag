import tagging.*
import groovy.swing.*
class LiveTaggedController {
	private def sb=new SwingBuilder()
    // these will be injected by Griffon
    def model
    def view
    def aS=AdaptorServiceFactory.getAdaptorService()
	def tm=TaggingManagerFactory.getTaggingManager()
	def itemGroup
    void mvcGroupInit(Map args) {
        def metaService=MetaServiceFactory.getMetaService()
		//manager meta data by metaService it self.
		
		metaService.providers.addAll(MockData.metas)
        metaService.init()
    }
    void selectBo(bo){
        view.detailPanel.removeAll()
        view.detailPanel.add(aS.getAdaptorClass(bo.type,'detailDisplay').newInstance(value:bo).getComponent())
        view.detailPanel.revalidate()
    }
    void selectSearchView(searchView){
    	sb.doOutside{
    		edt{
    			view.briefPanel.removeAll()
    		}
			def tagables=tm.findTagable(searchView.condition)
			this.itemGroup=new SingleSelectedGroup(
					selectionChanged:{this.selectBo(this.itemGroup.selectedValue)})
	        tagables.each{
				tagable->
	            def w=aS.getAdaptorClass(tagable.type,'briefDisplay').newInstance(value:tagable,group:itemGroup).getComponent()
				edt{
		            itemGroup.addItem(w,tagable)
		    		view.briefPanel.add(w)
					view.briefPanel.layout.setComponentConstraints(w,'wrap')
		            w.mouseClicked={e->itemGroup.select(e.source)}
			        view.briefPanel.revalidate()
	            }
	        }
    	}
    	null
//		view.briefPanel.update()
    }
    /*
    def action = { evt = null ->
    }
    */
}
