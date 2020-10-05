'use strict';

const LinkedList = require('../index');

const listA = new LinkedList();
listA.append(1);
listA.append(2);
listA.append(3);
listA.append(4);

const listB = new LinkedList();
listB.append('a');
listB.append('b');
listB.append('c');
listB.append('d');
listB.append('e');
listB.append('f');
listB.append('g');

function zip(a, b) {

  let currentA = a.head;
  let currentB = b.head;
  const list = new LinkedList();

  while (currentA || currentB) {
    currentA && list.append(currentA.value);
    currentB && list.append(currentB.value);
    currentA = (currentA && currentA.next) || null;
    currentB = (currentB && currentB.next) || null;
  }

  return list;

}

const zipped = zip(listA, listB);

listA.print();
listB.print();
zipped.print();
