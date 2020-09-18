'use strict';

function Node (value){
  this.value = value;
  this.next = null;
}

function LinkedList (){ // {}
  this.head = null;
  this.tail = null;
}

LinkedList.prototype.addToFront = function(value){
  const node = new Node(value);
  if(this.head === null){
    this.head = node;
    this.tail = node;
  } else {
    // take the new node and make it the head
    // const temporaryHolderOfTheHead = this.head;
    // this.head = node;
    // node.next = temporaryHolderOfTheHead;
    node.next = this.head;
    this.head = node;
  }
};

LinkedList.prototype.toString = function(){
  let output = '';
  let currentNodeIAmLookingAt = this.head;
  while(currentNodeIAmLookingAt !== null){
    output = output + `{${currentNodeIAmLookingAt.value}} -> `;
    currentNodeIAmLookingAt = currentNodeIAmLookingAt.next;
  }
  output = output + 'null';
  return output;
};

LinkedList.prototype.removeFromEnd = function(){ // Big O O(n)
  let potato = this.head;
  while(potato.next !== this.tail){
    potato = potato.next;
  }
  const theThingWeWillReturn = this.tail;
  this.tail = potato;
  potato.next = null;
  return theThingWeWillReturn.value;
};

const linkedList = new LinkedList();
linkedList.addToFront('Wellington');
linkedList.addToFront('Hans');
linkedList.addToFront('Elwood');
linkedList.addToFront('Cider');
linkedList.addToFront('Ginger');
linkedList.addToFront('Snowdrop');
console.log(linkedList.toString());
console.log(linkedList.removeFromEnd());
console.log(linkedList.removeFromEnd());
console.log(linkedList.toString());

// TODO: for monday : show you a more efficient linked list
// TODO: recursion

