'use strict';

const Stack = require('../index.js');

describe('Stacks', () => {

  it(`implements last on first out functionality`, () => {

    let testStack = new Stack();

    testStack.push(1);
    testStack.push(2);
    testStack.push(3);

    expect(testStack.pop()).toEqual(3);
    expect(testStack.pop()).toEqual(2);
    expect(testStack.pop()).toEqual(1);

  });

  it(`can peek at the next item`, () => {

    let testStack = new Stack();

    testStack.push(1);
    testStack.push(2);
    testStack.push(3);
    expect(testStack.peek()).toEqual(3);

  });

});
