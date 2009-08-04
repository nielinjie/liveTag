package tagging.util
import com.thoughtworks.xstream.XStream
class XML{
	static Object fromXML(String xml){
		if(!xml)return null
        def XStream xs=new XStream()
        return xs.fromXML(xml).with{
			println it
			it
		}
	}
	static String toXML(Object obj){
		 def XStream xs=new XStream()
	        def re= xs.toXML(obj)
			println re
	        return re
	}
}