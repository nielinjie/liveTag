package tagging.util

/**
 *
 * @author nielinjie
 */
class Conventions {
    private List<Candidate> candidates=[]
    void registerCandidate(Candidate candidate){
        candidates<<candidate
    }
    List<Object> getObjects(String... keys){
        candidates.collect{
            it.getObjects(keys)
        }.flatten()
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
class FindByClassNameCandidate extends Candidate{
    private boolean match(String className,String[] keys){
        List<String> nameParts=fromCamelCase(className)
        if (nameParts!=keys.size()) return false
        def re=true
        nameParts.eachWithIndex{
            p,i->
            re=re && p==~keys[i]
        }
        return re
    }
    static private List<String> fromCamelCase(String camelCase){
        def re=[]
        def index=0
        def matcher=(camelCase=~/[A-Z]/)
        while(matcher.find()){
            def offset= matcher.start()
            re<<(camelCase[index..<offset])
            index=offset
        }
        re<<( camelCase[index..-1])
        return re.collect{
            it.toLowerCase()
        }
    }
    def Iterator<String> classNameScaner=[]
    def List<Object> getObjects(String... keys){
        der re=[]
        try{
            classNameScaner.each{
                if(match(it,keys))
                re<< Class.forName(it).newInstance()
            }
        }catch(Exception e){

        }
        return re
    }
}
