class TaggingManager{
    private Map<UUID,String> tags=[:]
    private Map<String,Class> tagClasses=['base':Tag.class,'base.todo':TodoTag.class]
    void save(Tag tag){
        this.tags.put(tag.id,tag.asString())
    }
    Tag get(UUID id){
        return Tag.fromString(this.tags.get(id,null))
    }
    Class findTagClass(String type){
        return this.tagClasses.get(type,Tag.class)
    }
}
class TaggingManagerFactory{
    private static TaggingManager instance=new TaggingManager()
    static getTaggingManager(){
        return instance
    }
}
