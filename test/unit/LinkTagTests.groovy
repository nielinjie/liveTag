import org.junit.*
import tagging.*
import tagging.system.*
import static org.junit.Assert.*
import static org.junit.Assert.assertThat as at
import static org.hamcrest.CoreMatchers.*
class LinkTagTests{
    
    @Test void basic(){
        def tm=TaggingManagerFactory.getTaggingManager()
		tm.clear()
        def b1=new BTagable(aId:'a1')
        def b2=new BTagable(aId:'a1')
        tm.addTagable(b1)
		tm.addTagable(b2)
        BtoA bToA=new BtoA(from:b1,fromPropertyName:'aId',toType:'tagable.a')
        bToA.link({new ATagable()})
		bToA=new BtoA(from:b2,fromPropertyName:'aId',toType:'tagable.a')
        bToA.link({new ATagable()})
		def a=LinkTag.findLinked(b1)
		at(a.size,is(1))
		at(a[0],instanceOf(ATagable.class))
		def a2=LinkTag.findLinked(b2)
        at(a2.size,is(1))
        at(a2[0],instanceOf(ATagable.class))
		at(a.id,is(a2.id))
    }
    @Test void reverses(){
    	def tm=TaggingManagerFactory.getTaggingManager()
		tm.clear()
        def b1=new BTagable(aId:'a1')
        def b2=new BTagable(aId:'a1')
        tm.addTagable(b1)
        tm.addTagable(b2)
        BtoA bToA=new BtoA(from:b1,fromPropertyName:'aId',toType:'tagable.a')
        bToA.link({new ATagable()})
        bToA=new BtoA(from:b2,fromPropertyName:'aId',toType:'tagable.a')
        bToA.link({new ATagable()})
		def a=tm.findTagable({it instanceof ATagable})
		at(a.size, is(1))
		def bs=LinkTag.findReversesLinked(a[0])
		at(bs.size,is(2))
		b1=bs[0]
    	b2=bs[1]
    	at(b1.id, is(not(b2.id)))
    }
}
class BTagable extends Tagable{
	String type='tagable.b'
    def aId
}
class ATagable extends Tagable{
    String type='tagable.a'
}
class BtoA extends LinkTag{
    String type='bToA'
}