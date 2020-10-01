'use strict';

const Stack = require('./stack/index.js');

let familyStack = new Stack();
familyStack.push('John');
familyStack.push('Cathy');
familyStack.push('Zach');
familyStack.push('Allie');

console.log('standard traversal')
while (familyStack.peek()) {
  // Do our work
  let person = familyStack.pop();
  console.log(person);

  // repeats because we're looping
}

console.log('recursive traversal');

familyStack.push('John');
familyStack.push('Cathy');
familyStack.push('Zach');
familyStack.push('Allie')

function recursiveStackTraversal(stack) {
  // base case!
  if (!stack.peek()) { return; }

  // Do our work ...
  let person = stack.pop();
  console.log(person);

  // restart the process
  recursiveStackTraversal(familyStack);
}

recursiveStackTraversal(familyStack);
