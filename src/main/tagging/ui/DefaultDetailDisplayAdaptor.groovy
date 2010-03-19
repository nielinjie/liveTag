package tagging.ui
import groovy.swing.SwingBuilder
import javax.swing.*
abstract class DefaultDetailDisplayAdaptor{
    def value
    def controller
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
    def controller
    static SwingBuilder sb=new SwingBuilder()
    def getComponent(){
        return getPanel().with{
            it.opaque=true
            it
        }
    }
    def abstract getPanel()
}