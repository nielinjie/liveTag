/**
 * 
 */
package tagging.twitter

import tagging.*
import tagging.people.*
import groovy.util.slurpersupport.*
import groovy.swing.*
import java.text.*
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
	def condition={TaggingManagerFactory.getTaggingManager().findTagable({obj->obj instanceof TweetTagable})}
    def sortComparator={a,b->a.createdAt<=>b.createAt}
    Authenticator authenticator=null
    GPathResult slurpAPIStream(String url) {
        def text = ""
        try {
            text = new URL(url).openStream().text
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
//        tweets.each{
//            println it.key
//            println it.value
//        }
        //TODO: borrow import code from Greet
        //download from twitter
        def tm=TaggingManagerFactory.getTaggingManager()
        tweets.values().each{
			tw->
            if(tm.findTagable({tagable->tagable.bid==tw['id']})){
                return
            }
            def tweet=new TweetTagable(text:tw['text'],author:tw['userid'],createdAt:twitterFormat.parse(tw.created_at))
            tm.addTagable(tweet)
            //add keyword tags
            //add person tagable and createdby tag
			def createdBy=new CreatedByTag(from:tweet,fromPropertyName:'author',toType:'tagable.twitter.people')
			println createdBy.dump()
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
class TwitterImporterBriefDisplay extends ImporterBriefDisplayAdaptor{
}
class TweetTagable extends Tagable{
    def type='tagable.twitter.tweet'
	def text
	def author
	Date createdAt
}
class DMTagable extends Tagable{
    def type='tagable.twitter.DM'
	
}
class TwitterPeople extends PeopleTagable{
    def type='tagable.twitter.people'
	def userName
	def screenName
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
