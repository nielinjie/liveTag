package tagging
import groovy.xml.*
import groovy.util.*
import com.thoughtworks.xstream.XStream
class Tag extends BO{
    String type='tag'
}
class BO extends WithID{
    //String type
    String name
    static fromString(String string){
        if(!string)return null
        def XStream xs=new XStream()
        def type=((string=~/.*<type.*?>(.*)<\/type>.*/)[-1][1])
        def clazz=BoServiceFactory.getBoService().getBoClass(type)
        xs.alias('tag',clazz)
        return xs.fromXML(string)
    }
    String asString(){
        def XStream xs=new XStream()
        xs.alias('tag',this.class)
        def re= xs.toXML(this)
        println re
        return re
    }
    boolean equals(Object b){
        if (!b || !(b instanceof BO))
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
