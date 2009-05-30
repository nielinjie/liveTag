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
		return sb.panel(layout:new MigLayout(),constraints:'wrap'){
			etchedBorder(parent:true)
			editorPane(text:this.value.text,constraints:'w 300px::',editable:false,opaque: false,mouseClicked:{
				event->
				event.source=event.source.parent
				event.source.dispatchEvent((event))
				})
		}
	}
	
}
class TweetDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            label(text:this.value.text)
        }
    }
    
}
class TwitterPeopleBriefDisplay extends DefaultBriefDisplayAdaptor{
	def getPanel(){
		return sb.panel{
			label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)),mouseClicked:{
                event->
                event.source=event.source.parent
                event.source.dispatchEvent((event))
                })
		}
	}
}
class TwitterPeopleDetailDisplay extends DefaultDetailDisplayAdaptor{
	def getPanel(){
        return new SwingBuilder().panel{
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)))
        }
    }
}
