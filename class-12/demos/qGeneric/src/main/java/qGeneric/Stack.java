package qGeneric;

import java.util.EmptyStackException;

public class Stack <T> {
    public static void main(String[] args){
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.pop());
        s.push(5);
        System.out.println(s.pop());
        System.out.println(s.pop());

        Stack<String> stringStack = new Stack<>();
        stringStack.push("Nicholas");
        stringStack.push("Jack");
        stringStack.push("Paul");

        System.out.println(stringStack.pop());
        System.out.println(stringStack.pop());
        stringStack.push("Claudio");
        System.out.println(stringStack.pop());
        System.out.println(stringStack.pop());
    }
    public Node<T> top;

    public void push(T value){
        if(top == null){
            this.top = new Node<T>(value);
        } else {
            this.top = new Node<T>(value, this.top);
        }
    }

    public T pop(){
        if(this.top == null){
            throw new EmptyStackException();
        } else {
            T value = top.value;
            top = top.next;
            return value;
        }
    }

    public static class Node <T>{
        T value;
        Node<T> next;

        public Node(T value){
            this.value = value;
        }

        public Node(T value, Node<T> next){
            this.value = value;
            this.next = next;
        }
    }
}
