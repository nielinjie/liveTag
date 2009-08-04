package tagging
import tagging.contact.*
//@Singleton
class TaggingManager{
    private Map<UUID,Tag> tags=[:]
    private Map<UUID,Tagable> tagables=[:]
    @Lazy def sync={
        new Sync(tm:this).with{
        	//taggingManager is used as singleten, so contactManager not.
            cm=ContactManagerFactory.getNewContactManager()
			it
        }
    }()
    void clear(){
    	this.tags.clear()
		this.tagables.clear()
    }
    void saveTag(Tag tag){
        this.tags.put(tag.id,tag)
    }
    Tag getTag(UUID id){
        return this.tags.get(id,null)
    }
    Tagable getTagable(UUID id){
        return this.tagables.get(id,null)
    }
    List<Tagable> getTagableForTag(Tag tag){
    	return tag.tagables.collect{this.getTagable(it)}
    }
    List<Tag> getTagsForTagable(UUID tagableId){
    	return this.findTag{
    		tagableId in it.tagables
    	}
    }
    List<Tag> findTag(Closure filter){
    	return tags.values().findAll(filter)
    }
    void tagging(Tagable tagable, List<Tag> tags){
    	if(!(tagable.id in this.tagables.keySet())){
    		this.addTagable(tagable)
    	}
    	tags.each{
    		if(!(it.id in this.tags.keySet())){
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
    	this.tags.remove(tag.id)
    }
    List<Tag> getTagsForTagable(Tagable tagable){
        return this.getTagsForTagable(tagable.id)
    }
    List<Tagable> findTagable(Closure condition){
        this.tagables.values().findAll(
            condition
        )
    }
    void addTagable(Tagable tagable){
    	println "tagable added, ${tagable.dump()}"
    	this.tagables[tagable.id]=tagable
    }
    void fromOther(List<BO> bos){
    	bos.each{
    		if(it instanceof Tagable){
    			this.addTagable(it)
    		}
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

