package nxtsearch;

import java.util.*;

import rp.util.SimpleSet;


/**
 * The Class Node.
 *
 * @param <A> the generic type
 */
public class Node<A>{

  /** The contents. */
  private A contents; 
 
  /** The successors. */
  private SimpleSet<Node<A>> successors; 
  
  /** The pointers. */
  private ArrayList<Node<A>> pointers; 
  
  // We can only build a node with an empty set of successors:
  /**
   * Instantiates a new node.
   *
   * @param contents the content
   */
  public Node (A contents) {
    this.contents = contents;

    this.successors = new SimpleSet<Node<A>>(); 
    pointers = new ArrayList<Node<A>>();
    
  }
  
  // Hence we need this:
  /**
   * Adds the pointer.
   *
   * @param s the node
   */
  public void addPointer(Node<A> s) {
	    pointers.add(s);
	  }
  
  /**
   * Gets the pointers.
   *
   * @return the pointers
   */
  public ArrayList<Node<A>> getPointers() {
	  return pointers;  
	  }
  
  /**
   * Adds the successor.
   *
   * @param s the s
   */
  public void addSuccessor(Node<A> s) {
    successors.add(s);
  }

  /**
   * Contents equals.
   *
   * @param c the content
   * @return true, if equals
   */
  public boolean contentsEquals(A c) {
    return contents.equals(c);
  }

  // Get methods:
  /**
   * Contents.
   *
   * @return the content
   */
  public A contents() {
    return contents;
  }

  /**
   * Successors.
   *
   * @return the simple set of successors
   */
  public SimpleSet<Node<A>> successors() {
    return successors;
  }
 
 /**
  * F.
  *
  * @param h the h
  * @param d the d
  * @return the integer
  */
 public Integer f(int h, int d)
  {
	  int f = h+d;
	return f;
	  
  }

 public String toString(){
	 return "" + contents;
 }

}
