package estructuras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
    private class BTNode<E> implements Position<E>{
        private E elem;
        private BTNode<E> parent;
        private BTNode<E> left;
        private BTNode<E> right;
        private LinkedBinaryTree<E> myTree;
        public BTNode(E e, BTNode<E> p,BTNode<E> r, BTNode<E> l, LinkedBinaryTree<E> mt){
            this.elem = e;
            this.parent = p;
            this.right = r;
            this.left = l;
            this.myTree = mt;
        }

        @Override
        public E getElement() {
            return elem;
        }

        public BTNode<E> getLeft() {
            return left;
        }

        public BTNode<E> getParent() {
            return parent;
        }

        public LinkedBinaryTree<E> getMyTree() {
            return myTree;
        }

        public BTNode<E> getRight() {
            return right;
        }

        public void setParent(BTNode<E> parent) {
            this.parent = parent;
        }

        public void setLeft(BTNode<E> left) {
            this.left = left;
        }

        public void setRight(BTNode<E> right) {
            this.right = right;
        }

        public void setMyTree(LinkedBinaryTree<E> myTree) {
            this.myTree = myTree;
        }

        public void setElem(E elem) {
            this.elem = elem;
        }
    }

    private int size;
    private BTNode<E> root; //Composición

    private BTNode<E> checkPosition(Position<E> v) throws RuntimeException{
        if(! (v instanceof BTNode)){
            throw new RuntimeException("The position is invalid");
        }
        BTNode<E> node = (BTNode<E>) v;
        if (this != node.getMyTree() || this.size == 0){
            throw new RuntimeException("This node does not belong to this tree");
        }
        return node;
    }

    @Override
    public Position<E> left(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        BTNode<E> l = node.getLeft();
        if (l == null){
            throw new RuntimeException("No left child");
        }
        return l;
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        BTNode<E> l = node.getRight();
        if (l == null){
            throw new RuntimeException("No right child");
        }
        return l;
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
        BTNode<E> node = checkPosition(v);
        if ((node.getLeft() == null) && (node.getRight() == null)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        return node == this.root;
    }

    @Override
    public Position<E> add(E d, Position<E> pRoot) {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if (this.size == 0){
            throw new RuntimeException("The tree is empty");
        }
        return this.root;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if(this.root != null){
            throw new RuntimeException("The tree has already a root");
        }
        this.root = new BTNode<>(e,null,null,null,this);
        return this.root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        if (this.root == node){
            throw new RuntimeException("The node is a root");
        }
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) throws RuntimeException {
        LinkedList<Position<E>> children = new LinkedList<>();
        BTNode<E> node = checkPosition(v);
        if(hasLeft(node)){
            children.add(left(node));
        }
        if(hasRight(node)){
            children.add(right(node));
        }
        return children;
    }

    @Override
    public E replace(Position<E> v, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        node.setElem(e);
        return e;
    }

    @Override
    public Position<E> sibling(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        BTNode<E> parent = node.getParent();
        if (node == this.root){
            throw new RuntimeException("No sibling");
        }
        BTNode<E> sibling;
        if(parent.getRight() == node){
            sibling = parent.getLeft();
        }else{
            sibling = parent.getRight();
        }
        if(sibling == null){
            throw new RuntimeException("This node has not a sibling");
        }
        return sibling;
    }

    @Override
    public Position<E> addRoot(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        if (this.size != 0){
            throw new RuntimeException("The node has already a tree");
        }
        this.root = node;
        this.size++;
        return node;
    }

    @Override
    public Position<E> insertLeft(Position<E> v, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        //this.left(p)
        if (node.getLeft() != null){
            throw new RuntimeException("This node has already a right child");
        }
        BTNode<E> left = new BTNode<>(e, node,null, null,this);
        node.setLeft(left);
        this.size++;
        return left;
    }

    @Override
    public Position<E> insertRight(Position<E> v, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        //this.right(p)
        if (node.getRight() != null){
            throw new RuntimeException("This node has already a right child");
        }
        BTNode<E> right = new BTNode<>(e, node,null, null,this);
        node.setRight(right);
        this.size++;
        return right;
    }

    public E remove(Position<E> v) throws RuntimeException {
        BTNode<E> node = checkPosition(v);
        //Vamos a utilizar otra implem distinta a LinkedTree
        //Vamos a indicar que no puedo borrar un nodo que tenga DOS HIJOS
        //Si quisieramos borrar un nodo interno primero que borre sus hijos por este metodo -- hoja -- se puede borrar
        if(hasLeft(v) && hasRight(v)){
            throw new RuntimeException("Cannot remove a node has two children");
        }
        //Ya se que no tiene 2 hijos
        BTNode<E> parent = node.getParent();
        BTNode<E> child;
        //Parent se queda con el hijo del que borrar
        if (left(v) == null){
            child = node.getRight();
        }else{
            child = node.getLeft();
        } //Si no hay 2 hijos -- child tiene valor inicializado como null
        if(parent == null){ //Si es la raiz
            if(child != null){
                child.setParent(null);
            }
            this.root =  child;
        }else{ //Si no es la raiz
            if(parent.getLeft() == node){
                parent.setLeft(child);
            }else{
                parent.setRight(child);
            }
            if(child != null){ //Solo en el caso de q sea null
                child.setParent(parent);
            }
            node.setParent(null); //Este ya no es su padre
        }
        node.setMyTree(null); //Ya  no pertenece al arbol
        this.size--;
        return node.getElement();
    }

    private void attachLeft(Position<E> p, BinaryTree<E> t1){
        BTNode<E> node = checkPosition(p);
        if(this == t1){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if(hasLeft(p)){
            throw new RuntimeException("Node already has a left child");
        }
        //Comprobamos que el arbol no esté vacio
        if(t1 != null){
            BTNode<E> root = (BTNode<E>) t1.root();
            node.setLeft(root);
            root.setParent(node); //MIRAR
        }
        this.size+= t1.size();
    }

    private void attachRight(Position<E> p, BinaryTree<E> t1){
        BTNode<E> node = checkPosition(p);
        if(this == t1){
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if(hasRight(p)){
            throw new RuntimeException("Node already has a right child");
        }
        //Comprobamos que el arbol no esté vacio
        if(t1 != null){
            BTNode<E> root = (BTNode<E>) t1.root();
            node.setRight(root);
            root.setParent(node); //MIRAR
        }
        this.size+= t1.size();
    }

    @Override
    public void attach(Position<E> v, BinaryTree<E> t1, BinaryTree<E> t2) throws RuntimeException {
        //Unir t1 por la izda y t2 por la derecha
        attachLeft(v,t1);
        attachRight(v,t2);
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) throws RuntimeException {
        BTNode<E> n1 = checkPosition(p1);
        BTNode<E> n2 = checkPosition(p2);
        E e1 = n1.getElement();
        E aux = n2.getElement();
        n1.setElem(aux);
        n2.setElem(e1);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new InorderIteratorLT<E>(this);
    }
}
