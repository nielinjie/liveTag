/**
 * 
 */
package tagging.twitter


import tagging.*
import groovy.swing.*
import net.miginfocom.swing.MigLayout
/**
 * @author nielinjie
 *
 */
class TweetBriefDisplay extends DefaultBriefDisplayAdaptor{

	def getPanel(){
		return new SwingBuilder().panel(layout:new MigLayout(),constraints:'wrap'){
			etchedBorder(parent:true)
			editorPane(text:this.value.text,constraints:'w 300px::',editable:false,opaque: false)
		}
	}
	
}
class TweetDetailDisplay extends DefaultDetailDisplayAdaptor{

    def getPanel(){
        return new SwingBuilder().panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:this.value.text)
        }
    }
    
}
