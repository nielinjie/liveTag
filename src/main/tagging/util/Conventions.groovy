package tagging.util
import org.apache.log4j.*
/**
 *
 * @author nielinjie
 */
class Conventions {
    List<Candidate> candidates=[]
    void registerCandidate(Candidate candidate){
        candidates<<candidate
    }
    List<Object> getObjects(String... keys){
        def re=candidates.collect{
            it.getObjects(keys)
        }
        re.flatten()
    }
    
    
}
abstract class Candidate{
    
    abstract List<Object> getObjects(String... keys)
}
abstract class CountableCandidate extends Candidate{
    def acceptedKeys=[]// like [[twitter,sina],[briefDisplay]]
    boolean match(String[] keys){
        if(acceptedKeys.size()!=keys.size())return false
        def re=true
        keys.eachWithIndex{
            k,i->
            re=re && acceptedKeys[i].any{it==~k}
        }
        return re
    }
}
class SingletonCandidate extends CountableCandidate{
    def obj
    def List<Object> getObjects(String... keys){
        return match(keys)?[obj]:[]
    }
}
class ClassCandidate extends CountableCandidate{
    def Class clazz

    def List<Object> getObjects(String... keys){
        try{
            return match(keys)?[clazz.newInstance()]:[]
        }catch(Exception e){
        }
        return []
    }
}
class FindByClassNameCandidate extends AbstractClassNameCandidate{
    static String toFirstCapital(String word){
        if(word.size()<=1)return word.toUpperCase()
        return word[0].toUpperCase()+word[1..-1]
    }
    @Override boolean match(String className,String[] keys){
        className==~keys.collect{
            toFirstCapital(it)
        }.join('')
    }
}
abstract class AbstractClassNameCandidate extends Candidate{
    static log=Logger.getLogger(AbstractClassNameCandidate.class)
    String basePackage=''
    Map cache=[:]
    @Lazy List<ClassName> classNameScaner={new ClasspathScanner().getResources(basePackage).toList()}()
    def List<Object> getObjects(String... keys){

        def re
        re=cache[keys.toList()]
        if(re) {
            log.trace('cache hit')
            return re.collect{it.newInstance()}
        }
        re=[]
        try{
            classNameScaner.each{
                if(match(it.className,keys)){
                    re<< Class.forName(it.fullClassName)
                }
            }
            cache[keys.toList()]=re
        }catch(Exception e){
            e.printStackTrace()
        }
        return re.collect{it.newInstance()}
    }
}
