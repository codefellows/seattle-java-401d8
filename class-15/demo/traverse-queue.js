const Queue = require('./queue/index.js');

let familyQueue = new Queue();
familyQueue.enqueue('John');
familyQueue.enqueue('Cathy');
familyQueue.enqueue('Zach');
familyQueue.enqueue('Allie')

console.log('Traverse iteratively');

while (familyQueue.peek()) {
  // Process each
  let person = familyQueue.dequeue();
  console.log(person);
  // continue going until done (looping)
}

console.log('Traverse Recursively');

familyQueue.enqueue('John');
familyQueue.enqueue('Cathy');
familyQueue.enqueue('Zach');
familyQueue.enqueue('Allie')

function traverseQueueRecursively(queue) {
  if (!queue.peek()) { return; }
  let person = queue.dequeue();
  console.log(person);
  traverseQueueRecursively(queue)
}

traverseQueueRecursively(familyQueue);
