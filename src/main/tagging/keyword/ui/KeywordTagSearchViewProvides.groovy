package tagging.keyword.ui
import tagging.*
import tagging.keyword.*
import tagging.ui.*
/**
 *
 * @author nielinjie
 */
class KeywordTagSearchViewProvides {
    def getSearchViewItems(){
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        tm.findTag{
            it instanceof KeywordTag
        }.collect{
            new SearchViewItem(group:'Keyword',order:10,searchView:new KeywordTagSearchView(keywordTag:it))
        }
    }
}
class KeywordTagSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{}

