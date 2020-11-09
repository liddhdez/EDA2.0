package lineales;

import estructuras.Position;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DoubleLinkedList<E> implements List<E> {

    private class DNode<E> implements Position<E> {
        private DNode<E> prev, next;
        private E elem;
        private DoubleLinkedList<E> myList; //Matricula de todos los nodos que me pertenecen a mi lista
        //Referencia a la lista q lo contiene

        public DNode(DNode<E>prev,DNode<E> next, E e, DoubleLinkedList<E> myList){
            this.prev = prev;
            this.next = next;
            this.elem = e;
            this.myList = null;
        }

        @Override
        public E getElement() {
            return this.elem; //Viene de Position
        }

        public DoubleLinkedList<E> getMyList() { return myList; }
        public DNode<E> getPrev() { return prev; }
        public DNode<E> getNext(){return this.next;}
        public void setPrev(DNode<E> p){ this.prev = p;}
        public void setNext(DNode<E> next) {this.next = next;}
    }

    private int size;
    private DNode<E> head;
    private DNode<E> tail;
    public DoubleLinkedList(){
        this.size= 0;
        this.head = null;
        this.tail = null;
    }
    public int size(){ return this.size;}

    public boolean isEmpty(){ return this.size ==0;}

    @Override
    public boolean contains(Object o) { return false; }

    @Override
    public Iterator<E> iterator() { return null; }

    @Override
    public Object[] toArray() { return new Object[0]; }

    @Override
    public <T> T[] toArray(T[] ts) { return null; }

    @Override
    public boolean add(E e) { return false; }

    @Override
    public boolean remove(Object o) { return false; }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int i) {
        return null;
    }

    @Override
    public E set(int i, E e) {
        return null;
    }

    @Override
    public void add(int i, E e) {

    }

    @Override
    public E remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return null;
    }

    @Override
    public List<E> subList(int i, int i1) {
        return null;
    }

    public Position<E> addFirst(E e){
        DNode<E> newNode;
        if(this.isEmpty()){
            newNode = new DNode<>(null, null,e, this);//p -- n -- e
            this.head = newNode;
            this.tail = newNode;
        }else{
            // myList es this porque es el objeto que lo va a contener (lista)
            newNode = new DNode<>(null, this.head, e, this);
            this.head.setPrev(newNode);
            this.head = newNode;
        }
        this.size++;
        return newNode; //Esto genera problemas de seguridad porque alguien puede tocar el tamaño, los prev, los next...
        //Necesitamos buscarnos como solución algo que nos de seguridad: interface Position
    }

    private DNode<E> checkPosition(Position<E> p){
        // No hace falta comprobar p == null porque ya viene dentro de instanceof
        if (!(p instanceof DNode)){
            //Deja de funcionar, no nos sirve
            throw new RuntimeException("The position is invalid");
        //Ahora vamos a comprobar que pertenece a mi lista el P q me pasan
        }
        DNode<E> node = (DNode<E>)p;

        // tb puede ser node.myList == this
        if(node.getMyList() != this){
            throw new RuntimeException("the position does not belong to this list");
        }
        return node;
    }

// RuntimeException -- marcamos que es una operacion parcial (Es opcional)
    public E remove(Position<E> p) throws RuntimeException {
        //Comprobar si el position es correcto

        DNode<E> node = checkPosition(p);
        //Podemos acceder a DNode
        E elem = p.getElement(); //Accedemos al elemento del nodo con position
        if(this.head == this.tail){
            //Desreferenciamos el nodo, asi borrramos
            this.head = null;
            this.tail = null;
        }else if(node == this.head){
            this.head = this.head.getNext();
            node.getNext().setPrev(null);
        }else if(node == this.tail){
            this.tail = this.tail.getPrev();
            node.getPrev().setNext(null);
        }else{
            DNode<E> nodePrev = node.getPrev();
            DNode<E> nodeNext = node.getNext();
            nodePrev.setNext(nodeNext);
            nodeNext.setPrev(nodePrev);
        }
        this.size--;
        return elem;
    }
}
