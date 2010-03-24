package tagging.ui
import groovy.swing.SwingBuilder
import javax.swing.*
abstract class DefaultDetailDisplayAdaptor{
    def value
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel().with{
            it.opaque=true
            it
        }
    }
    def abstract getPanel()
    def getUnknownPanel(){
    	sb.panel(){
    		label(text:'Unknown')
    	}
    }
}
abstract class DefaultIconDisplayAdaptor{
    def value
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel().with{
            it.opaque=true
            it
        }
    }
    def abstract getPanel()
}