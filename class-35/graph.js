'use strict';

function Graph(){
  this.vertexes = new Set();
}

Graph.prototype.toString = function(){
  let output = 'Graph :\n';
  for(let node of this.vertexes){
    let oneNodeString = `   ${node.value} : [`;
    for(let edge of node.edges){
      const destination = edge.destination.value;
      oneNodeString += `${destination},`;
    }
    oneNodeString += ']\n';
    output+= oneNodeString;
  }
  return output;
};

Graph.prototype.add = function(value){
  const node = new GraphNode(value);
  this.vertexes.add(node);
  return node;
};

Graph.prototype.addEdge = function(node1, node2){
  const edge1 = new Edge(node1, node2);
  node1.edges.add(edge1);

  const edge2 = new Edge(node2, node1);
  node2.edges.add(edge2);
};

Graph.prototype.getNeighbors = function(node){ // TODO: Only return the unique neighbors
  const neighbors = [];
  for(let edge of node.edges){
    neighbors.push(edge.destination.value);
  }
  return neighbors;
};


function GraphNode(value){
  this.value = value; // value
  this.edges = new Set(); // LL: next, binary tree (left / right), k-ary tree(children)
}

function Edge(origin, destination){
  this.origin = origin;
  this.destination = destination;
}


const graph = new Graph();
const hawaii = graph.add('Hawaii');
const sji = graph.add('San Juan Isles');
const bahamas = graph.add('Bahamas');
const a = graph.add('a');
const b = graph.add('b');
const c = graph.add('c');

graph.addEdge(hawaii, sji);
graph.addEdge(hawaii, bahamas);
graph.addEdge(hawaii, bahamas);
graph.addEdge(bahamas, sji);
graph.addEdge(bahamas, a);
graph.addEdge(a, b);
graph.addEdge(b, c);


console.log(graph.toString());
console.log(graph.getNeighbors(hawaii));




