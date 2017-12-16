#include <stdio.h>
#include "list.h"

struct ListNode {
    int value = 0;
    ListNode *prev = nullptr;
    ListNode *next = nullptr;
};

ListNode* getListListNode(LinkedList *list, int const index) {
    if (index < 0 || index >= list->size)
        return nullptr;
    
    ListNode *result = list->head;
    for (int i = 0; i < index; i++)
        result = result->next;
    return result;
}

LinkedList* createList() {
    LinkedList *result = new LinkedList();
    return result;
}

void deleteListNode(ListNode *ListNode) {
    if (ListNode == nullptr)
        return;
    
    if (ListNode->next != nullptr)
        deleteListNode(ListNode->next);
    delete ListNode;
}

void deleteList(LinkedList *&list) {
    if (list == nullptr)
        return;
    
    deleteListNode(list->head);
    list = nullptr;
}

void eraseList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    deleteListNode(list->head);
    list->head = nullptr;
    list->tail = nullptr;
    list->size = 0;
}

void insertAtEnd(LinkedList *list, int const value) {
    if (list == nullptr)
        return;
    
    ListNode *ListNode = new ListNode();
    ListNode->value = value;
    if (list->head == nullptr) {
        list->head = ListNode;
        list->tail = ListNode;
    } else {
        list->tail->next = ListNode;
        ListNode->prev = list->tail;
        list->tail = ListNode;
    }
    list->size++;
}

void insertAtBegin(LinkedList *list, int const value) {
    if (list == nullptr)
        return;
    
    ListNode *ListNode = new ListNode();
    ListNode->value = value;
    if (list->head == nullptr) {
        list->head = ListNode;
        list->tail = ListNode;
    } else {
        list->head->prev = ListNode;
        ListNode->next = list->head;
        list->head = ListNode;
    }
    list->size++;
}

void insertAtIndex(LinkedList *list, int const value, int const index) {
    if (list == nullptr)
        return;
    
    if (index < 0 || index > list->size)
        return;
    
    if (index == list->size) {
        insertAtEnd(list, value);
        return;
    }
    
    if (index == 0) {
        insertAtBegin(list, value);
        return;
    }
    
    ListNode *ListNode = new ListNode();
    ListNode->value = value;
    
    ListNode *temp = getListListNode(list, index);

    temp->prev->next = ListNode;
    ListNode->prev = temp->prev;
    ListNode->next = temp;
    temp->prev = ListNode;
    
    list->size++;
}

int peekBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->head->value;
}

int peekEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->tail->value;
}

int peekIndex(LinkedList *list, int const index) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    if (index < 0 || index >= list->size)
        return -1;
    
    ListNode *temp = getListListNode(list, index);
    
    return temp->value;
}

void deleteBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        delete list->head;
        list->head = nullptr;
        list->tail = nullptr;
    } else {
        ListNode *temp = list->head->next;
        delete list->head;
        list->head = temp;
        temp->prev = nullptr;
    }
    list->size--;
}

void deleteEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        delete list->tail;
        list->head = nullptr;
        list->tail = nullptr;
    } else {
        ListNode *temp = list->tail->prev;
        delete list->tail;
        list->tail = temp;
        temp->next = nullptr;
    }
    list->size--;
}

void deleteIndex(LinkedList *list, int const index) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (index < 0 || index >= list->size)
        return;
    
    if (index == 0) {
        deleteBegin(list);
        return;
    }
    
    if (index == list->size - 1) {
        deleteEnd(list);
        return;
    }
    
    ListNode *temp = getListListNode(list, index);
    temp->prev->next = temp->next;
    temp->next->prev = temp->prev;
    delete temp;
    
    list->size--;
}

int findElement(LinkedList *list, int const value) {
    if (list == nullptr)
        return -1;
    
    int result = -1;
    
    int i = 0;
    ListNode *temp = list->head;
    while (temp != nullptr) {
        if (temp->value == value) {
            result = i;
            break;
        }
        temp = temp->next;
        i++;
    }
    
    return result;
}

void printList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    printf("[");
    ListNode *temp = list->head;
    while (temp != nullptr) {
        printf("%d", temp->value);
        if (temp->next != nullptr)
            printf(", ");
        temp = temp->next;
    }
    printf("]\n");
}

void printListNode(ListNode *ListNode) {
    if (ListNode == nullptr)
        printf("(null)");
    else
        printf("%d", ListNode->value);
}

void printSiblings(LinkedList *list, int const index) {
    if (list == nullptr)
        return;
    if (index < 0 || index >= list->size)
        return;
    
    ListNode *temp = getListListNode(list, index);
    
    printf("prev: ");
    printListNode(temp->prev);
    printf("\nnext: ");
    printListNode(temp->next);
    printf("\n");
}
