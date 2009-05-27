package tagging
import groovy.swing.SwingBuilder
import java.awt.SystemColor
import net.miginfocom.swing.MigLayout
abstract class DefaultBriefDisplayAdaptor{
    def value
    def group
    private def originBackground
    def getComponent(){
        def re=getPanel()
        re.metaClass.onSelected={
            re.metaClass.originBack=re.background
            re.background=SystemColor.activeCaption
        }
        re.metaClass.onUnselected={
            re.background=re.originBack
        }
        return re
    }
    def abstract getPanel()
}
