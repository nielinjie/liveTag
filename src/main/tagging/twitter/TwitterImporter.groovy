/**
 * 
 */
package tagging.twitter

import tagging.*
import tagging.people.*

import groovy.swing.*

/**
 * @author nielinjie
 *
 */
public class TwitterImporter extends Importer{
	String type='importer.twitter'
	def onTimer(){
		//TODO: borrow import code from Greet
	}
	
}
class TwitterImporterBriefDisplay extends ImporterBriefDisplayAdaptor{
	
	@Override
	def getPanel(){
		return new SwingBuilder().panel(super.getPanel()){
			button('Update Now')
		}
	}
}
class TweetTagable extends Tagable{
	def type='tagable.twitter.tweet'
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
    }
}
