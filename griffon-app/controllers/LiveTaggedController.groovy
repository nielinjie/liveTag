import tagging.*
import groovy.swing.*
import tagging.util.*
import org.java.plugin.*
import org.java.plugin.boot.*
import org.java.plugin.util.*
class LiveTaggedController {
    private def sb=new SwingBuilder()
    def model
    def view
    FunctionMatrix functionMatrix=new AutoFunctionMatrix().with{
        ServiceFactory.setService(FunctionMatrix.class, it)
        it
    }
    def aS=AdaptorServiceFactory.getAdaptorService(this)
    def tm=TaggingManagerFactory.getTaggingManager()
    def itemGroup
    def briefItemLayoutConstraints='wrap, w 400px::'
    def	detailItemLayoutConstraints='w :100%:, h :100%:'
    private processingViewFrame
    void mvcGroupInit(Map args) {
    }
    void selectBo(bo){
        view.detailPanel.removeAll()
        view.detailPanel.revalidate()
        def w=functionMatrix.getFunction(bo.class.simpleName,'detailDisplay')
        w.value=bo
        w=w.component
        view.detailPanel.add(w)
        view.detailPanel.layout.setComponentConstraints(w,detailItemLayoutConstraints)
        view.detailPanel.revalidate()
        view.detailPanel.repaint()
    }
    void selectSearchView(searchView){
        sb.doOutside{
            this.createViewFrame(searchView.condition().sort(searchView.sortComparator?:{a,b->0}),searchView.description)
            this.renderViewFrame(model.currentViewFrame)
        }
        null
    }
    private void expandViewFrame(event){
        def bos=model.currentViewFrame.getDelta()
        assert this.itemGroup!=null
        bos.each{ bo->
            def w=functionMatrix.getFunction(bo.class.simpleName,'briefDisplay')
            w.value=bo
            w.group=itemGroup
            w=w.component
            edt{
                itemGroup.addItem(w,bo)
                view.briefPanel.add(w)
                view.briefPanel.layout.setComponentConstraints(w,briefItemLayoutConstraints)
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
        this.processingViewFrame=viewFrame
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
            def w=functionMatrix.getFunction(bo.class.simpleName,'briefDisplay')
            w.value=bo
            w.group=itemGroup
            w=w.component
            edt{
                itemGroup.addItem(w,bo)
                if(viewFrame==this.processingViewFrame){
                    view.briefPanel.add(w)
                    view.briefPanel.layout.setComponentConstraints(w,briefItemLayoutConstraints)
                    w.mouseClicked={e->itemGroup.select(e.source)}
                    view.briefPanel.revalidate()
                }
            }
        }
        if(bos.isEmpty()){
            edt{
                view.briefPanel.revalidate()
            }
        }
        setMoreButtonEnable()
        this.processingViewFrame=null
    }
}
