const LinkedList = require('./singly-linked-list/index.js');

let list = new LinkedList();
list.append('John');
list.append('Cathy');
list.append('Zach');
list.append('Allie');


console.log('Traverse iteratively');

let current = list.head;
while (current) {
  // Do your work
  console.log(current.value);
  // Move it along
  current = current.next;
}


console.log('Traverse recursively')

function traverseListRecursively(node) {
  // Base Case
  if (!node) { return; }

  // do your work
  console.log(node.value);

  // Continute processing
  traverseListRecursively(node.next);
}

traverseListRecursively(list.head);
