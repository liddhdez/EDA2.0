package estructuras;

public class Pruebas {
    Tree<String> tree = new LinkedTree<>();
    //Ahora soy el usuario, no pongo E pongo el tipo.
    Position<String> pRoot = tree.addRoot("B");
    //Solo me salen los metodos definifos en la interfaz si lo declaro como Tree<...>...
    Position<String> pA = tree.add("A", pRoot); //Mirar lo de si esta en la interfaz o no por que falla
    Position<String> pB = tree.add("B", pRoot);
    Position<String> pD = tree.add("D", pRoot);


}
