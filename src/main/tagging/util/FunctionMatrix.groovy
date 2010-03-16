package tagging.util

/**
 *
 * @author nielinjie
 */
class FunctionMatrix {
    private Conventions conventions=new Conventions().with{
        it.registerCandidate(new FindByClassNameCandidate(basePackage:'tagging'))
//        it.registerCandidate(new RegExpClassNameCandidate(basePackage:'tagging'))
        it
    }
    void registerFuctionStub(FunctionStub function){
        def sc=new SingletonCandidate(
                acceptedKeys:[['_stub'],*(function.keys).collect{[it]}],
                obj:function
            )
        conventions.registerCandidate(sc)
    }
    List<FunctionStub> getAllFunctionStubs(){
        conventions.getObjects('_stub','.*') + conventions.getObjects('_stub','.*','.*')+conventions.getObjects('_stub','.*','.*','.*')
    }
    FunctionStub getFunctionStub(String... functionKeys){
        conventions.getObjects('_stub',*functionKeys).find{true}
    }
    Object getFunction(String type,String... functionKeys){
        def re=conventions.getObjects(type,*functionKeys)
        def stub=getFunctionStub(functionKeys)
        if(!stub) throw new IllegalArgumentException("no such function stub - $functionKeys")
        re.find{
            stub?.guard.call(it)
        }

    }
    Object getAllFunctions(String... functionKeys){
        def re=conventions.getObjects('.*',*functionKeys)
        re=re.findAll{
           !(it instanceof FunctionStub)
        }
        def stub=getFunctionStub(functionKeys)
        if(!stub)  throw new IllegalArgumentException("no such function stub - $functionKeys")
        re.findAll{
            stub.guard(it)
        }
        
    }
}
class FunctionStub{
    String[] keys
    Closure guard={Object o->true}
    String memo=''
    @Override String toString(){
        "FunctionStub: keys - $keys, memo - $memo".toString()
    }
}
class AutoFunctionMatrix extends FunctionMatrix{
    AutoFunctionMatrix(){
        super()
        this.registerFuctionStub(new FunctionStub(
                keys:['functionStubProvider'],
                guard:{Object o-> o.respondsTo('getFunctionStub')},
                memo:'auto set functionStubs to function matrix'
            ))
        def funs=this.getAllFunctions('functionStubProvider')
        funs.each{
            this.registerFuctionStub(it.getFunctionStub())
        }
    }
}
