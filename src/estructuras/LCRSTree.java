package estructuras;

import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LCRSTree<E> implements Tree<E>{
    private class LCRSNode<E> implements Position<E>{
        private E elem;
        private LCRSTree<E> myTree;
        private LCRSNode<E> parent;
        private LCRSNode<E> leftChild;
        private LCRSNode<E> rightSibling;

        //Constructor
        public LCRSNode(E e, LCRSNode<E> parent,LCRSNode<E> l, LCRSNode<E> r, LCRSTree<E> myTree){
            this.elem = e;
            this.parent = parent;
            this.leftChild = l;
            this.myTree = myTree;
        }

        public void setElem(E elem) {
            this.elem = elem;
        }

        public LCRSNode<E> getLeftChild() {
            return leftChild;
        }

        public LCRSNode<E> getParent() {
            return parent;
        }

        public LCRSNode<E> getRightSibling() {
            return rightSibling;
        }

        public LCRSTree<E> getMyTree() {
            return myTree;
        }

        public void setParent(LCRSNode<E> parent) {
            this.parent = parent;
        }

        public void setMyTree(LCRSTree<E> myTree) {
            this.myTree = myTree;
        }

        public void setLeftChild(LCRSNode<E> leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightSibling(LCRSNode<E> rightSibling) {
            this.rightSibling = rightSibling;
        }

        @Override
        public E getElement() {
            return null;
        }
    }

    private class LCRSTreeIterator<E> implements Iterator<Position<E>>{

        private Queue<LCRSNode<E>> nodeQueue = new ArrayDeque<>();

        public Iterable<? extends Position<E>> children(Position<E> v) throws RuntimeException {
            LCRSNode<E> node = (LCRSNode<E>) v;
            List<Position<E>> children = new LinkedList<>();
            LCRSNode<E> firstChild = node.getLeftChild();
            if (firstChild == null){
                throw new RuntimeException("this node has not children");
            }
            children.add(firstChild);
            while(firstChild.getRightSibling() != null){
                children.add(firstChild.getRightSibling());
                firstChild = firstChild.getRightSibling();
            }
            return children;
        }

        private LCRSTreeIterator(LCRSNode<E> root){
            nodeQueue.add(root);
        }

        @Override
        public boolean hasNext() {
            return nodeQueue.size() != 0;
        }

        @Override
        public Position<E> next() {
            LCRSNode<E> aux = nodeQueue.remove();
            Iterable<? extends Position<E>> list = children(aux);
            for(Position<E> node : list){
                nodeQueue.add((LCRSNode<E>) node);
            }
            return aux;
        }
    }
    private LCRSNode<E> root;
    private int size;

    public LCRSTree(){ //Constructor sin argumentos
        this.root = null;
        this.size = 0;
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
        return this.root;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if(this.root == null){
            this.root = new LCRSNode<>(e,null,null,null,this);
            return root();
        }else{
            throw new RuntimeException("This tree has already a tree");
        }
    }

    private LCRSNode<E> checkPosition(Position<E> p) throws RuntimeException{
        if(!(p instanceof LCRSNode)){ // Es null o no es LCRSNode
            throw new RuntimeException("the position is invalid");
        }
        LCRSNode<E> node = (LCRSNode<E>) p;
        if (node.getMyTree() != this || this.size == 0){
            throw new RuntimeException("this node does not belong to this tree");
        }
        return node;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        if(node == root()){
            throw new RuntimeException("The node is a root");
        }
        LCRSNode<E> parent = node.getParent();
        return parent;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        List<Position<E>> children = new LinkedList<>();
        LCRSNode<E> firstChild = node.getLeftChild();
        if (firstChild == null){
            throw new RuntimeException("this node has not children");
        }
        children.add(firstChild);
        while(firstChild.getRightSibling() != null){
            children.add(firstChild.getRightSibling());
            firstChild = firstChild.getRightSibling();
        }
        return children;
    }

    @Override
    public boolean isInternal(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        return node.getLeftChild() != null;
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        return node == root();
    }

    @Override
    public Iterator<Position<E>> iterator(){
        return new LCRSTreeIterator<>(this.root);
    }

    @Override
    public Position<E> add(E d, Position<E> pRoot) {
        LCRSNode<E> parent = checkPosition(pRoot);
        //pRoot es el padre
        LCRSNode<E> node = new LCRSNode<>(d,parent,null,null,this);
        if(parent.getLeftChild() == null){
            parent.setLeftChild(node);
        }else{
            LCRSNode<E> child = parent.getLeftChild();
            while(child.getRightSibling()!=null){
                child = child.getRightSibling();
            }
            //Ahora hemos llegado al nodo que no tiene hermano derecho
            child.setRightSibling(node);
        }
        this.size++;
        return node;
    }

    public void remove(Position<E> p) throws RuntimeException{
        LCRSNode<E> node = checkPosition(p);
        //Queremos borrar el nodo y su subarbol
        LCRSTreeIterator<E> it = new LCRSTreeIterator<>(node);
        int contador = 0;
        while(it.hasNext()){
            Position<E> pos = it.next();
            contador++;
        } //Para el size
        this.size-= contador;
        if(node == this.root){
            this.root = null;
        }else{
            LCRSNode<E> parent = node.getParent();
            LCRSNode<E> child = parent.getLeftChild();
            if(child == node){
                parent.setLeftChild(node.getRightSibling());
            }else{
                LCRSNode<E> ant = child;
                while(child != node){
                    ant = child;
                    child = child.getRightSibling();
                }
                ant.setRightSibling(child.getRightSibling());
            }
            child.setParent(null);
        }
    }

    private boolean isSubtree(Position<E> pRaiz, Position<E> pHijo) {
        LCRSNode<E> raiz = checkPosition(pRaiz);
        LCRSNode<E> hijo = checkPosition(pHijo);
        //Vamos a si hijo esta en raiz
        if (raiz == hijo) {
            return true;
        }
        LCRSTreeIterator<Position<E>> it = new LCRSTreeIterator<>(raiz);
        while(it.hasNext()){
            Position<E> next = (Position<E>) it.next();
            isSubtree(next, pHijo);
        }
        return false;
    }

    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        LCRSNode<E> origen = checkPosition(pOrig);
        LCRSNode<E> destino = checkPosition(pDest);

        if(isSubtree(pDest,pOrig)){
            throw new RuntimeException("Target position can't be a sub tree of origin");
        }
        if(origen == this.root){
            throw new RuntimeException("Root node can't be moved");
        }
        if(pOrig == pDest){
            throw new RuntimeException("Both position are the same");
        }
        //Lo añadimos a los hijos
        LCRSNode<E> pos = FindLastChild(pDest);
        if (pos == null){
            destino.setLeftChild(origen);
        }else{
            pos.setRightSibling(origen);
        }
        origen.setParent(destino);
        //No cambiamos el size
    }

    public LCRSNode<E> FindLastChild(Position<E> p) throws RuntimeException{
        LCRSNode<E> node = checkPosition(p);
        if(node.getLeftChild() == null){
            return null;
        }else{
            LCRSNode<E> sibling = node.getLeftChild();
            while(sibling.getRightSibling() != null){
                sibling = sibling.getRightSibling();
            }
            return sibling;
        }
    }

    public void swapElements(Position<E> p1, Position<E> p2){
        LCRSNode<E> n1 = checkPosition(p1);
        LCRSNode<E> n2 = checkPosition(p2);
        E aux = n1.getElement();
        n1.setElem(n2.getElement());
        n2.setElem(aux);
    }

}
