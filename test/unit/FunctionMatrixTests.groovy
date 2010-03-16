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
        def functionS=new FunctionStub(keys:['brief','display'],memo:'displayed in list view, should return a object has getPanel() method')
        fm.registerFuctionStub(functionS)
        def all=fm.getAllFunctionStubs()
        at(all.size,is(1))
        def fs=fm.getFunctionStub('brief','display')
        at(fs,is(functionS))
        //find something in classPath
        def re=fm.getFunction('tweet','brief','display')
        at(re,is(not(null)))
        re=fm.getFunction('twitterPeople','brief','display')
        at(re,is(not(null)))
        try{
            re=fm.getFunction('twitterPeople','detailed','display')
            fail('exception should thrown')
        }catch(Exception e){

        }
        re=fm.getAllFunctions('brief','display')
        at(re.size()>=1,is(true))// this is not cool, but works
        try{
            re=fm.getAllFunctionStubs('detailed','display')
            fail('exception should thrown')
        }catch(Exception e){

        }
    }
    @Test void functionGuard(){
        FunctionMatrix fm=new FunctionMatrix()
        def functionS=new FunctionStub(keys:['brief','display'],memo:'displayed in list view, should return a object has getPanel() method')
        fm.registerFuctionStub(functionS)
    }
    @Test void autoProvider(){
        AutoFunctionMatrix auFm=new AutoFunctionMatrix()
        println auFm.getAllFunctionStubs()
    }
}

