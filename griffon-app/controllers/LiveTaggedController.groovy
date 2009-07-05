import tagging.*
import groovy.swing.*
class LiveTaggedController {
    private def sb=new SwingBuilder()
    // these will be injected by Griffon
    def model
    def view
    def aS=AdaptorServiceFactory.getAdaptorService(this)
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
        view.detailPanel.add(aS.getAdaptor(bo,'detailDisplay').getComponent())
        view.detailPanel.revalidate()
    }
    void selectSearchView(searchView){
        sb.doOutside{
            edt{
                view.briefPanel.removeAll()
            }
            def tagables=searchView.condition()
            this.itemGroup=new SingleSelectedGroup(
                    selectionChanged:{
                        this.selectBo(this.itemGroup.selectedValue)
                    }
                    
                    )
            tagables.each{ tagable->
                def w=aS.getAdaptor(tagable,'briefDisplay')
				w.group=itemGroup
				w=w.getComponent()
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
