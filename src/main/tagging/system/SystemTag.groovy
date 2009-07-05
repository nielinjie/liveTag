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
class UpdatedTag extends SystemTag{
	String type='tag.system.updated'
	Date updatedTime
}
class UnreadTag extends SystemTag{
	String type='tag.system.unread'
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