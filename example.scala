import scala.util.{Random}
//some data class
class TestData(val p1:Int=0, val p2:Int = 0)

//lets make a companion object
object TestData {
    //define constructor function
    def apply(listCount: Int): List[TestData] = {
        //create instance of Random
	val r = new Random
	//create some lists
	val lists = for {i <- 0 to listCount} yield new TestData(r.nextInt(10000), r.nextInt(10000))
	//return representation of list
	lists.toList
   }
}

//lets define some computation to do in a trait to mixin later
trait TestComputation{
	def function(data:TestData):Int = {
		data.p1 + data.p2
	}

	def compute(list:List[TestData]): Int
}

//Lets create 2 implementations of TestComputation trait
//one for sequential processing
//one for parallel processing

object SequentialImpl extends TestComputation {
	def compute(list:List[TestData]):Int = list.map{function}.max
}

object ParallelImpl extends TestComputation {
	def compute(list:List[TestData]): Int = list.par.map{function}.max
}


//main program

object Experiment {
	def main(args: Array[String]) {
	val list = TestData(args(0).toInt)
	var t1 = System.currentTimeMillis
	SequentialImpl.compute(list)
	var t2 = System.currentTimeMillis
	println("Sequential: " + (t2-t1) + " (ms)")
	t1 = System.currentTimeMillis
	ParallelImpl.compute(list)
	t2 = System.currentTimeMillis
	println("Parallel: " + (t2-t1) + " (ms)")
     }
}
