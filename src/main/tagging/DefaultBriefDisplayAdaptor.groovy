package tagging
import groovy.swing.SwingBuilder
import java.awt.SystemColor
import net.miginfocom.swing.MigLayout
abstract class DefaultBriefDisplayAdaptor{
    def value
    def group
    def originBackground
    def thePanel
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
    	if (!this.thePanel)
    		this.thePanel=getPanel()
        return this.thePanel
    }
    def onSelected={
        this.originBackground=this.thePanel.background
        this.thePanel.background=SystemColor.activeCaption
    }
    def onUnselected={
        this.thePanel.background=this.originBackground
    }
    def abstract getPanel()
}
