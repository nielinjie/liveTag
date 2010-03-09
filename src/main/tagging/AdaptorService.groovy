package tagging
import org.apache.commons.collections.map.MultiKeyMap
//@Singleton
class AdaptorService{
    def boService=BoServiceFactory.getBoService()
    def controller
    private MultiKeyMap<String,String,Object> adaptorClasses=new MultiKeyMap<String,String,Object>()

    Object getAdaptor(String adapteeType, Object adaptee,String adaptorType){
        if (!adaptorType || !adapteeType) return null
        def re=this.adaptorClasses.get(adapteeType,adaptorType)
        def classOrPropertype= re?:this.getAdaptor(boService.getBoTypeParent(adapteeType),adaptee,adaptorType)
        if (!classOrPropertype) return null
        if (!(classOrPropertype instanceof Class)){
            return classOrPropertype.with{
                it.value=adaptee
                it.controller=this.controller
                it// return when register as a propertype
            }
        }else{
            return classOrPropertype.newInstance(value:adaptee,controller:this.controller)
        }
    }
    
    def getAdaptor(Object adaptee,String adaptorType){
    	return this.getAdaptor(adaptee.type,adaptee,adaptorType)
    }
    void registerAdaptor(String adapteeType,String adaptorType,Object adaptorClassOrPropertype){
        if (this.adaptorClasses.containsKey(adapteeType,adapteeType)) throw new IllegalArgumentException("duplicate adapteeType&adaptorType - ${adapteeType}&${adaptorType}")
        this.adaptorClasses.put(adapteeType,adaptorType,adaptorClassOrPropertype)
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
