import tag.*
import org.junit.*
class AdaptorTests extends GroovyTestCase{
    void testBo(){
        //register
        def adaptorService=new AdaptorService()
        adaptorService.registerBo('tag.todo','tag',TodoTag.class)
        try{
            adaptorService.registerBo('tag.todo','todo',TodoTag.class)
            fail('Should throw a IllegalArgumentException')
        }catch(IllegalArgumentException e){
            //fine
        }catch(Exception e){
            fail('Should throw a IllegalArgumentException')
        }
        def todoClass=adaptorService.getBoClass('tag.todo')
        assertEquals(todoClass,TodoTag.class)
    }
    void testAdaptor(){
        def adaptorService=new AdaptorService()
        adaptorService.registerBo('tag.todo','tag',TodoTag.class)
        adaptorService.registerAdaptor('tag','detailDisplay',MockDetailDisplayAdaptor.class)
        def todoDetailDisplayAdaptorClass=adaptorService.getAdaptorClass('tag.todo','detailDisplay')
        assertEquals(todoDetailDisplayAdaptorClass,MockDetailDisplayAdaptor.class)
    }
    @Test(expected=IllegalArgumentException.class)
    void duplicateBo(){
        def adaptorService= new AdaptorService()
        adaptorService.registerBo('tag.todo','tag',TodoTag.class)
        adaptorService.registerBo('tag.todo','anything',TodoTag.class)
    }
    @Test(expected=IllegalArgumentException.class)
    void duplicateAdaptor(){
        def adaptorService=new AdaptorService()
        adaptorService.registerAdaptor('tag','detailDisplay',MockDetailDisplayAdaptor.class)
        try{
            adaptorService.registerAdaptor('tag','briefDisplay',MockDetailDisplayAdaptor.class)
        }catch (Exception e){
            fail('Should not throw any exception')
        }
        adaptorService.registerAdaptor('tag','detailDisplay',MockDetailDisplayAdaptor.class)
    }
}
class MockDetailDisplayAdaptor{}
