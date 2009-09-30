/**
 * 
 */
package tagging.people
import tagging.*
import tagging.system.*

/**
 * @author nielinjie
 *
 */
class CreatedByTag extends LinkTag{
    def type='tag.people.createdBy'
    static PeopleTagable findCreatedBy(Tagable tagable){
        def linkeds=LinkTag.findLinked(tagable,{it instanceof CreatedByTag})
        if (linkeds)return linkeds[0]
        return null
    }
    static List<Tagable> findCreate(PeopleTagable people){
    	return LinkTag.findReversesLinked(people,{it instanceof CreatedByTag})
    }
}
