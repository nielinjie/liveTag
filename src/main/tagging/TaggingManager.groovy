package tagging
import tagging.contact.*
import tagging.util.*
import tagging.ok.*
//@Singleton
class TaggingManager{
    ObjectKeeper ok
    private singletonTagCache=[:]
    static Class objectKeeperClass
    private def listeners=[]
    @Lazy def sync={
        new Sync(tm:this).with{
            //taggingManager is used as singleten, so contactManager not.
            cm=ContactManagerFactory.getNewContactManager()
            it
        }
    }()
    void clear(){
    	this.ok.clear()
    }
    void saveTag(Tag tag){
        this.ok.put(tag)
    }
    Tag getTag(UUID id){
        def re= this.ok.get(id)
        assert re==null || re instanceof Tag
        return re
    }
    Tagable getTagable(UUID id){
        def re= this.ok.get(id)
        assert re==null || re instanceof Tagable
        return re
    }
    //TODO opnizaion
    boolean hasTagOnTagable(Tagable tagable,String tagType){
    	this.getTagsForTagable(tagable.id).any{
            it.type==tagType
    	}
    }
    List<Tagable> getTagableForTag(Tag tag){
    	return tag.tagables.collect{this.ok.get(it)}
    }
    List<Tag> getTagsForTagable(UUID tagableId){
    	return this.findTag{
            tagableId in it.tagables
    	}
    }
    
    List<Tag> findTag(Closure filter){
    	return this.ok.search{
            it instanceof Tag && filter(it)
    	}
    }
    void tagging(Tagable tagable, Tag tag){
        tagging(tagable,[tag])
    }
    void tagging(Tagable tagable, List<Tag> tags){

    	if(!(this.getTagable(tagable.id)) ){

            this.addTagable(tagable)
    	}
    	tags.each{
            if(!(this.getTag(it.id))){
                this.saveTag(it)
            }
            it.tagables<<tagable.id
            it.onTagging(tagable)
    	}
    	
    }
    void unTagging(Tagable tagable, Tag tag){
    	tag.tagables.remove(tagable.id)
        if(tag.tagables.empty){
            this.removeTag(tag)
        }
    }
    void removeTag(Tag tag){
    	this.ok.remove(tag.id)
    }
    List<Tag> getTagsForTagable(Tagable tagable){
        return this.getTagsForTagable(tagable.id)
    }
    List<Tagable> findTagable(Closure condition){
        return this.ok.search{
            (it instanceof Tagable) && condition(it)
        }
    }
    void addTagable(Tagable tagable){
    	println "tagable added, ${tagable.dump()}"
    	this.ok.put(tagable)
        if(tagable.respondsTo('onAdded')){
            tagable.onAdded()
        }
        this.listeners.each{
            if(it.respondsTo('onTagableAdded',Tagable.class))
            it.onTagableAdded(tagable)
        }
    }
    void fromOther(List<BO> bos){
    	bos.each{
            if(it instanceof Tagable){
                this.addTagable(it)
            }
    	}
    }
    void onInit(){
    	println 'tagging manager in oninit method'
        def l=ServiceFactory.getService(FunctionMatrix.class).getAllFunctions('taggingManagerListener')
        l.each{
            println l
        }
        this.listeners.addAll(l)
        if(this.ok==null){
            if(this.objectKeeperClass==null)
            this.ok=new StupidOK()//this is for test only
            else
            this.ok=this.objectKeeperClass.newInstance()
        }
        this.ok.start()
        this.listeners.each{
            if(it.respondsTo('onTaggingManagerStart',TaggingManager.class)) it.onTaggingManagerStart(this)
        }
    }
    void close(){
    	this.ok.close()
    }
    Tag getSingletonTag(Class clazz){
        if(this.singletonTagCache[clazz])
        return this.singletonTagCache[clazz]
        else{
            def findTag=TaggingManagerFactory.getTaggingManager().findTag{
                it.class==clazz
            }
            if(!findTag){
                def tag=clazz.newInstance()
                TaggingManagerFactory.getTaggingManager().saveTag(tag)
                this.singletonTagCache[clazz]=tag
            }else{
            	this.singletonTagCache[clazz]=findTag[0]
            }
            return this.singletonTagCache[clazz]
        }
    }
}
class TaggingManagerFactory{
    static TaggingManager getNewTaggingManager(){
    	return ServiceFactory.getNewService(TaggingManager.class)
    }
    static TaggingManager getTaggingManager(){
        return ServiceFactory.getService(TaggingManager.class)
    }
}

class TaggingManagerListenerFunctionStubProvider{
    def functionStubs=[
        new FunctionStub(
            keys:['taggingManagerListener'],
            guard:{o->
                o.respondsTo('onTagableAdded',Tagable.class) ||
                o.respondsTo('onTaggingManagerStart',TaggingManager.class) 

            },
            memo:"""get the listeners to taggingManager's tagable added.
                    function should respond to one or more following
                        onTagableAdded(Tagable)
                        onTaggingManagerStart(TaggingManager)
                    """
        ),
    ]
}