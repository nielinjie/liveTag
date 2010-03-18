package tagging.ui

/**
 *
 * @author nielinjie
 */
import tagging.util.*
class UIFunctionStubProvider {
    def functionStubs=[
        new FunctionStub(
            keys:['briefDisplay'],
            guard:{o->o.respondsTo('getComponent')},
            memo:"""get the render for bos in item list view.
                    function should respond to java.swing.pane getComponent()"""
        ),
        new FunctionStub(
            keys:['detailDisplay'],
            guard:{o->o.respondsTo('getComponent')},
            memo:"""get the render for bos in detail view.
                    function should respond to java.swing.pane getComponent()"""

        ),
        new FunctionStub(
            keys:['quickTagRequests'],
            guard:{o->o.respondsTo('getQuickTagRequests')},
            memo:"""get the quick tag requests.
                    function should respond to List<QuickTagRequest> getQuickTagRequests()"""

        ),
        new FunctionStub(
            keys:['quickTagProvides'],
            guard:{o->o.respondsTo('getQuickTagProvides')},
            memo:"""get the quick tag provided.
                    function should respond to List<QuickTagProvide> getQuickTagProvides()"""

        ),
    ]
}

