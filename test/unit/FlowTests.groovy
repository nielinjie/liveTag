import org.junit.*
import tagging.*
import tagging.text.*
import tagging.flow.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
class FlowTests{
	private Flow flow=null
	@Before void createFlow(){
		flow=new FlowBuilder().build{
            flow{
                start(to:'create')
                activity(name:'create',role:'coder',to:'review'){
                    obj.create()
                }
                activity(name:'review',role:'reviewer'){
                	review()
                    if(pass){to='end'}
                    else{to='update'}
                }
                activity(name:'update',role:'coder',to:'review'){
                	update()
                }
                end(name:'end')
            }
        }
	}
	@Test void testBuilder(){
		assertTrue(this.flow instanceof Flow)
	}
	@Test void runFlow(){
		def codeReviewRequest=new CodeReviewRequest()
		def runtime=this.flow.begin(codeReviewRequest,new Session(review:{pass=true;println 'reviewing'},update:{println 'updateing'}))
		assertNotNull(runtime)
		def ac=runtime.nextActivity
		assertNotNull(ac)
		assertThat(ac.name, is('create'))
		runtime.run()
		def ac2=runtime.nextActivity
		assertNotSame(ac,ac2)
		assertThat(ac2.name, is('review'))
		runtime.run()
		assertThat(runtime.nextActivity.name, is('end'))
	}
	@Test void runNotPassedFlow(){
        def codeReviewRequest=new CodeReviewRequest()
        def runtime=this.flow.begin(codeReviewRequest,new Session(updated:false,review:{pass=updated;println 'reviewing, not pass'},update:{updated=true;println 'updateing'}))
        assertNotNull(runtime)
        def ac=runtime.nextActivity
        assertNotNull(ac)
        assertThat(ac.name, is('create'))
        runtime.run()
        def ac2=runtime.nextActivity
        assertNotSame(ac,ac2)
        assertThat(ac2.name, is('review'))
        runtime.run()
        assertThat(runtime.nextActivity.name, is('update'))
		runtime.run()
		assertThat runtime.nextActivity.name, is('review')
    }
	@Test void withTM(){
		def tm=TaggingManagerFactory.getTaggingManager()
		tm.clear()
		def ta=new TextTagable(text:'I am a flow object')
		tm.addTagable(ta)
		def dsl="""
			flow{
                start(to:'create')
                activity(name:'create',role:'coder',to:'review'){
                   println 'I am created'
                }
                activity(name:'review',role:'reviewer'){
                    println 'reviewer'
					pass=true
                    if(pass){to='end'}
                    else{to='update'}
                }
                activity(name:'update',role:'coder',to:'review'){
                    println 'update'
                }
                end(name:'end')
            }
		"""
		def flowD=new FlowDefinition(dsl:dsl)
		def flowTag=new FlowTag(definition:flowD)
		tm.tagging(ta,[flowTag])
		//def runtime=flowTag.runtime
		def flowImporter=new FlowServerImporter(stop:true)
		flowImporter.onTime()
		
	}
}
class CodeReviewRequest{
	def create(){
		println 'I am created'
	}
}