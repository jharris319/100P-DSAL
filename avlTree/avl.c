#include <stdlib.h>
#include <stdio.h>
#include "node.h"
#include "avl.h"

static avltree *newTree();
int nodeCount;

avltree *newAVLTree() {
	avltree *t = newTree();
	t->root=NULL;
	return t;
}

static avltree *newTree() {
	avltree *t = (avltree*) malloc(sizeof(avltree));
	if (t==0) {fprintf(stderr,"out of memory -- no tree"); exit(-1); }
	return t;
}

// +--------------+
// |    INSERT    |
// +--------------+
node *insertHelper(node **avlNode,node **Parent,int value) {
	if(*avlNode==NULL) {*avlNode = newAVLNode(value,*Parent); nodeCount++;return *avlNode;}
	else if(value < (*avlNode)->value) return insertHelper(&(*avlNode)->left,&(*avlNode),value);
	else if(value > (*avlNode)->value) return insertHelper(&(*avlNode)->right,&(*avlNode),value);
	else {printf("\n[Duplicates not allowed]\n"); return NULL;}
}

node *insert(avltree *tree,int value) {
	return insertHelper(&tree->root,&tree->root,value);
}

// +------------+
// |    FIND    |
// +------------+
node *searchHelper(node *avlNode,int value) {
	if(avlNode==NULL || avlNode->value == value) return avlNode;
	if(value < avlNode->value) return searchHelper(avlNode->left,value);
	else return searchHelper(avlNode->right,value);
}

node *search(avltree *tree,int value){
	return searchHelper(tree->root,value);
}

// +--------------+
// |    DELETE    |
// +--------------+
node *sucessor(node *currNode) {
	if(currNode->right != NULL) currNode = currNode->right;
	while (currNode->left != NULL){
		currNode = currNode->left;
	}
	return currNode;
}

node *delete(avltree *tree,int value) {
	node *rNode;
	node *currNode = search(tree,value);
	if(currNode == NULL) {printf("\n[Not Found]\n"); return NULL;}

	//-----Node with TWO children-----//
	if (currNode->left != NULL && currNode->right != NULL) {
		node *sucessorNode = sucessor(currNode);
		rNode = sucessorNode->parent;
		if (sucessorNode->right == NULL) {
			currNode->value = sucessorNode->value;
			if (sucessorNode->parent->right == sucessorNode) sucessorNode->parent->right = NULL;
			else if (sucessorNode->parent->left == sucessorNode) sucessorNode->parent->left = NULL;
			nodeCount--;
			free(sucessorNode);
		}
		else if (sucessorNode->right != NULL) {
			currNode->value = sucessorNode->value;
			if (sucessorNode->parent->left == sucessorNode) {
				sucessorNode->parent->left = sucessorNode->right;
				sucessorNode->right->parent = sucessorNode->parent;
			}
			else {
				sucessorNode->parent->right = sucessorNode->right;
				sucessorNode->right->parent = sucessorNode->parent;
			}
			nodeCount--;
			free(sucessorNode);
		}
		return rNode;
	}
	//-----Node with NO children-----//
	else if (currNode->left == NULL && currNode->right == NULL) {
		if (tree->root == currNode) {tree->root = NULL; free(currNode); return NULL;}
		if (currNode->parent->right == currNode) {rNode = currNode->parent; currNode->parent->right = NULL;}
		else {rNode = currNode->parent;currNode->parent->left = NULL;}
		nodeCount--;
		free(currNode);
		return rNode;
	}
	//-----Node with LEFT child only-----//
	else if (currNode->left != NULL && currNode->right == NULL) {
		if (currNode == tree->root) {tree->root = currNode->left; currNode->left->parent = NULL; free(currNode); return NULL;}
		if (currNode->parent->right == currNode) {
			rNode = currNode->parent;
			currNode->left->parent = currNode->parent;
			currNode->parent->right = currNode->left;
		}
		else {
			rNode = currNode->parent;
			currNode->parent->left = currNode->left;
			currNode->left->parent = currNode->parent;
		}
		nodeCount--;
		free(currNode);
		return rNode;
	}
	//-----Node with RIGHT child only-----//
	else if (currNode->left == NULL && currNode->right != NULL) {
		if (currNode == tree->root) {tree->root = currNode->right; currNode->right->parent = NULL; free(currNode); return NULL;}
		if (currNode->parent->left == currNode) {
			rNode = currNode->parent;
			currNode->right->parent = currNode->parent;
			currNode->parent->left = currNode->right;
		}
		else {
			rNode = currNode->parent;
			currNode->right->parent = currNode->parent;
			currNode->parent->right = currNode->right;
		}
		nodeCount--;
		free(currNode);
		return rNode;
	}
	return NULL;
}

