/**
 * @author tushar
 */
object bin_search 
{
  def main(args: Array[String]) 
  {
    print("Enter array elements: ")
    val ip: String = Console.readLine
    var list = ip.split(' ').map(x => x.toInt).sorted;
    print("Enter number to be searched: ")
    val num: Int = Console.readInt
    var left = 0
    var right = list.length - 1
    if(binSearch(list, num, left, right) == -1)
      println("Not found")
    else
      println("Found")
   }
  
  def binSearch(list: Array[Int], target: Int, left: Int, right: Int): Int = 
  {
    
    if(left <= right)
    {
      val mid = left + (right - left)/2
      if(list(mid) == target)
        return mid
      else if(list(mid) > target)
      {
        return binSearch(list, target, left, mid-1)
      }
      else
      {
        return binSearch(list, target, mid+1, right)
      }
    }
    else
    {
      return -1
    }
  } 
}