package lineales;

import estructuras.Position;

public interface List<E> implements Position<E> {
    public int size();
    public Position<E> first;
    public Position last;
    public Position next;

}
