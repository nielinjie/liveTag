/**
 * defined some tag those use didnot care about
 */
package tagging.system

import tagging.*



/**
 * @author nielinjie
 *
 */

public class SystemTag extends Tag{
    
}
class SystemTagService{
    private cache=[:]
    def singletonSystemTags=[:]//['tag.system.unread':UnreadTag.class,'tag.system.star':StarTag.class]
    
}

class UpdatedTag extends SystemTag{
    static theType='tag.system.updated'
    String type=theType
    Date updatedTime
}
class UnreadTag extends SystemTag{
    static toggle(Tagable tagable){
        def tm=TaggingManagerFactory.getTaggingManager()
        def tag=tm.getSingletonTag('tag.system.unread')
        if(!tm.hasTagOnTagable(tagable,'tag.system.unread')){
            tm.tagging(tagable,[tag])
        }else{
            tm.unTagging(tagable,tag)
        }
    }
    String type='tag.system.unread'
}
class StarTag extends SystemTag{
    static toggle(Tagable tagable){
        def tm=TaggingManagerFactory.getTaggingManager()
        def tag=tm.getSingletonTag('tag.system.star')
        if(!tm.hasTagOnTagable(tagable,'tag.system.star')){
            tm.tagging(tagable,[tag])
        }else{
            tm.unTagging(tagable,tag)
        }
    }
    String type='tag.system.star'
}
class ImporterByTag extends SystemTag{
    String type='tag.system.importedBy'
    String importId//the id of importer
}
class UnreadSearchView extends SearchView{
	def type='searchView.unread'
    def name='Unread'
    def description='All unread tagable'
    def condition={
        def tm =TaggingManagerFactory.getTaggingManager()
		tm .findTagable{
        	tm.hasTagOnTagable(it,'tag.system.unread')
        }
    }
    def sortComparator={
        a,b->a.version>b.version
    }
}
class StarSearchView extends SearchView{
	def type='searchView.star'
    def name='Star'
    def description='All stared tagable'
    def condition={
        def tm =TaggingManagerFactory.getTaggingManager()
        tm .findTagable{
            tm.hasTagOnTagable(it,'tag.system.star')
        }
    }
    def sortComparator={
        a,b->a.version>b.version
    }
}
abstract class LinkTag extends Tag{
    UUID toId
    String fromPropertyName
    Tagable from
    String toType
    def link(Closure newToTagable){
        def tm=TaggingManagerFactory.getTaggingManager()
        def toTagable=tm.findTagable({obj->
                obj.type==toType && obj.bid==from."${this.fromPropertyName}"})
        if(!toTagable){
            toTagable=newToTagable(from)
            if(toTagable.bid==null)
                toTagable.bid=from."${this.fromPropertyName}"
            tm.addTagable(toTagable)
        }else{
            toTagable=toTagable[0]
        }
        this.toId=toTagable.id
        tm.tagging(from,[this])
    }
    static List<Tagable> findLinked(Tagable from,Closure tagFilter=null){
        def re=[]
        def tm=TaggingManagerFactory.getTaggingManager()
        def tagings=tm.getTagsForTagable(from.id)
        def linkeds= tagings.find{
            (it instanceof LinkTag && (tagFilter?tagFilter(it):true)) 
        }
        re.addAll(linkeds.collect{linked->tm.getTagable(linked.toId)})
        return re
    }
    static List<Tagable> findReversesLinked(Tagable to, Closure tagFilter=null){
        def re=[]
        def tm=TaggingManagerFactory.getTaggingManager()
        def tags=tm.findTag({it instanceof LinkTag && it.toId==to.id && (tagFilter?tagFilter(it):true)})
        tags.each{
            tag->
            re.addAll(tm.getTagableForTag(tag))
        }
        return re
    }
}

