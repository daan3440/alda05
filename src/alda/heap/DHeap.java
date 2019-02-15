// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
	private static final int DEFAULT_CAPACITY = 10;
	private static final int DEFAULT_DIMENSIONS = 2;

	private int currentSize;      // Number of elements in heap
	private int d;      // n
	private AnyType [ ] array; // The heap array
	/**
	 * Construct the binary heap.
	 */
	public DHeap( )
	{
		this( DEFAULT_DIMENSIONS );
	}

	/**
	 * Construct the binary heap.
	 * @param d the capacity of the binary heap.
	 */
	public DHeap(int d){
		if(d <= 1) {
			throw new IllegalArgumentException();
		}
		this.d = d;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY  + 1 ];
	}

	/**
	 * Insert into the priority queue, maintaining heap order.
	 * Duplicates are allowed.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x )
	{
		if( currentSize == array.length - 1 ) {
			enlargeArray( array.length * d + 1 );
		}
		// Percolate up
		int hole = ++currentSize;
		for( array[ 0 ] = x; hole != 1 && x.compareTo( array[ parentIndex(hole)] ) < 0; hole = (parentIndex(hole))) {
			array[ hole ] = array[ parentIndex(hole) ];
		}
		array[ hole ] = x;
	}




	private void enlargeArray( int newSize )
	{
		AnyType [] old = array;
		array = (AnyType []) new Comparable[ newSize ];
		for( int i = 0; i < old.length; i++ )
			array[ i ] = old[ i ];        
	}

	/**
	 * Find the smallest item in the priority queue.
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin( )
	{
		if( isEmpty( ) )
			throw new UnderflowException( );
		return array[ 1 ];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin( )
	{
		if( isEmpty( ) )
			throw new UnderflowException( );

		AnyType minItem = findMin( );
		array[ 1 ] = array[ currentSize--];
		percolateDown( 1 );

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary
	 * arrangement of items. Runs in linear time.
	 */
	private void buildHeap( )
	{
		for( int i = currentSize / 2; i > 0; i-- )
			percolateDown( i );
	}

	/**
	 * Test if the priority queue is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty( )
	{
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty( )
	{
		currentSize = 0;
	}



	/**
	 * Internal method to percolate down in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown( int hole )
	{
		AnyType tmp = array[ hole ];
		for(; minChild(hole) <= currentSize && tmp.compareTo(getMinChild(hole)) >= 0;  hole = minChild(hole))
			array[hole] = getMinChild(hole);
		array[ hole ] = tmp;
	}

	private AnyType getMinChild(int parent){
		return get(minChild(parent));
	}

	private int minChild(int parent){
		int firstChildIndex = firstChildIndex(parent);
		if(firstChildIndex > currentSize)
			return currentSize+d;
		
		AnyType min = array[firstChildIndex];
		int minChildIndex = firstChildIndex;

		for(int i = 1; i<d;i++){
			if(firstChildIndex+i <= currentSize){
				AnyType compare = array[firstChildIndex+i];
				if(compare !=null  && min.compareTo(compare) > 0){
					minChildIndex = firstChildIndex+i;
					min = compare;
				}
			}
		}
		return minChildIndex;
	}

	public int parentIndex(int i) {
		if (i <= 1)
			throw new IllegalArgumentException();
		return (i-2+d)/d;
	}

	public int firstChildIndex(int i) {
		if (i <1)
			throw new IllegalArgumentException();
		//		System.out.println(i*d-(d-2));
		return i*d-d+2;
	}

	public int size() {
		return currentSize;
	}


	public AnyType get(int index) {
		return array[index];
	}
}
