function addNumbersNormally(a, b) {
  return a + b;
}

function addNumbersRecursively(a, b) {
  // base case : when do I stop?
  if (b === 0) { return a; }

  // Do work by calling ourselves
  return addNumbersRecursively(a + 1, b - 1)
}

console.log(addNumbersNormally(2, 5));
console.log(addNumbersRecursively(2, 5));
