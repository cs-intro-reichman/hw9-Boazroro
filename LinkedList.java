/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node one; // pointer to the one element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		one = null;
		last = one;
		size = 0;
	}
	
	/**
	 * Gets the one node of the list
	 * @return The one node of the list.
	 */		
	public Node getone() {
		return this.one;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}Node t=one;
		if(index==size)
		return last;
		while(t!=null&&index>0)
		{t=t.next;
			index--;}
			if(t!=null)
			return t;
			
		return null;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the one node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
			
		if(index < 0 || index > size)
		{throw new IllegalArgumentException(
			"index must be between 0 and size");
			}	Node t=new Node(block);
			if(index==0)
			{t.next=one;
				one=t;
				if(size==0)
			{last=t;
				one=last;}
			
			size++;
			return;}
		
		if(index==size)
		{if(size==0)
			{one=t;
				last=one;}
			else
			last.next=t;
			last=t;
			size++;
		return;}
		else{
		Node prev=getNode(index-1);
		
		t.next=prev.next;
		prev.next=t;}
		size++;}
		
	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		add(size, block);
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's one element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addone(MemoryBlock block) {
		add(0, block);
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		return getNode(index).block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		for(int i = 0; i < size; i++) {
			if(getBlock(i).equals(block)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		int nodeIndex = indexOf(node.block);

		if (nodeIndex == -1) {
			throw new IllegalArgumentException("Node not found in the list.");
		}

		if (nodeIndex == 0) {
			one = one.next;
			if(size == 1) {
				last = null;
			}
		}
		else {
			Node prev = getNode(nodeIndex - 1);
			prev.next = node.next;
			if (node == last) {
				last = prev;      
			}
		}

		size--;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IllegalArgumentException(
				"index must be between 0 and size");
		}

		Node t = getNode(index);
		remove(t);
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		int indexOfBlock = indexOf(block);
		if(indexOfBlock == -1) {
			throw new IllegalArgumentException(
				"index must be between 0 and size");
		}

		remove(indexOfBlock);
	}	

	/**
	 * Returns an iterator over this list, starting with the one element.
	 */
	public ListIterator iterator(){
		return new ListIterator(one);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		if (size == 0) {
			return "";
		}
	
		String result = "";
		Node t = one;
	
		while (t!=null) {
			result += t.block;
			t=t.next;			
			if (t!=null) {
				result += " ";
			}
		}
	
		result += " ";
		return result;
	}

	public void sort (){
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - i - 1; j++) {
				Node current = getNode(j);
				Node next = current.next;
				if (current.block.baseAddress > next.block.baseAddress){
					MemoryBlock temp = current.block;
					current.block = next.block;
					next.block = temp;

				}
			}
			
		}
	}
}