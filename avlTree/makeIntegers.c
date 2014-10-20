#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "makeIntegers.h"

#define MAX_SIZE 1048576 // One Megabyte

int * swap(int arr[], int size) {
    // Generate two random numbers between zero and the last element's index
    int i;
    int j;
    i = (rand()%size);
    j = (rand()%size);

    // If they are same, get a new one!
    while( i == j){
        j = (rand()%size);
    }
    
    // Swap the two values of the indexes chosen at random
    int temp;
    temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    i = 0;
    j = 0;

    return arr;
}

int * genInts(int count, int start, int step, int swaps) {
    // Allocate an array the size of 1 Megabyte
    static int* arr;
    arr = malloc(MAX_SIZE * sizeof(count));
    
    // Populate the array
    int i;
    int j = start;
    for(i = 0; i < count; i++){
        arr[i] = j;
        j += step;
    }

    // Perform swaps, if any
    // Make randomness seed from time (A constant, ever changing variable)
    srand(time(NULL));
    int k = 0;
    while (k < swaps) {
        arr = swap(arr, count);
        k++;
    }

    return arr;
}

/* Eta Corhort */
