package estructuras;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
    private class BTPos<E> implements Position<E>{
        private E elem;
        private int parent; //Es suf saber su indice
        private int left, right;
        private int index; //Pos propia
        private ArrayBinaryTree<E> myTree;

        @Override
        public E getElement() {
            return null;
        }
    }
    //Es un array con nodos
    //ArrayList se redimensiona de forma dinamica
    private ArrayList<BTPos<E>> tree;
    private int size;
    private int root;


    @Override
    public Position<E> left(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public boolean hasLeft(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean hasRight(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean isInternal(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public Position<E> add(E d, Position<E> pRoot) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public E replace(Position<E> v, E e) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> sibling(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> addRoot(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertLeft(Position<E> v, E e) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertRight(Position<E> v, E e) throws RuntimeException {
        return null;
    }

    @Override
    public E remove(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2) throws RuntimeException {

    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) throws RuntimeException {

    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }
}
