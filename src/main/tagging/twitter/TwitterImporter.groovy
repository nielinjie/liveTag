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
	def condition={obj->obj instanceof TweetTagable}
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
			println it
            if(tm.findTagable({tagable->tagable.bid==it['id']})){
                return
            }
            def tweet=new TweetTagable(text:it['text'],createdAt:twitterFormat.parse(it.created_at))
            tm.addTagable(tweet)
            //add keyword tags
            //add person tagable and createdby tag
			if(!tm.findTagable({obj->obj.type=='tagable.twitter.people' && obj.bid==it['userid']})){
				def people=new TwitterPeople(bid:it['userid'],
						userName:it['username'],screenName:it['userScreenName'],
						imageUrl:it['userImage'])
				tm.addTagable(people)
				tm.tagging(tweet,[new CreatedByTag(authorBid:people.bid,authorId:people.id)])
			}
            //add system tags
            //updated
            //createby
            //unread
        }  
    }
    
}
class TwitterImporterBriefDisplay extends ImporterBriefDisplayAdaptor{
    
    @Override
    def getPanel(){
        return new SwingBuilder().panel(super.getPanel()){ button('Update Now') }
    }
}
class TweetTagable extends Tagable{
    def type='tagable.twitter.tweet'
	def text
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
}
class TwitterMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer.twitter','briefDisplay',TwitterImporterBriefDisplay.class)
		aS.registerAdaptor('tagable.twitter.tweet','briefDisplay',TweetBriefDisplay.class)
		aS.registerAdaptor('tagable.twitter.tweet','detailDisplay',TweetDetailDisplay.class)
		aS.registerAdaptor('tagable.twitter.people','briefDisplay',TwitterPeopleBriefDisplay.class)
        aS.registerAdaptor('tagable.twitter.people','detailDisplay',TwitterPeopleDetailDisplay.class)
    }
}
