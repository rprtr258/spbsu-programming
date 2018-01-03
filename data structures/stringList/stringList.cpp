#include <stdio.h>
#include "stringList.h"

struct Node {
    String *value = nullptr;
    Node *prev = nullptr;
    Node *next = nullptr;
};

Node* getListNode(StringLinkedList *list, Index const index) {
    if (index >= list->size)
        return nullptr;
    
    Node *result = list->head;
    for (Index i = 0; i < index; i++)
        result = result->next;
    return result;
}

StringLinkedList* stringListCreate() {
    StringLinkedList *result = new StringLinkedList();
    return result;
}

StringLinkedList* stringListCopy(StringLinkedList *other) {
    if (other == nullptr)
        return nullptr;
    
    StringLinkedList *result = new StringLinkedList();
    for (Index i = 0; i < other->size; i++) {
        Node *node = getListNode(other, i);
        stringListInsertAtEnd(result, node->value);
    }
    return result;
}

StringLinkedList* stringListMergeSorted(StringLinkedList *first, StringLinkedList *second) {
    if (first == nullptr || second == nullptr)
        return nullptr;
    if (!stringListIsSorted(first) || !stringListIsSorted(second))
        return nullptr;
    
    StringLinkedList *result = stringListCreate();
    Index i1 = 0;
    Index i2 = 0;
    while (i1 < first->size && i2 < second->size) {
        Node *firstNode = getListNode(first, i1);
        Node *secondNode = getListNode(second, i2);
        if (stringIsLess(firstNode->value, secondNode->value)) {
            stringListInsertAtEnd(result, firstNode->value);
            i1++;
        } else {
            stringListInsertAtEnd(result, secondNode->value);
            i2++;
        }
    }
    while (i1 < first->size) {
        Node *node = getListNode(first, i1);
        stringListInsertAtEnd(result, node->value);
        i1++;
    }
    while (i2 < second->size) {
        Node *node = getListNode(second, i2);
        stringListInsertAtEnd(result, node->value);
        i2++;
    }
    return result;
}

void deleteSingleNode(Node *node) {
    stringDelete(node->value);
    delete node;
}

void deleteNodes(StringLinkedList *list) {
    Node *tmp = list->head;
    while (tmp != nullptr) {
        Node *nextTmp = tmp->next;
        deleteSingleNode(tmp);
        tmp = nextTmp;
    }
}

void stringListErase(StringLinkedList *list) {
    if (list == nullptr)
        return;
    
    deleteNodes(list);
    list->head = nullptr;
    list->tail = nullptr;
    list->size = 0;
}

void stringListDelete(StringLinkedList *&list) {
    if (list == nullptr)
        return;
    
    deleteNodes(list);
    delete list;
    list = nullptr;
}

void stringListInsertAtEnd(StringLinkedList *list, String *value) {
    if (list == nullptr)
        return;
    
    Node *node = new Node();
    node->value = stringCopy(value);
    if (list->head == nullptr) {
        list->head = node;
        list->tail = node;
    } else {
        list->tail->next = node;
        node->prev = list->tail;
        list->tail = node;
    }
    list->size++;
}

void stringListInsertAtEnd(StringLinkedList *list, char const *value) {
    String *temp = stringCreate(value);
    stringListInsertAtEnd(list, temp);
    stringDelete(temp);
}

void stringListInsertAtBegin(StringLinkedList *list, String *value) {
    if (list == nullptr)
        return;
    
    Node *node = new Node();
    node->value = stringCopy(value);
    if (list->head == nullptr) {
        list->head = node;
        list->tail = node;
    } else {
        list->head->prev = node;
        node->next = list->head;
        list->head = node;
    }
    list->size++;
}

void stringListInsertAtBegin(StringLinkedList *list, char const *value) {
    String *temp = stringCreate(value);
    stringListInsertAtBegin(list, temp);
    stringDelete(temp);
}

void stringListInsertAtIndex(StringLinkedList *list, String *value, Index const index) {
    if (list == nullptr)
        return;
    
    if (index > list->size)
        return;
    
    if (index == list->size) {
        stringListInsertAtEnd(list, value);
        return;
    }
    
    if (index == 0) {
        stringListInsertAtBegin(list, value);
        return;
    }
    
    Node *node = new Node();
    node->value = stringCopy(value);
    
    Node *temp = getListNode(list, index);

    temp->prev->next = node;
    node->prev = temp->prev;
    node->next = temp;
    temp->prev = node;
    
    list->size++;
}

void stringListInsertAtIndex(StringLinkedList *list, char const *value, Index const index) {
    String *temp = stringCreate(value);
    stringListInsertAtIndex(list, temp, index);
    stringDelete(temp);
}

String* stringListPeekBegin(StringLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    return stringCopy(list->head->value);
}

String* stringListPeekEnd(StringLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    return stringCopy(list->tail->value);
}

