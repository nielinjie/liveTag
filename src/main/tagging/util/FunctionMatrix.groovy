package tagging.util

/**
 *
 * @author nielinjie
 */
class FunctionMatrix {
    static private Conventions conventions=new Conventions()
    static void registerFuctionStub(FunctionStub function){
        conventions.registerCandidate(new SingletonCandidate(
                acceptedKeys:[['stub'],function.keys],
                obj:function
            ))
    }
    static List<FunctionStub> getAllFunctionStubs(){
        conventions.getObjects('stub','.*') + conventions.getObjects('stub','.*','.*')+conventions.getObjects('stub','.*','.*','.*')
    }
    static FunctionStub getFunctionStub(String... functionKeys){
        conventions.getObjects('stub',*functionKeys)
    }
    static Object getFunction(String type,String... functionKeys){
        def all=getAllFunctions(type,functionKeys)
        all?all[0]:null
    }
    static Object getAllFunctions(String type,String... functionKeys){
        def re=conventions.getObjects(type,*functionKeys)
        def stub=getFunctionStub(functionKeys)
        re.findAll{
            stub.guard(it)
        }
        re
    }
}
class FunctionStub{
    String[] keys
    Closure guarde={true}
    String memo=''
}

