import tagging.*
import groovy.swing.*
class LiveTaggedController {
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
    	view.briefPanel.removeAll()
		println view.briefPanel.layout
		def tagables=tm.findTagable(searchView.condition)
		println tagables
		itemGroup=new SingleSelectedGroup(
				selectionChanged:{this.selectBo(itemGroup.selectedValue)})
        tagables.each{
            def w=aS.getAdaptorClass(it.type,'briefDisplay').newInstance(value:it,group:itemGroup).getComponent()
            itemGroup.addItem(w,it)
    		view.briefPanel.add(w)
			view.briefPanel.layout.setComponentConstraints(w,'wrap')
            w.mouseClicked={e->itemGroup.select(e.source)}
        }
    	view.briefPanel.revalidate()
//		view.briefPanel.update()
    }
    /*
    def action = { evt = null ->
    }
    */
}
