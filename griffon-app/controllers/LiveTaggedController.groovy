import tagging.*
import groovy.swing.*
import tagging.util.*
import tagging.ui.*
import net.miginfocom.swing.MigLayout


class LiveTaggedController {
    private def sb=new SwingBuilder()
    def model
    def view
    FunctionMatrix functionMatrix=new AutoFunctionMatrix().with{
        ServiceFactory.setService(FunctionMatrix.class, it)
        ServiceFactory.setService('controller',this)
        it
    }
    //def aS=AdaptorServiceFactory.getAdaptorService(this)
    def tm=TaggingManagerFactory.getTaggingManager()
    def itemGroup
    def briefItemLayoutConstraints='wrap, w 400px::'
    def	detailItemLayoutConstraints='grow'
    private processingViewFrame
    void mvcGroupInit(Map args) {
    }
    void selectBo(bo){
        view.detailPanel.removeAll()
        view.detailPanel.revalidate()
        def w=DisplayAdaptor.getAdaptor(bo,'detailDisplay')

        view.detailPanel.add(w,detailItemLayoutConstraints)
        //view.detailPanel.layout.setComponentConstraints(w,)
        view.detailPanel.validate()
        // view.detailPanel.repaint()
    }
    void selectSearchView(searchView){
        sb.doOutside{
            this.createViewFrame(searchView.condition().sort(searchView.sortComparator?:{a,b->0}),searchView.description)
            this.renderViewFrame(model.currentViewFrame)
        }
        null
    }
    private void expandViewFrame(event){
        sb.doOutside{
            def bos=model.currentViewFrame.getDelta()
            assert this.itemGroup!=null
            bos.eachWithIndex{ bo,index->
                def w=DisplayAdaptor.getAdaptor(bo,'briefDisplay')

                edt{
                    itemGroup.addItem(w,bo)
                    view.briefPanel.add(w,briefItemLayoutConstraints)
                    //view.briefPanel.layout.setComponentConstraints(w,)
                    w.mouseClicked={e->itemGroup.select(e.source)}
                    if(index % 7 ==6)view.briefPanel.revalidate()
                }
            }
            edt{
                view.briefPanel.revalidate()
            }
            setMoreButtonEnable()
        }
    }
    private void createViewFrame(bos,description){
        def re=new ViewFrame(deltaSize:80,allContent:bos,description:description)
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
            view.busy.busy=true
            view.briefPanel.removeAll()
            view.briefPanel.repaint()
        }
        def bos=viewFrame.getRequested()
        this.itemGroup=new SingleSelectedGroup(
            selectionChanged:{
                this.selectBo(this.itemGroup.selectedValue)
            }
        )
        bos.eachWithIndex{ bo,index->
            def w=DisplayAdaptor.getAdaptor(bo,'briefDisplay')
            edt{
                itemGroup.addItem(w,bo)
                if(viewFrame==this.processingViewFrame){
                    view.briefPanel.add(w,briefItemLayoutConstraints)
                    //view.briefPanel.layout.setComponentConstraints(w,)
                    w.mouseClicked={e->itemGroup.select(e.source)}
                    if(index % 7 ==2)view.briefPanel.revalidate()
                }
            }
        }
            edt{
                view.briefPanel.revalidate()
                view.busy.busy=false
            }
        setMoreButtonEnable()
        this.processingViewFrame=null
    }
    void configDialog(Object object){
        def dialog=
        new SwingBuilder().dialog(
            modal:true,
            owner:view.rootFrame,
            title:'Config',
            size:[600,400],
            layout:new MigLayout('debug,fill')
        ){
            widget(DisplayAdaptor.getAdaptor(object,'configDisplay'),constraints:'grow')
        }
        dialog.visible=true
    }
}
