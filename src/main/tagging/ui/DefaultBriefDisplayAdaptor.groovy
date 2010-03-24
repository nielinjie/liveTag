package tagging.ui
import tagging.*
import tagging.util.*
import groovy.swing.SwingBuilder
import java.awt.SystemColor
import javax.swing.*
import net.miginfocom.swing.MigLayout
abstract class DefaultBriefDisplayAdaptor extends DisplayAdaptor{
    def group
    def originBackground
    def thePanel
    def controller
  
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        if (!this.thePanel){
            this.thePanel=getPanel()
            //inject something...
            def fm=ServiceFactory.getService(FunctionMatrix.class)
            DisplayAdaptor.getAdaptor(value,'typeIconDisplay')?.with{
                    thePanel.add(sb.widget(it),'aligny top',0)
            }
            //ad=aS.getAdaptor('quickTag',value,'briefDisplay')
            def ad=fm.getFunction('quickTag','briefDisplay')
            if(ad){
                ad.value=value
                thePanel.add(sb. widget(ad.panel),'aligny top',-1)
            }
        }
        thePanel.validate()
        return thePanel

    }
    def abstract getPanel()
}
