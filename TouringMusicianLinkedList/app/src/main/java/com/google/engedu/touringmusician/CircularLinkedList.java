/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;
import android.util.Log;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private static final String TAG = "CircularLinkedList.java";

    private class Node {
        Point point;
        Node prev, next;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    Node head;

    public void insertBeginning(Point p) {
        //creating new linkedlist
        if(head==null){
            head = new Node();
            head.next = head;
            head.prev = head;
            head.point = p;
        }
        //if linkedlist exists
        else{
            Node nodeToAdd = new Node();
            Node temp;
            nodeToAdd.point = p;
            head.prev.next = nodeToAdd;
            temp = head.prev;
            head.prev = nodeToAdd;
            nodeToAdd.next = head;
            nodeToAdd.prev = temp;
            head = nodeToAdd;
        }


        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        if(head == null) return 0;
        float total = 0;
        Node prevNode = head;
        Node curNode = prevNode;
        int counter = 0;
        while(curNode.next != head){
            curNode = curNode.next;
            total+=distanceBetween(prevNode.point, curNode.point);
            prevNode = prevNode.next;
            counter++;
        }
        total+=distanceBetween(curNode.point, head.point);


        return total;


    }

    public void insertNearest(Point p) {
        if(head==null){
            head = new Node();
            head.next = head;
            head.prev = head;
            head.point = p;
        }
        //if linkedlist exists
        else {
            Node nodeToInsert = new Node();
            nodeToInsert.point = p;
            Node minNode = head;
            float minDist = distanceBetween(head.point, nodeToInsert.point);
            Node curNode = head;
            while (curNode.next != head) {
                curNode = curNode.next;
                float dist = distanceBetween(curNode.point, nodeToInsert.point);
                if (dist < minDist) {
                    minDist = dist;
                    minNode = curNode;
                }

            }
            Node temp = minNode.next;
            minNode.next = nodeToInsert;
            nodeToInsert.prev = minNode;
            nodeToInsert.next = temp;
            temp.prev = nodeToInsert;
        }
        }

    public void insertSmallest(Point p) {
        if(head==null){
            head = new Node();
            head.next = head;
            head.prev = head;
            head.point = p;
        }
        else if(head.next == head){
            Node nodeToInsert = new Node();
            nodeToInsert.point = p;
            head.next = nodeToInsert;
            head.prev = nodeToInsert;
            nodeToInsert.next = head;
            nodeToInsert.prev = head;
        }
        else{
            Node nodeToInsert = new Node();
            nodeToInsert.point = p;

            Node minPrevNode = head;
            Node prevNode = head;
            Node curNode = prevNode.next;
            float minDistance = distanceBetween(prevNode.point, nodeToInsert.point) + distanceBetween(nodeToInsert.point, curNode.point) - distanceBetween(prevNode.point, curNode.point);
            curNode = curNode.next;
            prevNode = prevNode.next;
            while(prevNode != head){

                float dist = distanceBetween(prevNode.point, nodeToInsert.point) + distanceBetween(nodeToInsert.point, curNode.point) - distanceBetween(prevNode.point, curNode.point);
                if (dist < minDistance){
                    minPrevNode = prevNode;
                    minDistance = dist;
                }
                curNode = curNode.next;
                prevNode = prevNode.next;
            }
            Node temp = minPrevNode.next;
            minPrevNode.next = nodeToInsert;
            nodeToInsert.prev = minPrevNode;
            nodeToInsert.next = temp;
            temp.prev = nodeToInsert;
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current  != null);
        }
        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