// +-----------------+
// |    VISUALIZE    |
// +-----------------+
void displayHelper(FILE *f,node *avlNode) {
	if(avlNode->left==NULL && avlNode->right==NULL && avlNode->parent==NULL) {
		fprintf(f,"\t%d\n",avlNode->value);
		return;
	}
	if(avlNode->left!=NULL) {
		fprintf(f, "\t\"%d:%d\" -> \"%d:%d\";\n", avlNode->value, avlNode->height, avlNode->left->value, avlNode->left->height);
	}
	if(avlNode->right!=NULL) {
		fprintf(f, "\t\"%d:%d\" -> \"%d:%d\";\n", avlNode->value, avlNode->height, avlNode->right->value, avlNode->right->height);
	}
	if(avlNode->left!=NULL) displayHelper(f,avlNode->left);
	if(avlNode->right!=NULL) displayHelper(f,avlNode->right);
}

void display(avltree *tree) {
	FILE *f = fopen("graph.dot", "w");
	fprintf(f,"digraph AVL {\n");
	displayHelper(f,tree->root);
	fprintf(f,"}");
	fclose(f);
}

// +---------------+
// |   TRAVERSE    |
// +---------------+
void preorderTraversal(node *currNode) {
	printf("%d ",currNode->value);
	if(currNode->left!=NULL) preorderTraversal(currNode->left);
	if(currNode->right!=NULL) preorderTraversal(currNode->right);
}

void inorderTraversal(node *currNode) {
	if(currNode->left!=NULL) inorderTraversal(currNode->left);
	printf("%d ",currNode->value);
	if(currNode->right!=NULL) inorderTraversal(currNode->right);
}

void postorderTraversal(node *currNode) {
	if(currNode->left!=NULL) postorderTraversal(currNode->left);
	if(currNode->right!=NULL) postorderTraversal(currNode->right);
	printf("%d ",currNode->value);
}

// +--------------+
// |    ROTATE    |
// +--------------+
void rotate_left(avltree *tree, node *x) {
	printf("[Rotate Left at %d]\n", x->value);
	node *y = x->right;
	x->right = y->left;
	if(y->left != NULL) y->left->parent = x;
	y->parent = x->parent;
	if(x->parent == NULL) tree->root = y;
	else if(x == x->parent->left) x->parent->left = y;
	else x->parent->right = y;
	y->left = x;
	x->parent = y;
	calcHeights(x);
	calcHeights(y);
	confirmBalance(x);
	confirmBalance(y);
}

void rotate_right(avltree *tree, node *x) {
	printf("[Rotate Right at %d]\n", x->value);
	node *y = x->left;
	x->left = y->right;
	if(y->right != NULL) y->right->parent = x;
	y->parent = x->parent;
	if(x->parent == NULL) tree->root = y;
	else if(x == x->parent->right) x->parent->right = y;
	else x->parent->left = y;
	y->right = x;
	x->parent = y;
	calcHeights(x);
	calcHeights(y);
	confirmBalance(x);
	confirmBalance(y);
}



void calcHeights(node *currNode) {
	if(currNode->left == NULL && currNode->right == NULL) currNode->height = 0;
	else if(currNode->left != NULL && currNode->right == NULL) currNode->height = currNode->left->height + 1;
	else if(currNode->left == NULL && currNode->right != NULL) currNode->height = currNode->right->height + 1;
	else {
		if(currNode->left->height > currNode->right->height) currNode->height = currNode->left->height +1;
		else currNode->height = currNode->right->height +1;
	}
	if(currNode->parent != NULL) calcHeights(currNode->parent);
}

void checkBalance(avltree *tree,node *currNode) {
	if (currNode->balanceFactor == 2) {
		node *leftChild = currNode->left;
		if (leftChild->balanceFactor == -1) { //left right
			rotate_left(tree,leftChild);
		}
		//left left
		rotate_right(tree,currNode);
		calcBalance(tree,currNode);
 	}
 	else if (currNode->balanceFactor == -2) {
		node *rightChild = currNode->right;
		if (rightChild->balanceFactor == 1) { //right left
			rotate_right(tree,rightChild);
		}
		//right right
		rotate_left(tree,currNode);
		calcBalance(tree,currNode);
	}
}

void calcBalance(avltree *tree,node *currNode) {
	if(currNode->left != NULL && currNode->right != NULL) {
		currNode->balanceFactor = currNode->left->height - currNode->right->height;
	}
	else if(currNode->left != NULL && currNode->right == NULL) currNode->balanceFactor = currNode->left->height + 1;
	else if(currNode->left == NULL && currNode->right != NULL) currNode->balanceFactor = -1 - currNode->right->height;
	else currNode->balanceFactor = 0;
	checkBalance(tree,currNode);
	if(currNode->parent != NULL) calcBalance(tree,currNode->parent);
}

