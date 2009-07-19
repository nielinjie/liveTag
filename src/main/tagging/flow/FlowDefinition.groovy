package tagging.flow
import tagging.*
class FlowDefinition{
	String dsl
	Flow flow
	def begin(obj,session){
		def shell=new GroovyShell(this.getClass().getClassLoader())
		def string="import tagging.flow.*\nnew FlowBuilder().build{\n${dsl}\n}"
		println string
		this.flow=shell.run(string,'FLowDSL',[])
		println this.flow
		this.flow.begin(obj,session)
	}
}
class FlowTag extends Tag{
	def type='tag.flow'
	def definition
	def needCreateTask=false
	def runtime
	@Override def onTagging(Tagable tagable){
		runtime=definition.begin(tagable,new Session())
		this.needCreateTask=true
	}
	def getFinished(){
		runtime.getNextActivity() instanceof EndActivity
	}
}
//Only this will deliver to 'clients'
class FlowTaskTagable extends Tagable{
	def type='tagable.flow.task'
	def flowTagId
	def activityName
	Boolean runned=false
	Boolean commited=false
	Session session=null
	String getBid(){
		return "$flowTagId-$activityName-${session.stepId}"
	}
}