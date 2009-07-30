/**
 * 
 */
package tagging.twitter.ui


import tagging.*
import tagging.ui.*
import groovy.swing.*
import tagging.people.*
import net.miginfocom.swing.MigLayout
/**
 * @author nielinjie
 *
 */
class TweetBriefDisplay extends DefaultBriefDisplayAdaptor{
    
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            etchedBorder(parent:true)
            def aS=AdaptorServiceFactory.getAdaptorService()
            def people=CreatedByTag.findCreatedBy(value)
            println people.dump()
            if(people){
                widget(aS.getAdaptor(people,'iconDisplay').getComponent())
            }
            editorPane(text:this.value.text,constraints:'w 300px::,h 48px::',editable:false,opaque: false,mouseClicked:{
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
        return new SwingBuilder().panel(layout:new MigLayout()){
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)), constraints:'wrap')
			def aS=AdaptorServiceFactory.getAdaptorService()
			widget(aS.getAdaptor(value.searchView,'briefDisplay').getComponent())
        }
    }
}
class TwitterPeopleIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return new SwingBuilder().panel{
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)))
        }
    }
}
class TwitterImporterBriefDisplay extends ImporterBriefDisplayAdaptor{
}
class TwitterMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer.twitter','briefDisplay',TwitterImporterBriefDisplay.class)
        aS.registerAdaptor('tagable.twitter.tweet','briefDisplay',TweetBriefDisplay.class)
        aS.registerAdaptor('tagable.twitter.tweet','detailDisplay',TweetDetailDisplay.class)
        aS.registerAdaptor('tagable.twitter.people','briefDisplay',TwitterPeopleBriefDisplay.class)
        aS.registerAdaptor('tagable.twitter.people','detailDisplay',TwitterPeopleDetailDisplay.class)
        aS.registerAdaptor('tagable.twitter.people','iconDisplay',TwitterPeopleIconDisplay.class)
    }
}
