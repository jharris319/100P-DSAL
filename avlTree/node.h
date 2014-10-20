typedef struct nodeobject {
	int value;
	int height;
	int balanceFactor;
	struct nodeobject *parent;
	struct nodeobject *left;
	struct nodeobject *right;
} node;

extern node *newAVLNode(int value,node *parent);
