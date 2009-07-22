package tagging.flow
import tagging.*


//run on 'server' side, to create task. and also, it accept commited task to run.
class FlowServerImporter extends Importer{
    void onTime(){
        def tm=TaggingManagerFactory.getTaggingManager()
        //create new tasks
        def flows=tm.findTag(){
            it.type=='tag.flow' && !it.finished && it.needCreateTask
        }
        flows.each{
            def activity =it.runtime.getNextActivity()
            println "I will creat task for actvity - ${activity.dump()}"
            println "session - ${it.runtime.session.dump()}"
			//copy session and trim obj from it
			def clientSession=it.runtime.session.copy()
			clientSession.merge([obj:null])
            def flowTaskTagable=new FlowTaskTagable(flowTagId:it.id,session:clientSession,activityName:activity.name,objId:it.runtime.obj.id)
            tm.addTagable(flowTaskTagable)
            it.needCreateTask=false
        }
        //find and run tasks which commited from clients
        def commitedTasks=tm.findTagable(){
            it.type=='tagable.flow.task' && it.commited && !it.runned
        }
        commitedTasks.each{
            task->
            def flowTags=tm.findTag(){
                it.id==task.flowTagId
            }
            assert flowTags.size==1
            def flowTag=flowTags[0]
            def runtime=flowTag.runtime
            assert runtime.nextActivity.name==task.activityName
            runtime.session.merge(task.session)
            runtime.run()
            task.runned=true
            flowTag.needCreateTask=true
        }
    }
}
//run on 'client' side, to find assigned task.
class FlowTaskSearchView extends SearchView{
    def type='searchView.flow.task'
    def condition={
        def tasks=TaggingManagerFactory.getTaggingManager().findTagable{
            it.type=='tagable.flow.task' && it.commited==false 
        }
        tasks.each{
			def objId=it.objId
			it.session.obj=TaggingManagerFactory.getTaggingManager().getTagable(objId)
        }
        tasks
    }
}
class FlowTaskHistorySearchView extends SearchView{
    def type='searchView.flow.taskHistory'
    def flowTagId
    def condition={
        TaggingManagerFactory.getTaggingManager().findTagable{
            it.type=='tagable.flow.task' && it.flowTagId==this.flowTagId
        }
    }
}