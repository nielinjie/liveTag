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

class  MentionedInTag extends LinkTag{
    static PeopleTagable findMetionded(Tagable tagable){
        def linkeds=LinkTag.findLinked(tagable,{it instanceof MentionedInTag})
        if (linkeds)return linkeds[0]
        return null
    }
    static List<Tagable> findMetiondedIn(PeopleTagable people){
    	return LinkTag.findReversesLinked(people,{it instanceof MentionedInTag})
    }
}
class CreatedBySearchView extends SearchView{
    def name='Created by People'
    PeopleTagable people
    @Lazy def description={"Tagables created by %{people.userName}"}()
    def sortComparator={a,b->0}
    def condition={
        CreatedByTag.findCreate(people)
    }
}