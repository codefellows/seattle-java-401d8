'use strict';

class Node {
  constructor(val) {
    this.value = val;
    this.left = null;
    this.right = null;
  }
}

class BinaryTree {
  constructor(root = null) {
    this.root = root;
  }

  // Visit the node as you encounter it
  preOrder() {
    let results = [];

    function _walk(node) {
      results.push(node.value);
      if (node.left) { _walk(node.left); }
      if (node.right) { _walk(node.right); }
    }

    _walk(this.root);

    return results;

  }

  // Visit the node after you go left
  inOrder() {

    let results = [];

    function _walk(node) {
      if (node.left) { _walk(node.left); }
      results.push(node.value);
      if (node.right) { _walk(node.right) }
    }

    _walk(this.root);

    return results;

  }

  // Without a helper
  inOrderNoHelper(node = this.root, results = []) {
    if (node.left) { this.inOrderNoHelper(node.left, results) }
    results.push(node.value);
    if (node.right) { this.inOrderNoHelper(node.right, results) }
    return results;
  }

  // Visit the node after you have gone all the way left and right
  postOrder() {

    let results = [];

    function _walk(node) {
      if (node.left) { _walk(node.left); }
      if (node.right) { _walk(node.right) }
      results.push(node.value);
    }

    _walk(this.root);

    return results;

  }

}

class BinarySearchTree extends BinaryTree {
  add(value) {

  }
  contains(value) {

  }
}

let ten = new Node(10);
let eight = new Node(8);
let seven = new Node(7);
let nine = new Node(9);
let fifteen = new Node(15);
let twelve = new Node(12);
let twentyone = new Node(21);

ten.left = eight;
eight.left = seven;
eight.right = nine;
ten.right = fifteen;
fifteen.left = twelve;
fifteen.right = twentyone;

let tree = new BinaryTree(ten);

// console.log(JSON.stringify(tree, null, 4));

console.log(tree.preOrder())
console.log(tree.inOrder())
console.log(tree.inOrderNoHelper())
console.log(tree.postOrder())
