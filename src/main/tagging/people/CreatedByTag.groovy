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
    static List<PeopleTagable> findMetionded(Tagable tagable){
        def linkeds=LinkTag.findLinked(tagable,{it instanceof MentionedInTag})
        //if (linkeds)return linkeds[0]
        return linkeds
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
//class MentionedInManagerListener{
//    def onTaggingManagerStart(TaggingManager tm){
//        //TODO add clean logic
//    }
////        def emptyKeywordTags=[]
////        tm.findTag{
////            it instanceof KeywordTag
////        }.each{
////            if(!(tm.getTagableForTag(it)))emptyKeywordTags<<it
////        }
////        emptyKeywordTags.each{
////            tm.removeTag(it)
////        }
//    def onTagableAdded(Tagable tagable){
//        if(tagable.respondsTo('getMentions')){
//            tagable.Mentions.each{
//                KeywordTag.addKeywordTag(tagable,it)
//            }
//        }
//    }
//}