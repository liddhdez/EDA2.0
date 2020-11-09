package estructuras;

public interface BinaryTree<E> extends Tree<E>{
   public Position<E> left(Position<E> v) throws RuntimeException;
   public Position<E> right(Position<E> v) throws RuntimeException;
   public boolean hasLeft(Position<E> v) throws RuntimeException;
   public boolean hasRight(Position<E> v) throws RuntimeException;
   public boolean isInternal(Position<E> v) throws RuntimeException;
   public boolean isLeaf(Position<E> v) throws RuntimeException;
   public boolean isRoot(Position<E> v) throws RuntimeException;
   public Position<E> root() throws RuntimeException;
   public E replace(Position<E> v, E e) throws RuntimeException;
   public Position<E> sibling(Position<E> v) throws RuntimeException;
   public Position<E> addRoot(Position<E> v) throws RuntimeException;
   public Position<E> insertLeft(Position<E> v, E e) throws RuntimeException;
   public Position<E> insertRight(Position<E> v, E e) throws RuntimeException;
   public E remove(Position<E> v) throws RuntimeException;
   public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2) throws RuntimeException;
   public void swapElements(Position<E> p1, Position<E> p2) throws RuntimeException;
}
