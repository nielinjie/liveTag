/**
 * 
 */
package tagging.keyword


import tagging.*
/**
 * @author nielinjie
 * This kind of tag is the 'tranditional' key word tag
 */
public class KeywordTag extends Tag{
    String type='tag.keyword'
    String keyword
    static void addKeywordTag(Tagable tagable, String keyword){
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        tm.tagging(tagable,getKeywordTag(keyword))
    }
    static KeywordTag getKeywordTag(String keyword){
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        def existed=tm.findTag {
            it instanceof KeywordTag && it.keyword==keyword
        }
        if(!existed){
            existed=new KeywordTag(keyword:keyword)
            tm.saveTag(existed)
        }else{
            existed=existed[0]
        }
        existed
    }
}
class KeywordTagSearchView extends SearchView{
    def keywordTag
    @Lazy def name={"Keyword - ${keywordTag.keyword}"}()
    @Lazy def description={"Keyword - ${keywordTag.keyword}"}()
    def condition={
        TaggingManager tm=TaggingManagerFactory.getTaggingManager()
        def re=tm.getTagableForTag(keywordTag)
        re
    }
}

class KeywordTaggingManagerListener{
    def onTaggingManagerStart(TaggingManager tm){
        def emptyKeywordTags=[]
        tm.findTag{
            it instanceof KeywordTag
        }.each{
            if(!(tm.getTagableForTag(it)))emptyKeywordTags<<it
        }
        emptyKeywordTags.each{
            tm.removeTag(it)
        }
    }
    def onTagableAdded(Tagable tagable){
        if(tagable.respondsTo('getKeywords')){
            tagable.keywords.each{
                KeywordTag.addKeywordTag(tagable,it)
            }
        }
    }
}