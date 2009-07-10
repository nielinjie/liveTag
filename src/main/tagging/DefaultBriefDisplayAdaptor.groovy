package tagging
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
    def iconNames=[]
    @Lazy def icons={
        [:].putAll(
        this.iconNames.collect{
            name->
            new MapEntry(name,new ImageIcon(getClass().getResource("/icons/${name.toLowerCase()}.png")))
        }
        )}()
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        if (!this.thePanel)
            this.thePanel=getPanel()
        return this.thePanel
    }
    def abstract getPanel()
}
