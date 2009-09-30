package tagging.util
import com.thoughtworks.xstream.XStream
class XML{
    static Object fromXML(String xml){
        if(!xml)return null
        def XStream xs=new XStream()
        return xs.fromXML(xml).with{
            it
        }
    }
    static String toXML(Object obj){
        def XStream xs=new XStream()
        String re=''
        try{
            re= xs.toXML(obj)}
        catch(Exception e){
        	e.printStackTrace()
        }
        return re
    }
}