String* stringListPeekIndex(StringLinkedList *list, Index index) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    if (index >= list->size)
        return nullptr;
    
    Node *temp = getListNode(list, index);
    
    return stringCopy(temp->value);
}

void stringListDeleteBegin(StringLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        deleteSingleNode(list->head);
        list->tail = nullptr;
    } else {
        Node *temp = list->head->next;
        deleteSingleNode(list->head);
        list->head = temp;
        temp->prev = nullptr;
    }
    list->size--;
}

void stringListDeleteEnd(StringLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        deleteSingleNode(list->tail);
        list->head = nullptr;
    } else {
        Node *temp = list->tail->prev;
        deleteSingleNode(list->tail);
        list->tail = temp;
        temp->next = nullptr;
    }
    list->size--;
}

void stringListDeleteIndex(StringLinkedList *list, Index const index) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (index >= list->size)
        return;
    
    if (index == 0) {
        stringListDeleteBegin(list);
        return;
    }
    
    if (index == list->size - 1) {
        stringListDeleteEnd(list);
        return;
    }
    
    Node *temp = getListNode(list, index);
    temp->prev->next = temp->next;
    temp->next->prev = temp->prev;
    deleteSingleNode(temp);
    
    list->size--;
}

void stringListLeaveUniques(StringLinkedList *list) {
    if (list == nullptr)
        return;
    
    for (Index i = 0; i < list->size; i++) {
        Node *node = getListNode(list, i);
        Index prev = stringListFind(list, node->value);
        if (prev != list->size && prev < i) {
            stringListDeleteIndex(list, i);
            i--;
        }
    }
}

void mergeSort(StringLinkedList *&list) {
    if (list->size == 1)
        return;
    
    int half = list->size / 2;
    StringLinkedList *first = stringListCreate();
    StringLinkedList *second = stringListCreate();
    
    for (int i = 0; i < half; i++) {
        Node *node = getListNode(list, i);
        stringListInsertAtEnd(first, node->value);
    }
    for (int unsigned i = half; i < list->size; i++) {
        Node *node = getListNode(list, i);
        stringListInsertAtEnd(second, node->value);
    }
    
    mergeSort(first);
    mergeSort(second);
    stringListDelete(list);
    
    list = stringListMergeSorted(first, second);
    
    stringListDelete(first);
    stringListDelete(second);
}

void stringListSort(StringLinkedList *&list) {
    if (list == nullptr || list->size == 0)
        return;
    
    mergeSort(list);
}

Index stringListFind(StringLinkedList *list, String *value) {
    if (list == nullptr) {
        throw 1;
        return 37;
    }
    
    Index result = list->size;
    
    Index i = 0;
    Node *temp = list->head;
    while (temp != nullptr) {
        if (stringAreEqual(temp->value, value)) {
            result = i;
            break;
        }
        temp = temp->next;
        i++;
    }
    
    return result;
}

Index stringListFind(StringLinkedList *list, char const *value) {
    String *temp = stringCreate(value);
    Index result = stringListFind(list, temp);
    stringDelete(temp);
    return result;
}
    
bool stringListIsSorted(StringLinkedList *list) {
    if (list == nullptr)
        return false;
    
    int result = true;
    for (int unsigned i = 0; i + 1 < list->size; i++) {
        Node *node = getListNode(list, i);
        Node *nextNode = getListNode(list, i + 1);
        if (!stringIsLess(node->value, nextNode->value)) {
            result = false;
            break;
        }
    }
    return result;
}

bool stringListContains(StringLinkedList *list, String *value) {
    if (list == nullptr) {
        throw 1;
        return true;
    }
    
    return (stringListFind(list, value) != list->size);
}

bool stringListContains(StringLinkedList *list, char const *value) {
    String *temp = stringCreate(value);
    bool result = stringListContains(list, temp);
    stringDelete(temp);
    return result;
}

String** stringListGetAsArray(StringLinkedList *list) {
    if (list == nullptr)
        return nullptr;
    
    String **result = new String*[list->size];
    Index i = 0;
    Node *node = list->head;
    while (node != nullptr) {
        result[i] = stringCopy(node->value);
        node = node->next;
        i++;
    }
    return result;
}

void printNode(Node *node) {
    if (node == nullptr)
        printf("(null)");
    else
        printf("\"%s\"", node->value->data);
}

void stringListPrint(StringLinkedList *list) {
    if (list == nullptr)
        return;
    
    printf("[");
    Node *temp = list->head;
    while (temp != nullptr) {
        printNode(temp);
        if (temp->next != nullptr)
            printf(", ");
        temp = temp->next;
    }
    printf("]\n");
}

void stringListPrintSiblings(StringLinkedList *list, Index const index) {
    if (list == nullptr)
        return;
    if (index >= list->size)
        return;
    
    Node *temp = getListNode(list, index);
    
    printf("prev: ");
    printNode(temp->prev);
    printf("\nnext: ");
    printNode(temp->next);
    printf("\n");
}

