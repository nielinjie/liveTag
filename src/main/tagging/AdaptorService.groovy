package tagging
import org.apache.commons.collections.map.MultiKeyMap
//@Singleton
class AdaptorService{
    def boService=BoServiceFactory.getBoService()
	def controller
    private MultiKeyMap<String,String,Class> adaptorClasses=new MultiKeyMap<String,String,Class>()

    private Class getAdaptorClass(String adapteeType, String adaptorType){
        if (!adaptorType || !adapteeType) return null
        def re=this.adaptorClasses.get(adapteeType,adaptorType)
        return re?:this.getAdaptorClass(boService.getBoTypeParent(adapteeType),adaptorType)
    }
    def getAdaptor(Object adaptee,String adaptorType){
    	def clas=getAdaptorClass(adaptee.type,adaptorType)
		if(clas){
			def r= clas.newInstance(value:adaptee,controller:this.controller)
			return r
		}
    	return null
    }
    void registerAdaptor(String adapteeType,String adaptorType,Class adaptorClass){
        if (this.adaptorClasses.containsKey(adapteeType,adapteeType)) throw new IllegalArgumentException("duplicate adapteeType&adaptorType - ${adapteeType}&${adaptorType}")
        this.adaptorClasses.put(adapteeType,adaptorType,adaptorClass)
    }
  
}
class AdaptorServiceFactory{
    static AdaptorService getAdaptorService(){
    	return ServiceFactory.getService(AdaptorService.class)
    }
    static AdaptorService getAdaptorService(def controller){
        def re= getAdaptorService()
		if(controller)
			if(!re.controller)re.controller=controller
		return re
    }
}
