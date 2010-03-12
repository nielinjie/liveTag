/**
 *
 * @author nielinjie
 */
import org.junit.*
import tagging.*
import tagging.util.*
import static org.junit.Assert.*
import static org.junit.Assert.assertThat as at
import static org.hamcrest.CoreMatchers.*

class ConventionTests {
    @Test void countableCandidate(){
        Conventions cons=new Conventions()
        def a=new Object()
        cons.registerCandidate(new SingletonCandidate(acceptedKeys:[['twitter','blog'],['briefDisplay']],obj:a))
        def re=cons.getObjects('blog','briefDisplay')
        at(re.size(),is(1))
        at(re[0],is(a))
        re=cons.getObjects('twitter','briefDisplay')
        at(re.size(),is(1))
        at(re[0],is(a))
        re=cons.getObjects('mop','briefDisplay')
        at(re.size(),is(0))
        re=cons.getObjects('twitter','noDisplay')
        at(re.size(),is(0))
        
        re=cons.getObjects('twitter','.*')
        at(re.size(),is(1))
        at(re[0],is(a))

        re=cons.getObjects('.*','.*')
        at(re.size(),is(1))
        at(re[0],is(a))

        re=cons.getObjects('.*','briefDisplay')
        at(re.size(),is(1))
        at(re[0],is(a))

        def b=new Object()
        cons.registerCandidate(new SingletonCandidate(acceptedKeys:[['milk'],['briefDisplay']],obj:b))
        re=cons.getObjects('milk','briefDisplay')
        at(re.size(),is(1))
        at(re[0],is(b))

        re=cons.getObjects('.*','briefDisplay')
        at(re.size(),is(2))
        at(re[0],is(a))
        at(re[1],is(b))
    }
    @Test void classpathScannded(){
        Conventions cons=new Conventions()
        cons.registerCandidate(new FindByClassNameCandidate(basePackage:'tagging'))
        def re=cons.getObjects('tweet','brief','display')
        at(re,is(not(null)))
        at(re.size(),is(1))
    }
}

