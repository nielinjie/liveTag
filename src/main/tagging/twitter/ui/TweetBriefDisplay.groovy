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
import tagging.keyword.*
import tagging.keyword.ui.*
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

class TweetTagableDetailDisplay extends DefaultTagableDetailDisplayAdaptor{
    def getPanel(){
        return sb.panel(layout:new MigLayout('fill','[fill,grow]','[fill][grow,fill][fill]'),constraints:'growx,wrap'){
            sb.panel(constraints:'wrap'){
                widget(DisplayAdaptor.getAdaptor(value,'typeIconDisplay'),constraints:'')
                widget(new TimeLabel(value.createdAt.time),constraints:'')
            }
            scrollPane(constraints:'top, growx, wrap'){
                editorPane(text:this.value.text,editable:false)
            }
            //sb.panel(layout:new MigLayout('debug,insets 0'),constraints:'wrap,growx'){
            sb.menuBar(constraints:'wrap,growx'){
                def people=CreatedByTag.findCreatedBy(value)
                if(people){
                    widget(DisplayAdaptor.getAdaptor(people,'cardDisplay'))//,mouseClicked:{ServiceFactory.getService('controller').selectSearchView(createdBySearchView)})
                }
                MentionedInTag.findMetionded(value).each{
                    widget(DisplayAdaptor.getAdaptor(it,'cardDisplay'))
                }
                def kws=value.keywords
                if(kws){
                    kws.each{
                        kw->
                        //println kw.dump()
                        //widget(DisplayAdaptor.getAdaptor(new KeywordTagSearchView(keywordTag:KeywordTag.getKeywordTag(kw)),'cardDisplay'))
                        //println DisplayAdaptor.getAdaptor(KeywordTag.getKeywordTag(kw),'cardDisplay')
                        widget(DisplayAdaptor.getAdaptor(KeywordTag.getKeywordTag(kw),'cardDisplay'))
                    }
                }
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
        //return sb.panel(layout:new MigLayout('debug,fill,insets 0','[fill,left]')){
        //            def drop=jb.jideSplitButton(icon:new DelayedImageIcon(32, 32, new URL(value.imageUrl)),text:value.userName,
        //                customize: { m ->
        //                    m.removeAll()
        //                    (1..5).each{ m.add "Option $it" }
        //                },actionPerformed:{e->println e.dump()})
        // sb.menuBar{
        def createdBySearchView=new CreatedBySearchView(people:value)
        def re
        if(value.confirmed){
            re=sb.menu(icon:new DelayedImageIcon(32, 32, new URL(value.imageUrl)),text:value.userName){
                widget(DisplayAdaptor.getAdaptor(createdBySearchView,'buttonDisplay'))
                sb.menuItem('1')
            }
        }
        else{
            re=sb.menu(icon:IconManager.getIcon('twitter'),text:value.screenName){
                //widget(DisplayAdaptor.getAdaptor(createdBySearchView,'buttonDisplay'))
                sb.menuItem('1')
            }
        }
        re
        //}

        // widget(drop,constraints:'wrap')
        //            label(icon:new DelayedImageIcon(32, 32, new URL(value.imageUrl)),text:value.userName)
        //            def createdBySearchView=new CreatedBySearchView(people:value)
        //            widget(DisplayAdaptor.getAdaptor(createdBySearchView,'buttonDisplay'))
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

