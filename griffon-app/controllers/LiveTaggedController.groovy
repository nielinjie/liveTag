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
        //add mock tagable
        //for now, it is for test ui
        MockData.mockTagables.each{
            TaggingManagerFactory.getTaggingManager().addTagable(it)
        }
    }
    void selectBo(bo){
        view.detailPanel.removeAll()
		view.detailPanel.revalidate()
        view.detailPanel.add(aS.getAdaptor(bo,'detailDisplay').getComponent())
        view.detailPanel.revalidate()
    }
    void selectSearchView(searchView){
        sb.doOutside{
            
            this.createViewFrame(searchView.condition(),searchView.description)
            this.renderViewFrame(model.currentViewFrame)
        }
        null
        //		view.briefPanel.update()
    }
    private void expandViewFrame(event){
        def bos=model.currentViewFrame.getDelta()
        this.itemGroup=new SingleSelectedGroup(
            selectionChanged:{
                this.selectBo(this.itemGroup.selectedValue)
            }
            )
        bos.each{ bo->
            def w=aS.getAdaptor(bo,'briefDisplay')
            w.group=itemGroup
            w=w.getComponent()
            edt{
                itemGroup.addItem(w,bo)
                view.briefPanel.add(w)
                view.briefPanel.layout.setComponentConstraints(w,'wrap')
                w.mouseClicked={e->itemGroup.select(e.source)}
                view.briefPanel.revalidate()
                
            }
        }
        if(bos.isEmpty()){
            edt{
                view.briefPanel.update()
            }
        }
        setMoreButtonEnable()
    }
    private void createViewFrame(bos,description){
        def re=new ViewFrame(allContent:bos,description:description)
        model.currentViewFrame=re
        model.history.add(re)
        re.getDelta()
    }
    private void setMoreButtonEnable(){
        edt{
            view.moreButton.enabled=model.currentViewFrame?.hasMore()
        }
    }
    void renderViewFrame(viewFrame){
        edt{
            view.briefPanel.removeAll()
			view.briefPanel.repaint()
        }
        def bos=viewFrame.getRequested()
        this.itemGroup=new SingleSelectedGroup(
            selectionChanged:{
                this.selectBo(this.itemGroup.selectedValue)
            }
            )
        bos.each{ bo->
            def w=aS.getAdaptor(bo,'briefDisplay')
            w.group=itemGroup
            w=w.getComponent()
            edt{
                itemGroup.addItem(w,bo)
                view.briefPanel.add(w)
                view.briefPanel.layout.setComponentConstraints(w,'wrap')
                w.mouseClicked={e->itemGroup.select(e.source)}
                view.briefPanel.revalidate()
            }
        }
        if(bos.isEmpty()){
        	edt{
        		view.briefPanel.revalidate()
        	}
        }
        setMoreButtonEnable()
    }
    /*
     def action = { evt = null ->
     }
     */
}
