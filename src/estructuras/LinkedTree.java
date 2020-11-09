package estructuras;

import java.util.*;

public class LinkedTree<E> implements Tree<E> {
    //Implements, asi marcamos esa relacion de herencia
    private class TreeNode<E> implements Position<E> {
        private E element;
        private TreeNode<E> parent;
        private LinkedTree<E> myTree;
        private List<TreeNode<E>> children;

        //ArrayList cuando voy a consultar en una posicion concreta
        //LinkedList para recorrerla ...
        //Declaramos lo mas generico posible y luego decido en el constructor
        public TreeNode(E e, List<TreeNode<E>> c, TreeNode<E> p, LinkedTree<E> t) {
            this.element = e;
            this.children = c;
            this.parent = p;
            this.myTree = t;
        }

        @Override
        public E getElement() {
            return element;
        }

        public final void setElement(E element) {
            this.element = element;
        }

        public LinkedTree<E> getMyTree() {
            return myTree;
        }

        public TreeNode<E> getParent() {
            return parent;
        }

        public List<TreeNode<E>> getChildren() {
            return children;
        }

        public void setMyTree(LinkedTree<E> myTree) {
            this.myTree = myTree;
        }

        public void setParent(TreeNode<E> parent) {
            this.parent = parent;
        }

        public void setChildren(List<TreeNode<E>> children) {
            this.children = children;
        }
    }

    private TreeNode<E> root;
    private int size;

    public LinkedTree() {
        this.root = null;
        this.size = 0;
    }

    private class LinkedTreeIterator<E> implements Iterator<Position<E>> {
        // Ó implements Iterator<TreeNode<E>>
        //Me interesa más que me devuelva cada Position
        //Podemos coger recorrido en anchura, profundidad... lo que queramos
        //Vamos a recorrerlo en anchura porq lo hemos decidido
        //Anchura: por niveles
        //Anchura: necesito cola -- profundidad: pila
        //¿Que vamos a almacenar en la cola? Los nodos <...>
        private Queue<TreeNode<E>> nodeQueue = new ArrayDeque<>();

        //En java no existe new ArrayQueue-- usamos Deque que se pueden hacer encolados y desencolados por ppio y final
        //nodeQueue. ... = Aqui sale los metodos de queue, da igual lo que hayamos puesto a la dcha del =
        private LinkedTreeIterator(TreeNode<E> root) {
            this.nodeQueue.add(root); //Cuando se llame al iterador lo llamamos con la raiz
        }

        @Override
        public boolean hasNext() {
            //Hasta que cola tenga elementos
            return nodeQueue.size() != 0;
        }

        @Override
        public Position<E> next() {
            TreeNode<E> aux = nodeQueue.remove();
            //Para sacar los hijos getChilden
            //Puedo usar el for mejorado porque children me da una lista, que es iterable
            for (TreeNode<E> node : aux.getChildren()) {
                this.nodeQueue.add(node); //Metemos los hijos del nodo en la cola
            }
            return aux;
        }
        //Remove -- no ponerlo, no necesario
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
        return null;
    }

    public Position<E> add(E e, Position<E> p) throws RuntimeException {
        TreeNode<E> parent = this.checkPosition(p);
        TreeNode<E> newNode = new TreeNode<>(e, new ArrayList<>(), null, null);
        List<TreeNode<E>> l = parent.getChildren();
        l.add(newNode);
        //No pasa nada si hay elementos repetidos, para eso les diferencia el position
        this.size++;
        return newNode;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        //Tener en cuenta si ya tiene raiz
        if (this.root != null) {
            throw new RuntimeException("The tree has already a root");
        }
        this.root = new TreeNode<>(e, new ArrayList<>(), null, this);
        //Lista de hijos 2 opciones: Constructor inicializamos con min info posible -- hijos a null
        //Problema: tengo que preguntar cada vez q añada tengo que preguntar si la lista de hijos esta vacia o no
        //Mejor crear lista vacia -- me ahorro if
        this.size = 1;
        return this.root;

    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        TreeNode<E> node = checkPosition(v);
        return node.getParent();
    }


