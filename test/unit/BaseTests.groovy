import tag.*
class BaseTests extends GroovyTestCase{
    void testManager(){
        tagTest(Tag.class,'base')
    }
    void testTodoTag(){
        tagTest(TodoTag.class,'base.todo')
        tagTest(new TodoTag(deadline:new Date(),done:false))
    }
    private tagTest(Tag tag){
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        assert (tag.id != null && tag.id instanceof UUID)
        tag.name='testing'
        def id=tag.id
        tm.save(tag)
        def newTag=tm.get(id)
        assert (tag.equals(newTag)):"${tag.dump()}, ${newTag.dump()}"

    }
    private tagTest(Class tagClass,String type){
        Tag tag=tagClass.newInstance()
        tag.type=type
        tagTest(tag)
    }

}
