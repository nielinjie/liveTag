package tagging.timeStream

import tagging.*
/**
 *
 * @author nielinjie
 */
class TimeStreamSearchView extends SearchView{
    def type='searchView.timeStream'
    def name='TimeStream'
    def description='Bos streamed by time'
    def sortComparator={a,b->-a."${Timable.boTypes[a.type]}".time<=>-b."${Timable.boTypes[a.type]}".time}
    def condition={
        def tm=TaggingManagerFactory.getTaggingManager()
        def tbo=tm.findTagable{
            it.type in Timable.boTypes.keySet()
        }
        tbo
    }
}
class Timable{
    static def boTypes=['tagable.twitter.tweet':'createdAt']
}