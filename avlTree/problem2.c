#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "node.h"
#include "avl.h"
#include "makeIntegers.h"

void menu() {
	system("clear");
    printf("Available Commands:\n");
    printf("    i XXX - Insert value XXX into the data structure\n");
    printf("    d XXX - Delete value XXX from the data structure\n");
    printf("    f XXX - Find value XXX in the data structure\n");
    printf("    v     - Visualize the data structure using graphviz\n");
    printf("    V     - Verify the correctness of the data structure\n");
    printf("    b XXX - Inject a fault of type XXX into the data structure\n");
    printf("    p XXX - Populate the tree with a set of integers from file XXX\n");
    printf("    g XXX - Generate XXX unique random numbers and populate the tree\n");
    printf("    s     - Print statistics concerning the current state of the data structure\n");
    printf("    l XXX - List the elements in the data structure; XXX should specify the traversal\n");
    printf("    ~     - Destroy entire tree\n");
    printf("    q     - Quit Problem2\n");
}

int main(int argc, char *argv[]) {
	char command,fileName[512];
	int flag, *ints, j,data;
	FILE *f;
	node *sNode;
	avltree *tree=newAVLTree();	
	menu();

	while(42) {
		printf("\n%s", "At your command: ");
		scanf("%s",&command);
		if (command == 'i' || command == 'd' || command == 'f' ||
			command == 'b' || command == 'g' || command == 'l') scanf("%d",&flag);
		else if (command == 'p') scanf("%s",fileName);


		switch(command){ 
			case 'i':
				menu();
				printf("\n[Inserting %d]\n", flag);
				sNode = insert(tree,flag);
				calcHeights(sNode);
				calcBalance(tree,sNode);
				break;
			case 'd':
				menu();
				printf("\n[Deleting %d]\n", flag);
				sNode = delete(tree,flag);
				if(sNode != NULL) {
					calcHeights(sNode);
					calcBalance(tree,sNode);
				}
				break;
			case 'f':
				sNode = search(tree,flag);
				menu();
				printf("\n[Find %d]\n", flag);
				if(sNode != NULL) {
					printf("\n[Value %d", sNode->value);
					printf(" / Height %d", sNode->height);
					printf(" / Balance Factor %d", sNode->balanceFactor);
					if(sNode->parent != NULL)printf(" / Parent %d", sNode->parent->value);
					if(sNode->left != NULL)printf(" / Left %d", sNode->left->value);
					if(sNode->right != NULL)printf(" / Right %d", sNode->right->value);
					printf("]\n");
				}
				else printf("\n[%d Not Found]\n",flag);
				break;
			case 'v':
				menu();
				if (tree->root == NULL) printf("\n[Cannot Display Empty Tree]\n");
				else {
					display(tree);
					system("./qgraph.sh graph&");
					printf("\n[Opening Tree Visualization]\n");
				}
				break;
			case 'V':
				menu();
				printf("\n[Verification Requested]\n\n");
				if(verifyBST(tree->root) == 0) {
					printf("[BST Properties Valid]\n");
				}
				else printf("[ERROR -- BST Properties NOT Valid]\n");
				if(verifyHeight(tree->root) == 0) {
					printf("[AVL Height Properties Valid]\n");
				}
				else printf("[ERROR -- AVL Height Properties NOT Valid]\n");
				if(verifyBalance(tree->root) == 0) {
					printf("[AVL Balance Properties Valid]\n");
				}
				else printf("[ERROR -- AVL Balance Properties NOT Valid]\n");
				break;
			case 'b':
				menu();
				if(flag == 1) createBSTFault(tree);
				else if(flag == 2) createBalFault(tree);
				break;
			case 'p':
				f = fopen(fileName, "r");
				data = 0;
				while (fscanf(f,"%d", &data) != EOF) {
					sNode = insert(tree,data);
					calcHeights(sNode);
					calcBalance(tree,sNode);
				}
        		fclose(f);
        		menu();
        		printf("\n[Tree Populated From: %s]\n", fileName);
				break;
			case 'g':
				ints = genInts(flag, 0, 10, flag/2);
				f = fopen("rand.txt", "w");
				for(j = 0; j < flag; j++) {
					fprintf(f,"%d\n",ints[j]);
					sNode = insert(tree,ints[j]);
					calcHeights(sNode);
					calcBalance(tree,sNode);
				}
        		fclose(f);
        		menu();
        		printf("\n[Generated %d Numbers -- Tree Populated]\n", flag);
				break;
			case 's':
				menu();
				if (tree->root == NULL) printf("\n[Cannot Generate Stats of Empty Tree]\n");
				printf("\n[Calculating Statistics]\n");
				statistics(tree);
				break;
			case 'l':
				menu();
				if (tree->root == NULL) printf("\n[Cannot Traverse Empty Tree]\n");
				else {
					if(flag==1) {
					printf("\n[Pre-Order Traversal]\n\n");
					preorderTraversal(tree->root);
					}
					else if (flag==2) {
						printf("\n[In-Order Traversal]\n\n");
						inorderTraversal(tree->root);
					}
					else if (flag==3) {
						printf("\n[Post-Order Traversal]\n\n");
						postorderTraversal(tree->root);
					}
					printf("%c", '\n');
				}
				break;
			case 'q':
				system("clear");
				exit(0);
				break;
			case '~':
				free(tree);
				tree=newAVLTree();
				clearNodeCount();
				menu();
				printf("\n[Tree Destroyed]\n");
				break;
			default:
				menu();
				printf("\n[Command Not Understood]\n");
				break;
		}
	}
	return 0;
}
