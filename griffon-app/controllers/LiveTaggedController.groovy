import tag.*
class LiveTaggedController {
    // these will be injected by Griffon
    def model
    def view
    def aS=AdaptorServiceFactory.getAdaptorService()

    void mvcGroupInit(Map args) {
        def metaService=MetaServiceFactory.getMetaService()
		//manager meta data by metaService it self.
		
		metaService.providers.addAll(['tag.twitter.TwitterMeta',TodoTagMeta.class,TextTagableMeta.class,ImporterMeta.class,SearchViewMeta.class])
        metaService.init()
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
