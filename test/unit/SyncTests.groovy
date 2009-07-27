import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at
import tagging.*
import tagging.contact.*
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
    	def sync=tm.sync
		println sync
		sync.cm=new ContactManager(contacts:[contact])
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
    @Test void xmlrpc(){
    	def bs=BoServiceFactory.getBoService()
        bs.registerBo('tagable.text','tagable',TextTagable.class)
    	def tm1=TaggingManagerFactory.getTaggingManager()
        tm1.clear()
		def tm2=TaggingManagerFactory.getNewTaggingManager()
		tm2.clear()
        Contact contact=new LanContact().with{
    		it.ip='localhost'
			it.port=7927
			it
    	}
        def sync1=tm1.sync
		sync1.cm=new ContactManager(contacts:[contact])
    	def sync2=tm2.sync
    	sync2.ports=['rpc':new XMLRPCSyncPort(sync:sync2)]
        sync2.ports.each{
        	it.value.start()
        }
        def list=sync1.request(new NewerThanHint(date:new Date()))
        at list.size, is(1)
        at list[0].text,is('on request')
        sync1.push([new TextTagable(text:'pushed')])
        list=tm2.findTagable{
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