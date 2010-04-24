/**
 * 
 */
package tagging.twitter

import tagging.*
import tagging.people.*
import groovy.util.slurpersupport.*
import groovy.swing.*
import java.text.*
import java.net.*
/**
 * @author nielinjie
 *
 */
public class TwitterImporter extends Importer{
    transient static final DateFormat twitterFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy")
    transient @Lazy XmlSlurper slurper = {new XmlSlurper()}()
    String type='importer.twitter'
    String urlBase='http://twitter.com'
    String username
    String password
    String proxyHostName='localhost'
    int proxyPort=8000
    transient @Lazy Proxy proxy={new Proxy(Proxy.Type.HTTP ,new InetSocketAddress(proxyHostName,proxyPort))}()
    transient @Lazy def condition={{n->TaggingManagerFactory.getTaggingManager().findTagable({obj->obj instanceof TweetTagable})}}()
    transient @Lazy def sortComparator={{a,b->-a.createdAt.time<=>-b.createdAt.time}}()
    transient Authenticator authenticator=null
    GPathResult slurpAPIStream(String url) {
        def text = ""
        try {

            text = new URL(url).openConnection(this.proxy).inputStream.text
            synchronized (slurper) {
                return slurper.parse(new StringReader(text))
            }
        } catch (Exception e) {
            System.err.println text
            throw e
        }
    }
    def onTimer(){
        if(!authenticator){
            this.authenticator=([getPasswordAuthentication : {
                        return new PasswordAuthentication(this.username, this.password as char[])
                    }
                ] as Authenticator)
            Authenticator.setDefault(this.authenticator)
        }
        
        def tweets=[:]
        slurpAPIStream(
                "$urlBase/statuses/friends_timeline.xml?count=100"
        ).status.each {
            tweets[it.id.text()]=[
						 'text':it.text.text(),
						 'id':it.id.text(),
						 'userid':it.user.id.text(),
						 'created_at':it.created_at.text(),
						 'username':it.user.name.text(),
						 'userScreenName':it.user.screen_name.text(),
						 'userImage':it.user.profile_image_url.text()
            ]
        }
        def tm=TaggingManagerFactory.getTaggingManager()
        tweets.values().each{
            tw->
            if(tm.findTagable({tagable->tagable.bid==tw['id']})){
                return
            }
            def tweet=new TweetTagable(text:tw['text'],author:tw['userid'],createdAt:twitterFormat.parse(tw.created_at),bid:tw['id'])
            tm.addTagable(tweet)
            //add keyword tags
            //add person tagable and createdby tag
            def createPeople=tm.findTagable{
                it instanceof TwitterPeople && (it.bid==tw['userid'] || it.screenName==tw['userScreenName'])
            }?.with{
                it.size()>0?it[0]:null
            }
            if(createPeople && !createPeople.confirmed){
                createPeople.with{
                    it.bid=tw['userid']
                    it.confirmed=true
                    it.userName=tw['username']
                    it.imageUrl=tw['userImage']
                }
            }
            createPeople=createPeople?:new TwitterPeople(bid:tw['userid'],confirmed:true,
                userName:tw['username'],screenName:tw['userScreenName'],
                imageUrl:tw['userImage'])
            def createdBy=new CreatedByTag()
            createdBy.link(tweet,createPeople)
            tweet.mentions.unique().each{
                mentionName->
                def mentionedPeople=tm.findTagable{
                    it instanceof TwitterPeople && it.screenName==mentionName
                }?.with{
                    it.size()>0?it[0]:null
                }
                mentionedPeople=mentionedPeople?:new TwitterPeople(confirmed:false, screenName:mentionName)
                new MentionedInTag().link(tweet,mentionedPeople)
            }
            //add system tags
            //updated
            //unread
        }  
    }

    
}

class TweetTagable extends Tagable{
    def type='tagable.twitter.tweet'
    def text
    def author
    Date createdAt
    def getKeywords(){
        (text=~/#(\w*+)/).collect{
            it[1]
        }
    }
    def getMentions(){
        (text=~/@(\w*+)/).collect{
            it[1]
        }
    }
    //TODO parse metioned peoples.
}
class DMTagable extends Tagable{
    def type='tagable.twitter.DM'
	
}
class TwitterPeople extends PeopleTagable{
    def type='tagable.twitter.people'
    def userName
    def screenName
    def confirmed=false// is this people found on twitter site?
    def imageUrl
    @Lazy def searchView={
    	new SearchView(name:'Tweeted',description:'Tweeted by this people',condition:{CreatedByTag.findCreate(this)},sortComparator:{a,b->0
            })
    }()
}

