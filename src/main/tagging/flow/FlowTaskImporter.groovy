package tagging.flow
import tagging.*
class FlowTaskImporter extends Importer{
	void onTimer(){
		def tm=TaggingManagerFactory.getTaggingManager()
		def commitedTasks=tm.findTagable(){
			it.type=='tagable.flow.task' && it.commited && !it.runned
		}
		commitedTasks.each{
			def runtime=tm.findTagable(){
				
			}
			it.activity.run(runtime)
			it.runned=true
		}
	}
}
class FlowServerImporter extends Importer{
	void onTime(){
		def tm=TaggingManagerFactory.getTaggingManager()
		def flows=tm.findTag(){
			it.type=='tag.flow' && !it.finished
		}
		flows.each{
			def activity =it.runtime.getNextActivity()
			def flowTaskTag=new FlowTaskTagable(flowTagId:it.id,session:it.runtime.session,activityName:activity.name)
			println flowTaskTag.dump()
		}
	}
}
class FlowTaskSearchView extends SearchView{
	
}