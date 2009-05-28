package tagging
//@Singleton
class TaggingManager{
    private Map<UUID,String> tags=[:]
    private Map<UUID,String> tagables=[:]
    void saveTag(Tag tag){
        this.tags.put(tag.id,tag.asString())
    }
    Tag getTag(UUID id){
        return Tag.fromString(this.tags.get(id,null))
    }
    Tagable getTagable(UUID id){
        return Tagable.fromString(this.tagables.get(id,null))
    }
    List<Tag> getTagsForTagable(UUID tagableId){
    	return tags.values.findAll{
    		tagableId in it.tagables
    	}
    }
    void tagging(Tagable tagable, List<Tag> tags){
    	tags.each{
    		it.tagables<<tagable.id
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
    	println "tagable added, $tagable"
    	this.tagables[tagable.id]=tagable
    }
}
class TaggingManagerFactory{
    private static taggingManager=new TaggingManager()
    static getTaggingManager(){
        return taggingManager
    }
}

