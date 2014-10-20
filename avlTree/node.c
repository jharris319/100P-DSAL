#include <stdio.h>
#include <stdlib.h>
#include "node.h"

static node *newNode(void);

/*************** public interface *************/

node *newAVLNode(int v,node *p) {
	node *q = newNode();
	q->value = v;
	q->height = 0;
	q->balanceFactor = 0;
	q->parent = p;
	q->left = NULL;
	q->right = NULL;
	return q;
}

/*************** private methods *************/

static node *newNode() {
	node *n = (node *) malloc(sizeof(node));
	if (n == 0) { fprintf(stderr,"out of memory"); exit(-1); }
	return n;
}
