import org.junit.*
import tagging.*
import tagging.system.*
import static org.junit.Assert.*
import static org.junit.Assert.assertThat as at
import static org.hamcrest.CoreMatchers.*
class ViewFrameTests{
	@Test void history(){
		History h=new History()
		h.add('a')
		h.add('b')
		h.add('c')
		at h.queue, is(['a','b','c'])
		at h.hasBack(), is(true)
		at h.back(), is('b')
		at h.back(), is('a')
		at h.queue, is(['a','b','c'])
		at h.hasBack(), is(false)
		at h.hasForward(), is(true)
		at h.to(0), is('a')
		at h.hasBack(), is(false)
		at h.hasForward(), is(true)
		at h.to(2), is('c')
		at h.hasForward() , is(false)
		at h.hasBack(), is(true)
		at h.to(1), is('b')
		at h.queue, is(['a','b','c'])
		h.add('d')
		at h.queue, is(['a','b','d'])
		h.add('e')
		at h.queue, is(['a','b','d','e'])
	}
	@Test void viewFrame(){
		def v=new ViewFrame(allContent:[1,2,3,4,5],deltaSize:2)
		at v.getDelta(),is([1,2])
		at v.getDelta(),is([3,4])
		at v.getRequested() , is([1,2,3,4])
		at v.hasMore(), is(true)
		at v.getDelta(), is([5])
		at v.hasMore(), is(false)
		v.reset()
		at v.getDelta(),is([1,2])
        at v.getDelta(),is([3,4])
        at v.getRequested() , is([1,2,3,4])
		at v.hasMore(), is(true)
        at v.getDelta(), is([5])
        at v.hasMore(), is(false)
	}
}