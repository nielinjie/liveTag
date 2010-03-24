package tagging.ui
import groovy.swing.SwingBuilder
import javax.swing.*
abstract class DefaultDetailDisplayAdaptor extends DisplayAdaptor{
    
    def getUnknownPanel(){
    	sb.panel(){
    		label(text:'Unknown')
    	}
    }
}
abstract class DefaultIconDisplayAdaptor extends DisplayAdaptor{
   
}