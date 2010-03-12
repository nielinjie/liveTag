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

class ClassScannerTests {
    @Test void scanner(){
        ClasspathScanner cs=new ClasspathScanner()
        def re=cs.getResources('tagging')
        println re

        re=cs.getResources('tagging.system')
        println re
        re=cs.getResources('org.jdom')
        println re
        re=cs.getResources('org.jdom.adapters')
        println re
    }
}

