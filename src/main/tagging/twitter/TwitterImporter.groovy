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
    static final DateFormat twitterFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy")
    XmlSlurper slurper = new XmlSlurper()
    String type='importer.twitter'
    String urlBase='http://twitter.com'
    String username
    String password
    String proxyHostName='localhost'
    int proxyPort=8000
    Proxy proxy=new Proxy(Proxy.Type.HTTP ,new InetSocketAddress(proxyHostName,proxyPort))
    def condition={TaggingManagerFactory.getTaggingManager().findTagable({obj->obj instanceof TweetTagable})}
    def sortComparator={a,b->-a.createdAt.time<=>-b.createdAt.time}
    Authenticator authenticator=null
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
            def createdBy=new CreatedByTag(from:tweet,fromPropertyName:'author',toType:'tagable.twitter.people')
            createdBy.link({
                    new TwitterPeople(bid:tw['userid'],
                        userName:tw['username'],screenName:tw['userScreenName'],
                        imageUrl:tw['userImage'])
                })
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
        //TODO parse keywords here
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
    def comfirmed=false// is this people found on twitter site?
    def imageUrl
    @Lazy def searchView={
        //    		def name
        //		    def description
        //		    def condition
        //		    def sortComparator
    	new SearchView(name:'Tweeted',description:'Tweeted by this people',condition:{CreatedByTag.findCreate(this)},sortComparator:{a,b->0
            })
    }()
}

