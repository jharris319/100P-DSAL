CC=gcc
CFLAGS=-Wall -g

problem2: problem2.o node.o avl.o makeIntegers.o
	$(CC) -o problem2 problem2.o node.o avl.o makeIntegers.o

clean:
	rm *.o *.dot *.png *.txt problem2

valgrind:
	make problem2
	valgrind --leak-check=yes --track-origins=yes ./problem2
