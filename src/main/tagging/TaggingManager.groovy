package tagging
//@Singleton
class TaggingManager{
    private Map<UUID,Tag> tags=[:]
    private Map<UUID,Tagable> tagables=[:]
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
    	
    	tags.each{
    		if(!(it.id in this.tags.keySet())){
    			this.saveTag(it)
    		}
    		it.tagables<<tagable.id
			it.onTagging(tagable)
    	}
    	
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
}
class TaggingManagerFactory{
    private static taggingManager=new TaggingManager()
    static getNewTaggingManager(){
    	return new TaggingManager()
    }
    static getTaggingManager(){
        return taggingManager
    }
}

