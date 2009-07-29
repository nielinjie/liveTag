package tagging
class ServiceFactory{
    private static instances=[:]
    static getNewService(Class clazz){
        return  clazz.newInstance()
    }
    static getService(Class clazz){
        if(instances.get(clazz)==null){
            instances[clazz]=clazz.newInstance()
        }
        return instances[clazz]
    }
}