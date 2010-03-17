package tagging.ui

/**
 *
 * @author nielinjie
 */
import tagging.util.*
class UIFunctionStubProvider {
    def functionStubs=[
        new FunctionStub(keys:['briefDisplay'],guard:{o->o.respondsTo('getComponent')},memo:'get the render for bos in item list view'),
        new FunctionStub(keys:['detailDisplay'],guard:{o->o.respondsTo('getComponent')},memo:'get the render for bos in detail view')
    ]
}

