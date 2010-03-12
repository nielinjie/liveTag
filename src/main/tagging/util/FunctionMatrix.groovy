package tagging.util

/**
 *
 * @author nielinjie
 */
class FunctionMatrix {
    private Conventions conventions=new Conventions().with{
        it.registerCandidate(new FindByClassNameCandidate(basePackage:'tagging'))
        it
    }
    void registerFuctionStub(FunctionStub function){
        conventions.registerCandidate(new SingletonCandidate(
                acceptedKeys:[['stub'],function.keys],
                obj:function
            ))
    }
    List<FunctionStub> getAllFunctionStubs(){
        conventions.getObjects('stub','.*') + conventions.getObjects('stub','.*','.*')+conventions.getObjects('stub','.*','.*','.*')
    }
    FunctionStub getFunctionStub(String... functionKeys){
        conventions.getObjects('stub',*functionKeys)
    }
    Object getFunction(String type,String... functionKeys){
        def re=conventions.getObjects(type,*functionKeys)
        println re
        def stub=getFunctionStub(functionKeys)
        re.find{
            stub.guard.call(it)
        }

    }
    Object getAllFunctions(String... functionKeys){
        def re=conventions.getObjects('.*',*functionKeys)
        def stub=getFunctionStub(functionKeys)
        re.findAll{
            stub.guard(it)
        }
        
    }
}
class FunctionStub{
    String[] keys
    Closure guard={Object o->true}
    String memo=''
}

