import tagging.*
import tagging.text.*
import tagging.todo.*
import tagging.people.*
import org.junit.*
class AdaptorTests extends GroovyTestCase{
    void testBo(){
        //register
        def boService=new BoService()
        boService.registerBo('tag.todo','tag',TodoTag.class)
        try{
            boService.registerBo('tag.todo','todo',TodoTag.class)
            fail('Should throw a IllegalArgumentException')
        }catch(IllegalArgumentException e){
            //fine
        }catch(Exception e){
            fail('Should throw a IllegalArgumentException')
        }
        def todoClass=boService.getBoClass('tag.todo')
        assertEquals(todoClass,TodoTag.class)
    }
    void testAdaptor(){
        def adaptorService=new AdaptorService()
        def boService=new BoService()
        adaptorService.boService=boService
        boService.registerBo('tag.todo','tag',TodoTag.class)
        adaptorService.registerAdaptor('tag','detailDisplay',MockDetailDisplayAdaptor.class)
        def todoDetailDisplayAdaptorClass=adaptorService.getAdaptor(new TodoTag(),'detailDisplay').class
        assertEquals(todoDetailDisplayAdaptorClass,MockDetailDisplayAdaptor.class)
    }
    @Test(expected=IllegalArgumentException.class)
    void duplicateBo(){
        def boService= new BoService()
        boService.registerBo('tag.todo','tag',TodoTag.class)
		boadaptorService.registerBo('tag.todo','anything',TodoTag.class)
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
class MockDetailDisplayAdaptor{
	def value
	def controller
}
