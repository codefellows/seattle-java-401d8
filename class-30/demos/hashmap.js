'use strict';



function Hashmap(size){
  this.buckets = []; // needs known indexes for the hash to work

  if(size){
    for(size; size > 0; size--){
      this.buckets.push([]);
    }
  } else {
    for (size = 10; size > 0; size--) {
      this.buckets.push([]);
    }
  }
}

function Node(key, value){
  this.key = key;
  this.value = value;
}

Hashmap.prototype.get = function(key){ // searching for 'n';
  let hashedIndex = this.hash(key);
  let bucket = this.buckets[hashedIndex];
  // [{key: Book;, value: H},{key: e, value: D},{key: n, value: L},]
  for(let i = 0; i < bucket.length; i++){
    if(key === bucket[i].key){
      return bucket[i].value;
    }
  }
  // throw new Error('that thing is not in a bucket, try again');
  return null;

  // return the value ::: returning 'L';
};

Hashmap.prototype.contains = function (key) { // searching for 'n';
  let hashedIndex = this.hash(key);
  let bucket = this.buckets[hashedIndex];
  // [{key: Book;, value: H},{key: e, value: D},{key: n, value: L},]
  for (let i = 0; i < bucket.length; i++) {
    if (key === bucket[i].key) {
      return true;
    }
  }
  // throw new Error('that thing is not in a bucket, try again');
  return false;

  // return the value ::: returning 'L';
};

Hashmap.prototype.add = function(key, value){
  let hashedIndex = this.hash(key);
  this.buckets[hashedIndex].push(new Node(key, value));
};

Hashmap.prototype.hash = function(key){ // abc
  let hash = '';
  for(let i in key){
    hash += key.charCodeAt(i);
  }
  hash = parseInt(hash);
  // console.log(hash);
  hash = hash * hash * hash;
  // console.log(hash);
  hash = hash * 11;
  // console.log(hash);
  hash = hash / 37;

  hash = hash % this.buckets.length;
  hash = Math.floor(hash);
  // console.log('final hash ', hash);
  return hash;
};

Hashmap.prototype.toString = function(){
  let str = '[\n';
  for(let i in this.buckets){
    str += i + ': [';
    for(let j in this.buckets[i]){
      str += '{' + this.buckets[i][j].key + ', ' + this.buckets[i][j].value + '},';
    }
    str += '] \n';
  }
  str += ']';
  return str;
};


let hashy = new Hashmap(99); // prime numbers are good things
console.log(hashy.toString());
hashy.add('Book', 'Night before Christmas');
hashy.add('Books', 'A');
hashy.add('Bookd', 'B');
hashy.add('Bookf', 'C');
hashy.add('Bookg', 'D');
hashy.add('Bookh', 'E');
hashy.add('Bookj', 'F');
hashy.add('Bookk', 'G');
hashy.add('Bookl', 'G');
hashy.add('Book;', 'H');
hashy.add('Book1', 'I');
hashy.add('Book2', 'J');
hashy.add('Book3', 'K');
hashy.add('Book4', 'L');
hashy.add('Book5', 'L');
hashy.add('Book6', 'L');
hashy.add('a', 'Night before Christmas');
hashy.add('b', 'A');
hashy.add('c', 'B');
hashy.add('d', 'C');
hashy.add('e', 'D');
hashy.add('f', 'E');
hashy.add('g', 'F');
hashy.add('h', 'G');
hashy.add('i', 'G');
hashy.add('j;', 'H');
hashy.add('k', 'I');
hashy.add('l', 'J');
hashy.add('m', 'K');
hashy.add('n', 'L');
hashy.add('o', 'L');
hashy.add('q', 'L');
console.log(hashy.toString());
console.log(hashy.get('q'));
console.log(hashy.get('o'));
console.log(hashy.get('n'));
console.log(hashy.get('m'));
console.log(hashy.get('l'));
console.log(hashy.get('k'));
console.log(hashy.get('j'));
console.log(hashy.get('i'));

console.log(hashy.contains('q'));
console.log(hashy.contains('o'));
console.log(hashy.contains('n'));
console.log(hashy.contains('m'));
console.log(hashy.contains('l'));
console.log(hashy.contains('k'));
console.log(hashy.contains('j'));
console.log(hashy.contains('i'));


