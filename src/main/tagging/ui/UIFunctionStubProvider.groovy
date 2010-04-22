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
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for bos in item list view.
                    function should respond to java.swing.pane getComponent()"""
        ),
        new FunctionStub(
            keys:['buttonDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for bos in button style.
                    function should respond to java.swing.menuItem getComponent()"""
        ),
        new FunctionStub(
            keys:['detailDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for bos in detail view.
                    function should respond to java.swing.pane getComponent()"""

        ),
        new FunctionStub(
            keys:['cardDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for bos in detail view, to display a assiciate.
                    function should respond to java.swing.memu getComponent()"""

        ),
        new FunctionStub(
            keys:['typeIconDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for bos in detail view.
                    function should respond to java.swing.pane getComponent()"""

        ),
        new FunctionStub(
            keys:['iconDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
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
        new FunctionStub(
            keys:['searchViewProvides'],
            guard:{o->o.respondsTo('getSearchViewItems')},
            memo:"""get search view provided.
                    function should respond to List<SearchViewItem> getSearchViewItems()"""

        ),
        new FunctionStub(
            keys:['magicTextProvides'],
            guard:{o->o.respondsTo('getMagicTextProvides')},
            memo:"""get the magic text function provided.
                    function should respond to List<MagicTextProvide> getMagicTextProvides()"""

        ),
       new FunctionStub(
            keys:['configDisplay'],
            guard:{o->
                o.respondsTo('getComponent') \
                && o.respondsTo('setValue')
            },
            memo:"""get the render for object in config dialog.
                    function should respond to java.swing.panel getComponent()"""
        )
    ]
}

