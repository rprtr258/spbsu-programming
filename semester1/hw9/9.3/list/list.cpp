#include <stdio.h>
#include "../huffmanNode.h"
#include "list.h"

struct ListNode {
    HuffmanNode *value = nullptr;
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

void deleteSingleListNode(ListNode *node) {
    if (node == nullptr)
        return;
    
    if (node->value != nullptr)
        delete node->value;
    delete node;
}

void deleteListNode(ListNode *node) {
    if (node == nullptr)
        return;
    
    if (node->next != nullptr)
        deleteListNode(node->next);
    
    deleteSingleListNode(node);
}

void deleteList(LinkedList *&list) {
    if (list == nullptr)
        return;
    
    deleteListNode(list->head);
    delete list;
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

void insertAtEnd(LinkedList *list, HuffmanNode *value) {
    if (list == nullptr)
        return;
    
    ListNode *node = new ListNode();
    node->value = copy(value);
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

void insertAtBegin(LinkedList *list, HuffmanNode *value) {
    if (list == nullptr)
        return;
    
    ListNode *node = new ListNode();
    node->value = copy(value);
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

void insertAtIndex(LinkedList *list, HuffmanNode *value, int const index) {
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
    
    ListNode *node = new ListNode();
    node->value = copy(value);
    
    ListNode *temp = getListListNode(list, index);

    temp->prev->next = node;
    node->prev = temp->prev;
    node->next = temp;
    temp->prev = node;
    
    list->size++;
}

HuffmanNode* popBegin(LinkedList *list) {
    HuffmanNode *result = peekBegin(list);
    deleteBegin(list);
    return result;
}

HuffmanNode* popEnd(LinkedList *list) {
    HuffmanNode *result = peekEnd(list);
    deleteEnd(list);
    return result;
}

HuffmanNode* popIndex(LinkedList *list, int const index) {
    HuffmanNode *result = peekIndex(list, index);
    deleteIndex(list, index);
    return result;
}

HuffmanNode* peekBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    return copy(list->head->value);
}

HuffmanNode* peekEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    return copy(list->tail->value);
}

HuffmanNode* peekIndex(LinkedList *list, int const index) {
    if (list == nullptr || list->size == 0)
        return nullptr;
    
    if (index < 0 || index >= list->size)
        return nullptr;
    
    ListNode *temp = getListListNode(list, index);
    
    return copy(temp->value);
}

void deleteBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        deleteSingleListNode(list->head);
        list->head = nullptr;
        list->tail = nullptr;
    } else {
        ListNode *temp = list->head->next;
        deleteSingleListNode(list->head);
        list->head = temp;
        temp->prev = nullptr;
    }
    list->size--;
}

void deleteEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        deleteSingleListNode(list->tail);
        list->head = nullptr;
        list->tail = nullptr;
    } else {
        ListNode *temp = list->tail->prev;
        deleteSingleListNode(list->tail);
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
    deleteSingleListNode(temp);
    
    list->size--;
}
