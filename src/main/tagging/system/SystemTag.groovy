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
    SystemTag getSingletonSystemTag(type){
        if(cache[type])return cache[type]
        Class clazz=singletonSystemTags[type]
        if(clazz==null)
            throw new RuntimeException()
        else{
            def findTag=TaggingManagerFactory.getTaggingManager().findTag{
                it.type==type
            }
            if(!findTag){
                def tag=clazz.newInstance()
                TaggingManagerFactory.getTaggingManager().saveTag(tag)
                cache[type]=tag
            }else{
                cache[type]=findTag[0]
            }
            return cache[type]	
        }
    }
}
class SingletonSystemTag extends SystemTag{
    static def getInstance(){
        def tm=TaggingManagerFactory.getTaggingManager()
        def find=tm.findTag{
            it.type==theType
        }
        if(!(find.empty)){
            
        }
    }
}
class UpdatedTag extends SystemTag{
    static theType='tag.system.updated'
    String type=theType
    Date updatedTime
}
class UnreadTag extends SystemTag{
    String type='tag.system.unread'
}
class StarTag extends SystemTag{
    String type='tag.system.star'
}
class ImporterByTag extends SystemTag{
    String type='tag.system.importedBy'
    String importId//the id of importer
}
abstract class LinkTag extends Tag{
    UUID toId
    String fromPropertyName
    Tagable from
    String toType
    def link(Closure newToTagable){
        def tm=TaggingManagerFactory.getTaggingManager()
        def toTagable=tm.findTagable({obj->obj.type==toType && obj.bid==from."${this.fromPropertyName}"})
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