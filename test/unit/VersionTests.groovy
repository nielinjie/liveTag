import org.junit.*
import static org.junit.Assert.*
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.assertThat as at
import tagging.*
class VersionTests{
	@Test void version(){
		def v1=new Version()
		sleep(100)
		def v2=new Version()
		at v2,is(not(v1))
		at v2.newer(v1),is(true)
		def v3=new Version()
		v3.date=v1.date
		at v3,is(v1)
		at v3.newer(v1),is(false)
	}
	@Test void versionWithBo(){
		def bo=new AnyBo()
		at bo.version, is(not(null))
		def v1=bo.version.clone()
		sleep(100)
		bo.pa='pa1'
		at bo.version,not(is(v1))
		def v2=bo.version.clone()
		sleep(100)
		bo.pb='pb1'
		at bo.version, is(not(v2))
		at bo.version.newer(v2), is(true)
		def v3=bo.version.clone()
		sleep(100)
		bo.pa='pa1'
		at bo.version,is(v3)
		at bo.version.newer(v3), is(false)
	}
}
class AnyBo extends BO{
	def pa
	def pb
}