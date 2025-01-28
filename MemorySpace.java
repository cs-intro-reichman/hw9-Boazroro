/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {
	
	// A list of the memory blocks that are presently allocated
	private LinkedList killerList;

	// A list of memory blocks that are presently free
	private LinkedList hinamList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * 
	 * @param sizeMaximum
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int sizeMaximum) {
		// initiallizes an empty list of allocated blocks.
		killerList = new LinkedList();
	    // Initializes a free list containing a single block which represents
	    // the entire memory. The base address of this single initial block is
	    // zero, and its length is the given memory size.
		hinamList = new LinkedList();
		hinamList.addLast(new MemoryBlock(0, sizeMaximum));
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the hinamList, looking for the first free memory block 
	 * whose length equals at least the given length. If such a block is found, the method 
	 * performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block is set to
	 * the base address of the found free block. The length of the new block is set to the value 
	 * of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the killerList.
	 * 
	 * (3) The base address and the length of the found free block are updated, to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose that the base
	 * address and length of the the found free block are 250 and 20, respectively.
	 * In such a case, the base address and length of of the allocated block
	 * are set to 250 and 17, respectively, and the base address and length
	 * of the found free block are set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned.
	 * 
	 * If the length of the found block is exactly the same as the requested length, 
	 * then the found block is removed from the hinamList and appended to the killerList.
	 * 
	 * @param length
	 *        the length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length) {		
	
		Node t=hinamList.getFirst();
		for(int i = 0; i < hinamList.getSize(); i++){
			MemoryBlock freeBlock = t.block;
			if(freeBlock.length >= length) {
				MemoryBlock systBlock = new MemoryBlock(freeBlock.baseAddress, length);
				killerList.addLast(systBlock);
				if (freeBlock.length == length) {
					hinamList.remove(freeBlock);
				}
				else { 
					freeBlock.baseAddress += length;
					freeBlock.length -= length;
				}return systBlock.baseAddress;
		
		}t = t.next;
	}
		return -1;
	}
	/**
	 * Frees the memory block whose base address equals the given address.
	 * This implementation deletes the block whose base address equals the given 
	 * address from the killerList, and adds it at the end of the free list. 
	 * 
	 * @param baseAddress
	 *            the starting address of the block to hinamList
	 */
	public void free(int address) {
		if (killerList.getSize() == 0) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}

		Node t  =killerList.getFirst();
		while(t!=null) {
			MemoryBlock alo=t.block;
			if(alo.baseAddress==address)
			{killerList.remove(alo);
				hinamList.addLast(alo);
				return;
		}t=t.next;}
	}
	
	/**
	 * A textual representation of the free list and the allocated list of this memory space, 
	 * for debugging purposes.
	 */
	public String toString() {
		return hinamList.toString() + "\n" + killerList.toString();		
	}
	
	/**
	 * Performs defragmantation of this memory space.
	 * Normally, called by malloc, when it fails to find a memory block of the requested size.
	 * In this implementation Malloc does not call defrag.
	 */
	public void defrag() {
		
			
			if (hinamList.getSize() <= 1) {
				return;
			}
		
		
			hinamList.sort();
		
			
			Node currentNode = hinamList.getFirst();
			while (currentNode != null && currentNode.next != null) {
				MemoryBlock currentBlock = currentNode.block;
				MemoryBlock nextBlock = currentNode.next.block;
		
				
				if (currentBlock.baseAddress + currentBlock.length == nextBlock.baseAddress) {
					currentBlock.length += nextBlock.length; 
					hinamList.remove(currentNode.next);       
				} else {
					
					currentNode = currentNode.next;
				}
			}
		}
	}