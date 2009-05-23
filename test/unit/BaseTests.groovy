import tag.*
import org.junit.*
import static org.junit.Assert.*
import junit.framework.TestCase
class BaseTests {
    @BeforeClass public static void ka(){
        AdaptorServiceFactory.getAdaptorService().registerBo('tag.todo','tag',TodoTag.class)
    }
    @Test void testManager(){
        tagTest(Tag.class,'tag')
    }
    @Test void testTodoTag(){
        tagTest(TodoTag.class,'tag.todo')
        tagTest(new TodoTag(deadline:new Date(),done:false))
    }
    private tagTest(Tag tag){
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        assertTrue(tag.id != null && tag.id instanceof UUID)
        tag.name='testing'
        def id=tag.id
        tm.saveTag(tag)
        def newTag=tm.getTag(id)
        assertTrue("${tag.dump()}, ${newTag.dump()}",tag.equals(newTag))

    }
    private tagTest(Class tagClass,String type){
        Tag tag=tagClass.newInstance()
        tag.type=type
        tagTest(tag)
    }

}
