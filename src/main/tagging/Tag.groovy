package tagging
import groovy.xml.*
import groovy.util.*
import com.thoughtworks.xstream.XStream
class Tag extends BO{
    String type='tag'
    Set<UUID> tagables=new HashSet<UUID>()
    def onTagging(Tagable tagable){
    }
}
class BO extends WithID{
    Version version=new Version()
    //update version function
    void setProperty(String name,value){
        if(this."$name"==null && value!=null||value!=this."$name"){
            this.version.update()
        }
        super.setProperty(name,value)
    }
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
class Version{
    def date=new Date()
    void update(){
        this.date=new Date()
    }
    boolean newer(Version b){
        return this.date.time>b.date.time
    }
    boolean newer(Date date){
        return this.date.time>date.time
    }
    boolean equals(Object b){
        if(b ==null || ! b instanceof Version)
        return false
        return b.date==this.date
    }
    Version clone(){
        def r=new Version()
        r.date=this.date
        return r
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
