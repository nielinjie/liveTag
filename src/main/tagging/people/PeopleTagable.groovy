/**
 * 
 */
package tagging.people
import tagging.*


/**
 * @author nielinjie
 *
 */
public class PeopleTagable extends Tagable{
    String type='tagable.people'
}
class PeopleSearchView extends SearchView{
    def type='searchView.people'
    def name='People'
    def description='All known people'
    def sortComparator={a,b->a.userName<=>b.userName}
    def condition={
        def tm=TaggingManagerFactory.getTaggingManager()
        tm.findTagable{
            it.type==~/tagable\..*\.people/
        }
    }
}