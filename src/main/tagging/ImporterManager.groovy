package tagging

class ImporterManager{
    def importers=[]

}

class ImporterManagerConfigSaveRequets{
    Map<String,Object> getConfigs(){
        return ['Importers':ServiceFactory.getService(ImporterManager.class).importers]
    }
}
class ImporterManagerTaggingManagerListener{
    def onTaggingManagerStart(TaggingManager taggingManager){
        def im=ServiceFactory.getService(ImporterManager.class)
        def cm=ServiceFactory.getService(ConfigService.class)
        im.importers=cm.getConfig('Importers')
        im.importers.each{
           it.start()
        }
    }
}