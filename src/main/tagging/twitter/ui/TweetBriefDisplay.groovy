/**
 * 
 */
package tagging.twitter.ui


import tagging.*
import tagging.ui.*
import tagging.twitter.*
import tagging.util.*
import groovy.swing.*
import tagging.people.*
import net.miginfocom.swing.MigLayout
/**
 * @author nielinjie
 *
 */
class TweetTagableBriefDisplay extends DefaultBriefDisplayAdaptor{
    
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            def people=CreatedByTag.findCreatedBy(value)

            if(people){
                widget(DisplayAdaptor.getAdaptor(people,'iconDisplay'),constraints:'aligny top')
            }
            editorPane(text:this.value.text,constraints:'w 300px::,h 48px::',editable:false,opaque: false
                ,mouseClicked:{
                    event->
                    event.source=event.source.parent
                    event.source.dispatchEvent((event))
                }
            )
        }
       

    }
}

class TweetTagableDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('fillx'),constraints:'growx,wrap'){
            editorPane(text:this.value.text,editable:false,constraints:'growx, wrap')
            def people=CreatedByTag.findCreatedBy(value)

            if(people){
                widget(DisplayAdaptor.getAdaptor(people,'cardDisplay'))//,mouseClicked:{ServiceFactory.getService('controller').selectSearchView(createdBySearchView)})
            }
        }
    }
    
}
class TweetTagableTypeIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('ins 0')){
            label(icon:IconManager.getIcon('twitter'))
        }
    }
}
class TwitterPeopleBriefDisplay extends DefaultBriefDisplayAdaptor{
    def getPanel(){

        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            widget(DisplayAdaptor.getAdaptor(value,'iconDisplay'))
            editorPane(text:"${this.value.screenName} - ${this.value.userName}@twitter",constraints:'w 300px::,h 48px::',editable:false,opaque: false,mouseClicked:{
                    event->
                    event.source=event.source.parent
                    event.source.dispatchEvent((event))
                })
        }
    }
}
class TwitterPeopleCardDisplay extends DisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            label(icon:new DelayedImageIcon(48, 48, new URL(value.imageUrl)))
            def createdBySearchView=new CreatedBySearchView(people:value)
            widget(DisplayAdaptor.getAdaptor(createdBySearchView,'buttonDisplay'))
        }
    }
}
class TwitterPeopleDetailDisplay extends DefaultDetailDisplayAdaptor{

    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            label(icon:new DelayedImageIcon(48, 48, new URL(value.imageUrl)), constraints:'wrap')
            def createdBySearchView=new CreatedBySearchView(people:value)
            widget(DisplayAdaptor.getAdaptor(createdBySearchView,'buttonDisplay'))
        }
    }
}
class TwitterPeopleIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('ins 0')){
            label(icon:new DelayedImageIcon(48,48,new URL(value.imageUrl)))
          
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

