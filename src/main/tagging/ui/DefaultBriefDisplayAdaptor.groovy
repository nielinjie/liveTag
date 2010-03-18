package tagging.ui
import tagging.*
import tagging.util.*
import groovy.swing.SwingBuilder
import java.awt.SystemColor
import javax.swing.*
import net.miginfocom.swing.MigLayout
abstract class DefaultBriefDisplayAdaptor{
    def value
    def group
    def originBackground
    def thePanel
    def controller
    //    def iconNames=[]
    //    @Lazy def icons={
    //        [:].putAll(
    //            this.iconNames.collect{
    //                name->
    //                new MapEntry(name,new ImageIcon(getClass().getResource("/icons/${name.toLowerCase()}.png")))
    //            }
    //        )}()
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        if (!this.thePanel){
            this.thePanel=getPanel()
            //inject something...
            def fm=ServiceFactory.getService(FunctionMatrix.class)
            def ad=fm.getFunction(value.class.simpleName,'typeIconDisplay')
            if(ad){
                ad.value=value
                thePanel.add(sb.widget(ad.panel),'aligny top',0)
            }
            //ad=aS.getAdaptor('quickTag',value,'briefDisplay')
            ad=fm.getFunction('quickTag','briefDisplay')
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
