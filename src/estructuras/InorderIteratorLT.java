package estructuras;
import java.util.*;

public class InorderIteratorLT<E> implements Iterator<Position<E>> {
    private final Deque<Position<E>> deque = new ArrayDeque<>();
    private LinkedBinaryTree<E> tree;

    public InorderIteratorLT(LinkedBinaryTree<E> tree){
        this.tree = tree;
        deque.add(tree.root()); //Añadimos raiz al arbol
    }
    @Override
    public boolean hasNext() {
        return deque.size() != 0;
    }

    @Override
    public Position<E> next() {
        Position<E> p = deque.removeFirst();
        if(tree.hasRight(p)){
            p = tree.right(p);
            deque.add(tree.right(p));
            while(tree.hasLeft(p)){
                deque.add(tree.left(p));
                p = tree.left(p);
            }
        }
        while(tree.hasLeft(p)){
            deque.add(tree.left(p));
            p = tree.left(p);
        }
    }
}
