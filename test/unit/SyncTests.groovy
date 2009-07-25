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
		def sy=new Sync()
		def r1=sy.getBOs(new NewerThanHint(date:date))
		at r1.empty,is(true)
		keyword.keyword='new'
		r1=sy.getBOs(new NewerThanHint(date:date))
		at r1.size,is(1)
	}
}