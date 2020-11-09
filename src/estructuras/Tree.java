package estructuras;

public interface Tree<E> extends Iterable<Position<E>>{
    // Iterable -- estructura se puede recorrer
    public int size();
    public boolean isEmpty();
    public Position<E> root() throws RuntimeException; //RuntimeException no es obligatorio en java
    //Lo usamos para marcar que es un metodo parcial
    public Position<E> addRoot(E e) throws RuntimeException;
    //Excepción si ya tiene raíz porq lo hemos decidido

    //public Position<E> add(E e, Position<E>p);
    public Position<E> parent(Position<E> v) throws RuntimeException;
    public Iterable<? extends Position<E>> children(Position<E> v) throws RuntimeException;
    public boolean isInternal(Position<E> v) throws RuntimeException;
    public boolean isLeaf(Position<E> v) throws RuntimeException;
    public boolean isRoot(Position<E> v) throws RuntimeException;

    Position<E> add(E d, Position<E> pRoot);
}
