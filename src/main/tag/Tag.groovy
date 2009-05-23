import groovy.xml.*
import groovy.util.*
import org.apache.commons.beanutils.*
import com.thoughtworks.xstream.XStream
class Tag extends WithID{
    String type
    String name
    static fromString(String string){
        def XStream xs=new XStream()
        def type=((string=~/.*<type>(.*)<\/type>.*/)[0][1])
        println type
        def clazz=TaggingManagerFactory.getTaggingManager().findTagClass(type)
        println clazz
        xs.alias('tag',clazz)
        return xs.fromXML(string)
       /* if(!string)return null
        def tag=new XmlSlurper().parseText(string)
        Tag re=TaggingManagerFactory.getTaggingManager().findTagClass(tag.type.text()).newInstance()
        re.id=UUID.fromString(tag.id.text())
        re.type=tag.type.text()
        println re.type
        def allProperties=tag.property
        allProperties.each{
            def pro=re.metaClass.getMetaProperty(it.@name.text())
            println pro
            if (it.text()!=''){
                println pro.getType()
                def da=ConvertUtils.convert(it.text(),pro.getType())
                println da.class
                re."${it.@name.text()}"=da
            }
        }
        println re.type
        return re*/
    }
    String asString(){
        def XStream xs=new XStream()
        xs.alias('tag',this.class)
        def re= xs.toXML(this)
        println re
        return re
        /*
        def writer=new StringWriter()
        def xml=new MarkupBuilder(writer)
        xml.tag{
            type(this.type)
            id(this.id)
            this.metaClass.properties.each{
                pr->
                if (!(pr.name in ['class','metaClass','type','id'])){
                    def value= this."${pr.name}"
                    println "${pr.name} - ${value}"
                    property(name:pr.name,ConvertUtils.convert(value))
                }
            }
        }
        println writer.toString()
        return writer.toString()*/
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
