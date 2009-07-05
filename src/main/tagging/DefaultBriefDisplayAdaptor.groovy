package tagging
import groovy.swing.SwingBuilder
import java.awt.SystemColor
import net.miginfocom.swing.MigLayout
abstract class DefaultBriefDisplayAdaptor{
    def value
    def group
    def originBackground
    def thePanel
	def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
    	if (!this.thePanel)
    		this.thePanel=getPanel()
        return this.thePanel
    }
    def abstract getPanel()
}
