import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at

import tagging.*
import tagging.system.*
import tagging.text.*

class SystemTagTests{
    @Test void singletonSystemTag(){
        def tm=TaggingManagerFactory.getTaggingManager()
        tm.clear()
		BoServiceFactory.getBoService().with{
        	it.clear()
            it.registerBo('tag.system.unread','tag',UnreadTag.class)
            it.registerBo('tag.system.star','tag',StarTag.class)
        }
        def ta=new TextTagable(text:'tagging me')
        def ta2=new TextTagable(text:'tagging me 2')
        tm.tagging(ta,[tm.getSingletonTag('tag.system.unread')])
        def find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        tm.tagging(ta2,[tm.getSingletonTag('tag.system.unread')])
        find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        def tagables=tm.getTagableForTag(tm.getSingletonTag('tag.system.unread'))
        at tagables.size,is(2)
        
        tm.unTagging(ta,tm.getSingletonTag('tag.system.unread'))
        find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        tagables=tm.getTagableForTag(tm.getSingletonTag('tag.system.unread'))
        at tagables.size,is(1)
		
		tm.unTagging(ta2,tm.getSingletonTag('tag.system.unread'))
		find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(0)
		
    }
}