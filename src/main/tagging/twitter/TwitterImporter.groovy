/**
 * 
 */
package tagging.twitter

import tagging.*
import tagging.people.*
import groovy.util.slurpersupport.*
import groovy.swing.*

/**
 * @author nielinjie
 *
 */
public class TwitterImporter extends Importer{
    XmlSlurper slurper = new XmlSlurper()
    String type='importer.twitter'
    String urlBase='http://twitter.com'
    String username
    String password
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
                    tweets[it.id.text()]=['text':it.text.text(),'id':it.id.text()]
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
//            if(taManager.findTagable({tagable->tagable.bid==it['id']})){
//                return
//            }
            def tweet=new TweetTagable(text:it['text'])
            tm.addTagable(tweet)
            //add keyword tags
            //add person tagable
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
}
class DMTagable extends Tagable{
    def type='tagable.twitter.DM'
}
class TwitterPeople extends PeopleTagable{
    def type='tagable.twitter.people'
}
class TwitterMeta{
    def static provideMeta(){
        def aS=AdaptorServiceFactory.getAdaptorService()
        aS.registerAdaptor('importer.twitter','briefDisplay',TwitterImporterBriefDisplay.class)
		aS.registerAdaptor('tagable.twitter.tweet','briefDisplay',TweetBriefDisplay.class)
		aS.registerAdaptor('tagable.twitter.tweet','detailDisplay',TweetDetailDisplay.class)
    }
}
