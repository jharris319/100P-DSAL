typedef struct avlobject {
	node *root;
} avltree;

extern avltree *newAVLTree();

node *insert(avltree *tree,int value);
node *search(avltree *tree,int value);
void display();
node *delete(avltree *tree,int value);
void inorderTraversal(node *currNode);
void preorderTraversal(node *currNode);
void postorderTraversal(node *currNode);
void confirmBalance(node* currNode);
void calcHeights(node *currNode);
void calcBalance(avltree *tree,node *currNode);
int verifyBST(node *currNode);
int verifyHeight(node *currNode);
int verifyBalance(node *currNode);
void statistics(avltree *tree);
void clearNodeCount();
void createBSTFault(avltree *tree);
void createBalFault(avltree *tree);