package tagging
import tagging.contact.*
import tagging.util.*
//@Singleton
class TaggingManager{
	ObjectKeeper ok
	static Class objectKeeperClass
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
    }
    void fromOther(List<BO> bos){
    	bos.each{
    		if(it instanceof Tagable){
    			this.addTagable(it)
    		}
    	}
    }
    void onInit(){
    	 if(this.ok==null){
    		 if(this.objectKeeperClass==null)
    			 this.ok=new StupidOK()//this is for test only
    		 else
    			 this.ok=this.objectKeeperClass.newInstance()
         }
    	 this.ok.start()
    }
    void close(){
    	this.ok.close()
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
class StupidOK implements ObjectKeeper{
	def file='./savedObjs'
	def objs=[:]
	Object get(def id){
		return objs[id]
	}
    List search(def filter){
    	return objs.values().findAll(filter)
    }
    void clear(){
    	this.objs.clear()
    }
    void put(Object obj){
    	this.objs[obj.id]=obj
    }
    void remove(def id){
    	this.objs.remove(id)
    }
    void close(){
    	def s=XML.toXML(this.objs)
		println s
		File f=new File(this.file)
    	f.text=s
    }
    void start(){
    	File f=new File(this.file)
    	def s=f.text
		this.objs=XML.fromXML(s)
		println this.objs
    }
}
