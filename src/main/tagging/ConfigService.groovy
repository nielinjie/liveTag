package tagging
import tagging.util.*
/**
 *
 * @author nielinjie
 */
class ConfigService {
    ObjectKeeper ok=new StupidOK(file:'./configs')
    def onInit(){
        ok.start()
    }
    def close(){
        saveAllRequest()
        ok.close()
    }
    def saveAllRequest(){
        List fs=ServiceFactory.getService(FunctionMatrix.class).getAllFunctions('configRequets')
        fs.each{
            def configs=it.getConfig()
            configs.each{
                id,config->
                this.putConfig(id,config)
            }
        }
    }
    def getConfig(String id){
        ok.get(id)
    }
    def putConfig(String id, Object config){
        ok.put(id,config)
    }
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

