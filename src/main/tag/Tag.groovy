import groovy.xml.*
import groovy.util.*
class Tag extends WithID{
    String type
    String name
    static fromString(String string){
        string.metaClass.asType={
            clazz->
            println 'in my mixin'
            delegate.asType(clazz)
        }
        if(!string)return null
        def tag=new XmlSlurper().parseText(string)
        Tag re=TaggingManagerFactory.getTaggingManager().findTagClass(tag.type.text()).newInstance()
        re.id=UUID.fromString(tag.id.text())
        re.type=tag.type.text()
        def allProperties=tag.property
        allProperties.each{
            re."${it.@name}"=it.@value.asType
        }
        return re
    }
    String asString(){
        def writer=new StringWriter()
        def xml=new MarkupBuilder(writer)
        xml.tag{
            type(this.type)
            id(this.id)
            this.metaClass.properties.each{
                if (!(it.name in ['class','metaClass','type','id']))
                    property(name:it.name,value:this."${it.name}")
            }
        }
        println writer.toString()
        return writer.toString()
    }
    boolean equals(Object b){
        if (!b || !(b instanceof Tag))
            return false
        return b.id==this.id && b.name==this.name && b.type==this.type
    }
}
class WithID{
    private UUID id
    void setId(UUID id){
        this.id=id
    }
    UUID getId(){
        if (!this.id){
            this.id=UUID.randomUUID()
        }
        return this.id
    }
}
