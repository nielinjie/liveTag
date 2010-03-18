/**
 * 
 */
package tagging.twitter.ui


import tagging.*
import tagging.ui.*
import tagging.twitter.*
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
            def aS=AdaptorServiceFactory.getAdaptorService()
            def people=CreatedByTag.findCreatedBy(value)

            if(people){
                widget(aS.getAdaptor(people,'iconDisplay').getComponent(),constraints:'aligny top')
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
class TweetTypeIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('ins 0')){
            label(icon:IconManager.getIcon('twitter'))
        }
    }
}
class TwitterPeopleBriefDisplay extends DefaultBriefDisplayAdaptor{
    def getPanel(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            widget(aS.getAdaptor(value,'iconDisplay').getComponent())
            editorPane(text:"${this.value.screenName} - ${this.value.userName}@twitter",constraints:'w 300px::,h 48px::',editable:false,opaque: false,mouseClicked:{
                    event->
                    event.source=event.source.parent
                    event.source.dispatchEvent((event))
                })
        }
    }
}
class TwitterPeopleDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)), constraints:'wrap')
            def aS=AdaptorServiceFactory.getAdaptorService()
            widget(aS.getAdaptor(value.searchView,'briefDisplay').getComponent())
        }
    }
}
class TwitterPeopleIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('ins 0')){
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)))
        }
    }
}

class TwitterImporterBriefDisplay extends ImporterBriefDisplayAdaptor{
}
class TwitterSearchViewProvides{
    def searchViewItems=[
        new SearchViewItem(
            order:10,
            group:'Category',
            searchView:new TwitterImporter(
                username:'nielinjie',password:'790127',
                name:'Twitter Importer',
                description:'Sample Twitter Importer',interval:300
            )
        )
    ]
}
class TwitterMeta{
    def static provideMeta(){
//        def aS=AdaptorServiceFactory.getAdaptorService()
//        aS.registerAdaptor('tagable.twitter.tweet','briefDisplay',TweetBriefDisplay.class)
//        aS.registerAdaptor('tagable.twitter.tweet','detailDisplay',TweetDetailDisplay.class)
//        aS.registerAdaptor('tagable.twitter.tweet','typeIconDisplay',TweetTypeIconDisplay.class)
//
//        aS.registerAdaptor('tagable.twitter.people','briefDisplay',TwitterPeopleBriefDisplay.class)
//        aS.registerAdaptor('tagable.twitter.people','detailDisplay',TwitterPeopleDetailDisplay.class)
//        aS.registerAdaptor('tagable.twitter.people','iconDisplay',TwitterPeopleIconDisplay.class)
    }
}

