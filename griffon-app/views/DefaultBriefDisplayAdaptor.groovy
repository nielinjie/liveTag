import groovy.swing.SwingBuilder
import java.awt.SystemColor
import net.miginfocom.swing.MigLayout
class DefaultBriefDisplayAdaptor{
    def value
    def group
    private def originBackground
    def getComponent(){
        def re=new SwingBuilder().panel(background:SystemColor.inactiveCaption,layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:value.name)
            button(text:'button')
        }
        re.metaClass.onSelected={
            re.metaClass.originBack=re.background
            re.background=SystemColor.activeCaption
        }
        re.metaClass.onUnselected={
            re.background=re.originBack
        }
        return re
    }
}
