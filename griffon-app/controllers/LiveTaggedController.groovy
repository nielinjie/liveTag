import tag.*
class LiveTaggedController {
    // these will be injected by Griffon
    def model
    def view
    def aS=AdaptorServiceFactory.getAdaptorService()

    void mvcGroupInit(Map args) {
        /*model.searchViews.addAll([
          new SearchView(condition:{true},sortComparator:{a,b->0})  
        ])*/
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('tag.todo','briefDisplay',TodoTagBriefDisplayAdaptor.class)
        aS.registerAdaptor('tag.todo','detailDisplay',TodoTagDetailDisplayAdaptor.class)
        aS.registerAdaptor('tagable.text','detailDisplay',TextTagableDetailDisplayAdaptor.class)
        aS.registerAdaptor('tagable.text','briefDisplay',TextTagableBriefDisplayAdaptor.class)
        aS.registerAdaptor('searchView','briefDisplay',SearchViewBriefDisplayAdaptor.class)
		aS.registerAdaptor('importer','briefDisplay',ImporterBriefDisplayAdaptor.class)
        // this method is called after model and view are injected
    }
    void selectBo(bo){
        view.detailPanel.removeAll()
        view.detailPanel.add(aS.getAdaptorClass(bo.type,'detailDisplay').newInstance(value:bo).getComponent())
        view.detailPanel.revalidate()
    }
    /*
    def action = { evt = null ->
    }
    */
}
