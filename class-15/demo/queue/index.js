'use strict';

class Queue {

  constructor() {
  }

  enqueue(item) {
    let queuedItem = { item: item, next: null };
    if (!this.head) {
      this.head = queuedItem;
    }
    else {
      let current = this.head;
      while (current.next) {
        current = current.next;
      }
      current.next = queuedItem;
    }
  }

  dequeue() {
    if (this.head) {
      let result = this.head;
      this.head = this.head.next;
      return result.item;
    }
  }

  peek() {
    return this.head;
  }
}

module.exports = Queue;
