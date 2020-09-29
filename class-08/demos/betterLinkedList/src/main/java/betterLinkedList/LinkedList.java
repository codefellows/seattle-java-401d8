package betterLinkedList;

public class LinkedList {

    public void main (String[] args){
//        LinkedList ll = new LinkedList();
    }

    Node head;
    Node tail;

    public void addToEnd(String value){
        if(head == null){
            head = new Node(value);
            tail = head;
        } else {
//            Node n = new Node(value);
//            n.next = tail;
//            tail = n;
            tail = new Node(value, tail);
        }
    }

    public String pop(){
        String temp = tail.value;
        tail = tail.next;
        return temp;
    }
}

class Node {
    String value;
    Node next;

    public Node(String value){
        this.value = value;
    }

    public Node(String value, Node next){
        this.value = value;
        this.next = next;
    }
}