    // ? extends Position<E> -- Explicación:
    //No hay relacion entre Iterable y la lista de hijos que devuelve
    //Iterable de Position o algo que herede de Position (Lista de TreeNode)

    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) throws RuntimeException {
        TreeNode<E> node = checkPosition(p);
        return node.getChildren(); //Devolvemos la lista de hijos
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
    public Iterator<Position<E>> iterator() { //Metodo por heredar de Iterable
        //Tenemos que crear un objeto del iterador que acabo de crear
        return new LinkedTreeIterator<>(this.root);
    }


    public boolean isRoot(Position<E> p) throws RuntimeException {
        //Para evitar que no sea null, o un nodo de una lista...
        //Siempre q el usuario me pase un position tenemos q hacerlo
        TreeNode<E> node = checkPosition(p);
        return this.root == node;
    }

    private TreeNode<E> checkPosition(Position<E> p) throws RuntimeException {
        if (!(p instanceof TreeNode)) {
            throw new RuntimeException("the position is invalid");
        }
        TreeNode<E> aux = (TreeNode<E>) p;
        //this.size == 0 lo incluimos para que el metodo remove siga funcionando si se borra la raiz
        if (aux.getMyTree() != this || this.size == 0) {
            throw new RuntimeException("This node does not belong to this tree");
        }
        return aux;
        //O(1) -- complejidad. Cualquier cosa q sea O(1) la puedo hacer cuando quiera
    }

    //Decidimos borrar el nodo y todos sus descendentes
    public void remove(Position<E> p) throws RuntimeException {
        //O(1) -- cortamos ref al padre y borramos sus descendentes
        //  Cortamos la referencia del hijo que queremos borrar
        //El size se vera afectado, no podemos quitarle 1, tb a sus hijos
        //Por el size es O(n), si fuera por la ref cortarla seria O(1)
        //mytree no puede dañar la complejidad de los metodos, implicaria un mal diseño

        TreeNode<E> node = checkPosition(p); //Nodo B
        //Recorremos arbol con Iterador creado
        LinkedTreeIterator<E> it = new LinkedTreeIterator<>(node); // Lo recorro desde donde lo quiero borrar
        int contador = 0;
        while (it.hasNext()) {
            Position<E> pos = it.next(); //Ya que tengo q contar los nodos, le borro el myTree. No le añadiriamos complejidad si solo tuvieraos q hacer eso
            //Pos apunta en primer lugar en it.next() al D, el node. Por la implementacion de la cola
            TreeNode<E> n = (TreeNode<E>) pos;
            contador++;
        } //Para el size
        this.size -= contador;
        if (node.getParent() != null) {
            TreeNode<E> parent = node.getParent(); //Nodo D
            parent.getChildren().remove(node); //Quitamos de la lista de hijos el nodo B. No necesitamos quitar sus descendientes
        } else {
            this.root = null;
        }
        //Borramos arbol desde 0 -- complejidad O(1)
        //Desventaja: todos los position estan marcados
        //El usuario sigue teniendo punteros y puede añadir a un nodo "borrado" un hijo con .add
        //Variamos checkPosition para que esto no nos lo permita
    }

    private void posOrderAux(Position<E> p, List<Position<E>> list) throws RuntimeException {
        for (Position<E> w : children(p)) {
            posOrderAux(w, list); //Primero hijos y despues el nodo
        } //Metodo recursivo
        list.add(p);
    }

    public Iterable<Position<E>> posOrder() {
        List<Position<E>> positions = new ArrayList<>();
        if (this.size != 0) {
            posOrderAux(this.root, positions);
        }
        return positions;
    }

    private void preOrderAux(Position<E> p, List<Position<E>> list) throws RuntimeException {
        list.add(p); //dif con posorden
        for (Position<E> w : children(p)) {
            preOrderAux(w, list); //Primero hijos y despues el nodo
        } //Metodo recursivo

    }

    public Iterable<Position<E>> preOrder() {
        List<Position<E>> positions = new ArrayList<>();
        if (this.size != 0) {
            preOrderAux(this.root, positions);
        }
        return positions;
    }

    //Practica1: moveSubtree

    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        //Mueve nodo de su correspondiente arbol a hacerlo hijo de pDest
        TreeNode<E> origen = checkPosition(pOrig);
        TreeNode<E> destino = checkPosition(pDest);
        //Comprobacion de que los nodos que me pasa el usuario son correctos

        //Comprobamos 3 casos:
        if (origen == this.root) {
            throw new RuntimeException("The node is the root");
        } else if(isSubtree(origen, destino)){
            throw new RuntimeException("Destination is a subtree of origen");
        }else{
            Position<E> parent = parent(pOrig);
            TreeNode<E> nodeParent = (TreeNode<E>) parent;
            //origen.setParent(null); //Desconectamos su padre
            nodeParent.getChildren().remove(origen);//Desconectamos al padre ese hijo
            //Ahora conectamos origen a destino
            origen.setParent(destino);
            List<TreeNode<E>>children = destino.getChildren();
            children.add(origen); //Añadimos a la lista de hijos origen
            return pDest;
        }
    }

    private boolean isSubtree(TreeNode<E> origen, TreeNode<E> destino) {
        //Comprobamos que destino es descendiente de origen
        LinkedTreeIterator<E> it = new LinkedTreeIterator<>(origen);
        while(it.hasNext()){
            Position<E> next = it.next();
            TreeNode<E> nodeNext = (TreeNode<E>) next;
            if(destino == nodeNext){
                return true;
            }
        }
        return false;
    }
}

