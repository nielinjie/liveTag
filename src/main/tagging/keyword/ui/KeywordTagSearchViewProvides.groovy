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
class KeywordTagCardDisplay extends DisplayAdaptor{
    def getPanel(){
        return sb.menu(icon:IconManager.getIcon('sharp'),text:value.keyword){
            widget(DisplayAdaptor.getAdaptor(new KeywordTagSearchView(keywordTag:value),'buttonDisplay'))
        }
    }
}
class KeywordTagDetailDisplay extends DefaultDetailDisplayAdaptor{
    def getPanel(){
        sb.panel(){
            label(text:value.keyword)
            widget(DisplayAdaptor.getAdaptor(new KeywordTagSearchView(keywordTag:value),'buttonDisplay'))
        }
    }
}
class KeywordTagSearchViewBriefDisplay extends SearchViewBriefDisplayAdaptor{}
class KeywordTagSearchViewButtonDisplay extends SearchViewButtonDisplayAdaptor{}



