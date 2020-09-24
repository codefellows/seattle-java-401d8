package recursion;

public class LinkedList {
    Node head;
    Node tail;

    public void append(int value){
        if(head == null){
            head = new Node(value);
            tail = head;
            return;
        }

        head = new Node(value, head);
    }

    public static Node mergeIterative(LinkedList one, LinkedList two){
//        Node current1 = one.head;
//        Node current2 = two.head;
//        Node temp1;
//        Node temp2;
//        while(current1.next != null || current2.next != null){
//            temp1 = current1.next;
//            temp2 = current2.next;
//            current1.next = current2;
//            current2.next = temp1;
//            current1 = temp1;
//            current2 = temp2;
//        }
//
//        if(current2 != null) current1.next = current2;
//        if(current1 != null) current2.next = current1;
        return one.head;
    }

    public static Node mergeRecursive(LinkedList one, LinkedList two){
        return mergeRecursiveBest(one.head, two.head);
    }
    // node1 : null, node2 : 1 -> null ::: return node 2
    // node1 : null, node2: 1 -> 2 -> 3 -> null ::: return node2
    // node2 : null, node1: 1 -> 2 -> 3 -> null ::: return node1
    // node1: null, node2: null  ::: null

    // node1 : 1 -> null , node2: 3 -> 4 -> null

    private static Node mergeRecursive(Node node1, Node node2){
        if(node1 == null) return node2;
        if(node2 == null) return node1;

        Node temp1 = node1.next; // null
        Node temp2 = node2.next; // null
        node1.next = node2; // 99 -> 100
        node2.next = mergeRecursive(temp1, temp2); // 99 -> 100 -> null

        return node1;
    }

    private static Node mergeRecursiveBest(Node node1, Node node2){
        if(node1 == null) return node2;
        node1.next = mergeRecursiveBest(node2, node1.next);
        return node1;
    }

    // 1. no way to stop : Recursion should always have a way to exit
    // 2. Doesn't do anything meaningful : Recursion should have a purpose
    // 3. Recursion should build on itself :
    // 3b. Recursion should use the accumulated operations to influence its behavior

    public String toString(){
        // stop when Node.next is null or when Node == null
        // the value wont be null, but the node will
        // build a string, return a string
        // as we go, concatenate that string with node looking characters
        // leap of faith that it builds the string we want as we go
        return toString(head);

    }

    private String toString(Node nodeImAt){
        if(nodeImAt == null) return "null";
        String recursivePiece = String.format("{%d} -> ", nodeImAt.value);
        return recursivePiece + toString(nodeImAt.next);
//        return "{1} -> " + "null";
    }



    class Node {
        int value;
        Node next;

        Node(int value){
            this.value = value;
        }

        Node(int value, Node next){
            this.value = value;
            this.next = next;
        }
    }


}
