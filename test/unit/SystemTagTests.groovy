import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at

import tagging.*
import tagging.system.*
import tagging.text.*

class SystemTagTests{
    @Test void singletonSystemTag(){
        def sts=ServiceFactory.getService(SystemTagService.class)
        sts.singletonSystemTags.put('tag.system.unread',UnreadTag.class)
        def tm=TaggingManagerFactory.getTaggingManager()
        tm.clear()
        def ta=new TextTagable(text:'tagging me')
        def ta2=new TextTagable(text:'tagging me 2')
        tm.tagging(ta,[sts.getSingletonSystemTag('tag.system.unread')])
        def find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        tm.tagging(ta2,[sts.getSingletonSystemTag('tag.system.unread')])
        find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        def tagables=tm.getTagableForTag(sts.getSingletonSystemTag('tag.system.unread'))
        at tagables.size,is(2)
        
        tm.unTagging(ta,sts.getSingletonSystemTag('tag.system.unread'))
        find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(1)
        tagables=tm.getTagableForTag(sts.getSingletonSystemTag('tag.system.unread'))
        at tagables.size,is(1)
		
		tm.unTagging(ta2,sts.getSingletonSystemTag('tag.system.unread'))
		find=tm.findTag{
            it.type=='tag.system.unread'
        }
        at find.size, is(0)
		
    }
}