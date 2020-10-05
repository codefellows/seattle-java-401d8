'use strict';

const Queue = require('../index.js');

describe('Queue', () => {

  it(`implements first in last out functionality`, () => {

    let testQueue = new Queue();

    testQueue.enqueue(1);
    testQueue.enqueue(2);
    testQueue.enqueue(3);

    expect(testQueue.dequeue()).toEqual(1);
    expect(testQueue.dequeue()).toEqual(2);
    expect(testQueue.dequeue()).toEqual(3);

  });

});
