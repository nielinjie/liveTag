import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at
import tagging.*
import tagging.text.*
import tagging.keyword.*
class SyncTests{
    @Test void hint(){
        def tm=TaggingManagerFactory.getTaggingManager()
        tm.clear()
        def text=new TextTagable(text:'haha')
        def keyword=new KeywordTag(keyword:'old')
        tm.tagging(text,[keyword])
        sleep(100)
        def date=new Date()
        def sy=new Sync(tm:tm)
        def r1=sy.getBOs(new NewerThanHint(date:date))
        at r1.empty,is(true)
        keyword.keyword='new'
        r1=sy.getBOs(new NewerThanHint(date:date))
        at r1.size,is(1)
        text.text='haha again'
        r1=sy.getBOs(new NewerThanHint(date:date))
        at r1.size,is(2)
    }
    @Test void sync(){
    	def tm=TaggingManagerFactory.getTaggingManager()
		tm.clear()
		Contact contact=new MockContact()
    	def sync=new Sync(tm:tm,cm:new ContactManager(contacts:[contact]))
    	def list=sync.request(new NewerThanHint(date:new Date()))
		at list.size, is(1)
		at list[0].text,is('requested')
		sync.push([new TextTagable(text:'pushed')])
		list=contact.tm.findTagable{
    		true
    	}
    	at list.size, is(1)
		at list[0].text,is('pushed')
    }
}
class MockContact extends MemoryContact{
	def tm=TaggingManagerFactory.getNewTaggingManager()
	def sync=new MockSync(tm:tm)
	MockContact(Map att){
		super()
        tm.clear()
		this.syncPort=new MemorySyncPort(sync:sync)
	}
}
class MockSync extends Sync{
	List<BO> onRequest(Hint hint){
		return [new TextTagable(text:'requested')]
	}
}