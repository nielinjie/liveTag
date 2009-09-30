package tagging.ui

import tagging.*
import net.miginfocom.swing.MigLayout
import groovy.swing.*
import javax.swing.*
class ImporterBriefDisplayAdaptor extends DefaultBriefDisplayAdaptor{
    def iconNames=['download']
    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            etchedBorder(parent:true)
            label(text:value.name)
            //label(text:bind{value.stop})
            label(/*constraints:'w ::24px, h ::24px',*/icon:this.icons['download'])
        }
    }
}
class ImporterMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer','briefDisplay',ImporterBriefDisplayAdaptor.class)
    }
}