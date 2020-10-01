'use strict';

const LinkedList = require('../index.js');

describe('testing LinkedList', function(){

  test('list.fromArray should return a linked list', () => {
    let list = LinkedList.fromArray([1,2,3,4]);
    expect(list.head.value).toEqual(1);
    expect(list.head.next.value).toEqual(2);
    expect(list.head.next.next.value).toEqual(3);
    expect(list.head.next.next.next.value).toEqual(4);
  });

  test('list.isEmpty() should be true for empty list', () => {
    let list = new LinkedList();
    expect(list.isEmpty()).toEqual(true);
  });

  test('list.isEmpty() should be false for non-empty list', () => {
    let list = new LinkedList();
    list.append(1);
    expect(list.isEmpty()).toEqual(false);
  });

  test('list.size() should be 0 for empty list', () => {
    let list = new LinkedList();
    expect(list.size()).toEqual(0);
  });

  test('list.size() should be 1 for one-node list', () => {
    let list = new LinkedList();
    list.append(1);
    expect(list.size()).toEqual(1);
  });

  test('list.size() should be correct for many node list.', () => {
    let list = LinkedList.fromArray([1,2,3,4]);
    expect(list.size()).toEqual(4);
  });

  test('append(value) should append node to empty list', () => {
    let list = new LinkedList();
    list.append(0);
    expect(list.head.value).toBe(0);
  });


  test('append(value) should append node to non-empty list', () => {
    let list = new LinkedList();
    list.append(0);
    list.append(3);
    list.append(4);
    expect(list.head.value).toEqual(0);
    expect(list.head.next.value).toEqual(3);
    expect(list.head.next.next.value).toEqual(4);
  });

  test('prepend(node) should node to empty list', () => {
    let list = new LinkedList();
    list.prepend(5);
    expect(list.head.value).toBe(5);
  });

  test('prepend(node) should node to non-empty list', () => {
    let list = new LinkedList();
    list.prepend(5);
    list.prepend(4);
    list.prepend(0);
    expect(list.head.value).toBe(0);
    expect(list.head.next.value).toBe(4);
    expect(list.head.next.next.value).toBe(5);
  });

  test('remove(value) should remove node from one-element list', () => {
    let list = new LinkedList();
    list.append(42);
    list.remove(42);
    expect(list.head).toBe(null);
  });

  test('remove(value) should remove node from front of list', () => {
    let list = LinkedList.fromArray([0,1,2,3,4,5,6,7,8]);
    list.remove(0);
    expect(list.head.value).toBe(1);
  });

  test('remove(value) should remove node from middle of list', () => {
    let list = LinkedList.fromArray([0,1,2,3,4,5,6,7,8]);
    list.remove(3);
    expect(list.head.next.next.value).toBe(2);
    expect(list.head.next.next.next.value).toBe(4);
  });

  test('remove(value) should remove node from end of list', () => {
    let list = LinkedList.fromArray([0,1,2,3,4,5,6,7,8]);
    list.remove(8);
    expect(list.head.next.next.next.next.next.next.next.value).toBe(7);
    expect(list.head.next.next.next.next.next.next.next.next).toBe(null);
  });

  test('list.reverse() should reverse the list', () => {
    let list = LinkedList.fromArray([4, 5, 6]);
    list.reverse();
    expect(list.head.value).toEqual(6);
    expect(list.head.next.value).toEqual(5);
    expect(list.head.next.next.value).toEqual(4);
  });

  test('getMiddle() should return middle node', () => {
    let list = LinkedList.fromArray([4, 5, 6]);
    let middle = list.getMiddle();
    expect(middle.value).toEqual(5);

    list = LinkedList.fromArray([3, 4, 5, 6]);
    middle = list.getMiddle();
    expect(middle.value).toEqual(5);
  });

  test('list.getLast() should return last node', () => {
    let list = LinkedList.fromArray([3, 4, 5, 6]);
    let result = list.getLast();
    expect(result.value).toEqual(6);
  });

  test('list.getNthFromLast() should return nth from last node', () => {
    let list = LinkedList.fromArray([3, 4, 5, 6]);
    let result = list.getNthFromLast(0);
    expect(result.value).toEqual(6);

    result = list.getNthFromLast(1);
    expect(result.value).toEqual(5);

    result = list.getNthFromLast(2);
    expect(result.value).toEqual(4);

    result = list.getNthFromLast(3);
    expect(result.value).toEqual(3);
  });

  test('list.map(doubler) should double a list', () => {
    let list = LinkedList.fromArray([1,2,3]);
    let result = list.map(n => n.value * 2);
    expect(result).toEqual(LinkedList.fromArray([2,4,6]));
  });

  test('list.filter(isEven) should filter out odd numbers', () => {
    let list = LinkedList.fromArray([1,2,3,4]);
    let result = list.filter(n => n.value % 2 === 0);
    expect(result).toEqual(LinkedList.fromArray([2,4]));
  });

  test('list.reduce(cb) should reduce list', () => {
    let list = LinkedList.fromArray([1,2,3]);
    let result = list.reduce((p, n) => p + n.value, 0);
    expect(result).toEqual(6);
  });

  test('list.find(value) return fist node containg value', () => {
    let list = LinkedList.fromArray([1,2,3,2,1]);
    let result = list.find(2);
    expect(list.head.next).toEqual(result);
  });

  test('getNth should return the corect node', () => {
    let list = LinkedList.fromArray([1,2,3,4]);
    expect(list.getNth(0)).toEqual(list.head);
    expect(list.getNth(1)).toEqual(list.head.next);
    expect(list.getNth(2)).toEqual(list.head.next.next);
    expect(list.getNth(3)).toEqual(list.head.next.next.next);
  });

});
