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
                def fm=ServiceFactory.getService(FunctionMatrix.class)
                def w=fm.getFunction(people.class.simpleName,'iconDisplay')
                w.setValue(people)
                widget(w.getComponent(),constraints:'aligny top')
            }
            editorPane(text:this.value.text,constraints:'w 300px::,h 48px::',editable:false,opaque: false,mouseClicked:{
                    event->
                    event.source=event.source.parent
                    event.source.dispatchEvent((event))
                })
        }
       

    }
}

class TweetTagableDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            label(text:this.value.text)
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
        def fm=ServiceFactory.getService(FunctionMatrix.class)

        return sb.panel(layout:new MigLayout(),constraints:'wrap'){
            def w=fm.getFunction(value.class.simpleName,'iconDisplay')
            w.setValue(value)
            widget(w.getComponent())
            editorPane(text:"${this.value.screenName} - ${this.value.userName}@twitter",constraints:'w 300px::,h 48px::',editable:false,opaque: false,mouseClicked:{
                    event->
                    event.source=event.source.parent
                    event.source.dispatchEvent((event))
                })
        }
    }
}
class TwitterPeopleDetailDisplay extends DefaultDetailDisplayAdaptor{
    def fm=ServiceFactory.getService(FunctionMatrix.class)

    def getPanel(){
        return sb.panel(layout:new MigLayout()){
            label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)), constraints:'wrap')
            //def aS=AdaptorServiceFactory.getAdaptorService()
            //            def w=fm.getFunction(value.searchView.class.simpleName,'briefDisplay')
            //            w.setValue(value.searchView)
            //            widget(w.getComponent())
        }
    }
}
class TwitterPeopleIconDisplay extends DefaultIconDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('ins 0')){
            //label(icon:new FixedSizeImageIcon(48, 48, new URL(value.imageUrl)))
            def label=new RemoteImageLabel(value.imageUrl)
            widget(label)
//            sb.doLater{
//                label.url=value.imageUrl
//            }
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

