package tagging.flow
class FlowBuilder{
    def build(closure){
        closure.delegate=this
        closure()
    }
    def flow(closure){
        def re=new Flow()
        closure.delegate=re
        closure()
        return re
    }
}
class Session extends Expando{
    Session(Map atts){
    	super(atts)
    }
}
class Flow{
    def acts=[:]
    def runtimes=[:]
    def start(att){
        def re=new StartActivity(name:'start')
        this.acts['start']=re
        if(att.to)re.to=att.to
    }
    def activity(att,closure){
        def re=new Activity(closure:closure,name:att.name)
        this.acts[att.name]=re
        if(att.to)re.to=att.to
        if(att.roles)re.roles=att.roles.split(',').collect{new Role(name:it)}
    }
    def end(att){
        this.acts[att.name]=new EndActivity(name:att.name)
    }
    def begin(Object obj, session){
        if(!session.obj)
            session.obj=obj
        def re=new FlowRuntime(flow:this,obj:obj,session:session,nextActivity:this.acts.start)
        this.runtimes[obj]=re
        //to activity AFTER start
        re.run()
        return re
    }
    def taskList(Role role){
        this.runtime.values().findAll{
            it.nextActivity.roles.contains(role)
        }
    }
    def runtime(Object obj){
        this.runtime[obj]
    }
}

class Activity{
    def name
    def closure
    def to
    def roles=[]
    def run( session){
        session.to=null
        closure.delegate=session
        closure()
    }
}
class EndActivity extends Activity{}
class StartActivity extends Activity{
    @Override def run( session){
    }
}

class Role{
    def name
}
class FlowRuntime{
    def flow
    def obj
    def session
    def nextActivity=null
    def run(){
        this.nextActivity.run(this.session)
        if(this.nextActivity.to){
            this.nextActivity=flow.acts[this.nextActivity.to]
        }else
        if(session.to){
            this.nextActivity=flow.acts[session.to]
        }
    }
}