void confirmBalance(node* currNode) {
	if((currNode->left != NULL) && (currNode->right != NULL)) {
		currNode->balanceFactor = (currNode->left->height - currNode->right->height);
	}
	else if(currNode->left != NULL) {currNode->balanceFactor = (currNode->left->height + 1);}
	else if(currNode->right != NULL) {currNode->balanceFactor = (-1 - currNode->right->height);}
	else {currNode->balanceFactor = 0;}
	if(currNode->parent!=NULL) confirmBalance(currNode->parent);
}

// +------------+
// |   VERIFY   |
// +------------+
int verifyBST(node *currNode) {
	int watchdog = 0;
	if(currNode->left != NULL) {
		if(currNode->value < currNode->left->value) watchdog = 1;
	}
	if(currNode->right != NULL) {
		if(currNode->value > currNode->right->value) watchdog = 1;
	}
	if(currNode->left!=NULL) {
		if(verifyBST(currNode->left) == 1) watchdog = 1;
	}
	if(currNode->right!=NULL) {
		if(verifyBST(currNode->right) == 1) watchdog = 1;
	}
	return watchdog;
}

int verifyHeight(node *currNode) {
	int watchdog = 0;
	if((currNode->left != NULL) && (currNode->right != NULL)) {
		if(currNode->left->height > currNode->right->height) {
			if(currNode->height - currNode->left->height != 1) watchdog = 1;	
		}
		else{
			if(currNode->height - currNode->right->height != 1) watchdog = 1;
		}
	}
	else if(currNode->left != NULL) {if(currNode->height - currNode->left->height != 1) watchdog = 1;}
	else if(currNode->right != NULL) {if(currNode->height - currNode->right->height != 1) watchdog = 1;}
	else {if(currNode->height != 0) watchdog = 1;}

	if(currNode->left!=NULL) {
		if(verifyHeight(currNode->left) == 1) watchdog = 1;
	}
	if(currNode->right!=NULL) {
		if(verifyHeight(currNode->right) == 1) watchdog = 1;
	}
	return watchdog;
}

int verifyBalance(node *currNode) {
	int watchdog = 0;
	if((currNode->left != NULL) && (currNode->right != NULL)) {
		if(currNode->balanceFactor != (currNode->left->height - currNode->right->height)) watchdog = 1;
	}
	else if(currNode->left != NULL) {if(currNode->balanceFactor != (currNode->left->height + 1)) watchdog = 1;}
	else if(currNode->right != NULL) {if(currNode->balanceFactor != (-1 - currNode->right->height)) watchdog = 1;}
	else {if(currNode->balanceFactor != 0) watchdog = 1;}

	if(currNode->left!=NULL) {
		if(verifyBalance(currNode->left) == 1) watchdog = 1;
	}
	if(currNode->right!=NULL) {
		if(verifyBalance(currNode->right) == 1) watchdog = 1;
	}
	return watchdog;
}

// +--------------+
// |  STATISTICS  |
// +--------------+
int shortestPath(node *currNode) {
	int lDepth = 0,rDepth = 0;
	if(currNode == NULL) return 0;
	lDepth = shortestPath(currNode->left);
	rDepth = shortestPath(currNode->right);
	if(lDepth < rDepth) return lDepth + 1;
	else return rDepth + 1;
}

int longestPath(node *currNode) {
	int lDepth = 0,rDepth = 0;
	if(currNode == NULL) return 0;
	lDepth = longestPath(currNode->left);
	rDepth = longestPath(currNode->right);
	if(lDepth > rDepth) return lDepth + 1;
	else return rDepth + 1;
}


void statistics(avltree *tree){
	printf("\n[Total Nodes - %d]\n", nodeCount);
	printf("[Total Height - %d]\n", tree->root->height);
	printf("[Shortest Path - %d]\n", shortestPath(tree->root));
	printf("[Longest Path - %d]\n", longestPath(tree->root));
}

void clearNodeCount() {
	nodeCount = 0;
}

// +------------+
// |   FAULTS   |
// +------------+
void createBSTFault(avltree *tree) {
	int nodeValue,value;
	node *currNode;
	printf("\nSpecify Fault Node: ");
	scanf("%d",&nodeValue);
	currNode = search(tree,nodeValue);
	printf("Replace Value With: ");
	scanf("%d",&value);
	printf("[Replacing %d With %d]\n",currNode->value,value);
	currNode->value = value;
}

void createBalFault(avltree *tree) {
	int nodeValue;
	node *currNode;
	printf("\nSpecify Fault Node: ");
	scanf("%d",&nodeValue);
	currNode = search(tree,nodeValue);
	printf("[Deleting %d's Children]\n",currNode->value);
	currNode->left = NULL;
	currNode->right = NULL;
}
