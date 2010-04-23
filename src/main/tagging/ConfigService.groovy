package tagging
import tagging.util.*
import tagging.ok.*
/**
 *
 * @author nielinjie
 */
class ConfigService {
    ObjectKeeper ok=new StupidOK(file:'/home/nielinjie/configs')
    Map configs=[:]
    def onInit(){
        ok.start()
        ok.search{true}.each{
            this.configs[it.id]=it.value
        }
        println this.configs
    }
    def close(){
        saveAllRequest()
        println this.configs
        this.configs.each{
            ok.put(new ConfigItem(id:it.key,value:it.value))
        }
        ok.close()
    }
    def saveAllRequest(){
        List fs=ServiceFactory.getService(FunctionMatrix.class).getAllFunctions('configSaveRequets')
        fs.each{
            def configs=it.getConfigs()
            configs.each{
                id,config->
                this.putConfig(id,config)
            }
        }
    }
    def getConfig(String id){
        this.configs[id]
    }
    def putConfig(String id, Object config){
        this.configs[id]=config
    }
}
class ConfigItem{
    String id
    Object value
}
class ConfigSavingRequestFunctionStubProvider{
    def functionStubs=[
        new FunctionStub(
            keys:['configSaveRequets'],
            guard:{o-> o.respondsTo('getConfigs')},
            memo:"""
                    config requests fired by other components .
                    function should respond to Map<String,Object)> getConfigs()
            """
        )
    ]
}

