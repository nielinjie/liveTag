package tagging.ui

import tagging.*

import groovy.swing.*
import javax.swing.*
class ImporterBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconNames=['download']
    def getPanel(){
        return sb.panel{
            label(text:value.name)
            label(text:bind{value.stop})
            button(text:'Update Now',icon:this.icons['download'])
        }
    }
}
class ImporterMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer','briefDisplay',ImporterBriefDisplayAdaptor.class)
    }
}