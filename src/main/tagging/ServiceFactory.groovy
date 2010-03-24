package tagging
class ServiceFactory{
    private static instances=[:]
    static getNewService(Class clazz){
        return  clazz.newInstance().with{
            if(it.respondsTo('onInit')){
                it.onInit()
            }
            it
        }
    }
    static getService(Class clazz){
        if(instances.get(clazz)==null){
            instances[clazz]=clazz.newInstance().with{
                if(it.respondsTo('onInit')){
                    it.onInit()
                }
                it
            }
        }
        return instances[clazz]
    }
    static setService(Class clazz, Object instance){
        instances[clazz]=instance
    }
    static setService(String key,Object instance){
        instances[key]=instance
    }
    static getService(String key){
        return instances[key]
    }
}