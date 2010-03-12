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

class FunctionMatrixTests {
    @Test void functionMatrix(){
        FunctionMatrix fm=new FunctionMatrix()
        fm.registerFuctionStub(new FunctionStub(keys:['brief','display'],memo:'displayed in list view, should return a object has getPanel() method'))
        def all=fm.getAllFunctionStubs()
        at(all.size,is(1))
        def re=fm.getFunction('tweet','brief','display')
        at(re,is(not(null)))
        re=fm.getAllFunctions('brief','display')
        at(re.size(),is(1))
    }
}

