package com.library.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * CollectionPerformanceBenchmark
 * Conducts micro-benchmarking comparing ArrayList vs LinkedList:
 * 1. Insertion at head: add(0, element)
 * 2. Append at tail: add(element)
 * 3. Random indexed access: get(index)
 */
public class CollectionPerformanceBenchmark {

    private static final int INSERT_HEAD_COUNT = 25000;
    private static final int APPEND_TAIL_COUNT = 100000;
    private static final int RANDOM_READ_COUNT = 10000;

    public static void runBenchmark() {
        System.out.println("=====================================================================");
        System.out.println("       COLLECTIONS PERFORMANCE BENCHMARK: ArrayList vs LinkedList     ");
        System.out.println("=====================================================================");

        // Test 1: Insert at Head add(0, elem)
        List<Integer> arrayListHead = new ArrayList<>();
        List<Integer> linkedListHead = new LinkedList<>();

        long start = System.nanoTime();
        for (int i = 0; i < INSERT_HEAD_COUNT; i++) {
            arrayListHead.add(0, i);
        }
        long arrayListHeadTimeMs = (System.nanoTime() - start) / 1_000_000;

        start = System.nanoTime();
        for (int i = 0; i < INSERT_HEAD_COUNT; i++) {
            linkedListHead.add(0, i);
        }
        long linkedListHeadTimeMs = (System.nanoTime() - start) / 1_000_000;

        // Test 2: Append at Tail add(elem)
        List<Integer> arrayListTail = new ArrayList<>();
        List<Integer> linkedListTail = new LinkedList<>();

        start = System.nanoTime();
        for (int i = 0; i < APPEND_TAIL_COUNT; i++) {
            arrayListTail.add(i);
        }
        long arrayListTailTimeMs = (System.nanoTime() - start) / 1_000_000;

        start = System.nanoTime();
        for (int i = 0; i < APPEND_TAIL_COUNT; i++) {
            linkedListTail.add(i);
        }
        long linkedListTailTimeMs = (System.nanoTime() - start) / 1_000_000;

        // Test 3: Random Access get(index) on populated list of 100,000 items
        start = System.nanoTime();
        long sumArray = 0;
        int step = APPEND_TAIL_COUNT / RANDOM_READ_COUNT;
        for (int i = 0; i < RANDOM_READ_COUNT; i++) {
            sumArray += arrayListTail.get(i * step);
        }
        long arrayListGetTimeMs = (System.nanoTime() - start) / 1_000_000;

        start = System.nanoTime();
        long sumLinked = 0;
        for (int i = 0; i < RANDOM_READ_COUNT; i++) {
            sumLinked += linkedListTail.get(i * step);
        }
        long linkedListGetTimeMs = (System.nanoTime() - start) / 1_000_000;

        // Print Formatted Timing Table
        System.out.printf("%-30s | %-15s | %-15s | %-15s%n",
                "Operation & Size", "ArrayList (ms)", "LinkedList (ms)", "Fastest Choice");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-30s | %-15d | %-15d | %-15s%n",
                "add(0, item) [" + INSERT_HEAD_COUNT + " items]",
                arrayListHeadTimeMs, linkedListHeadTimeMs,
                (linkedListHeadTimeMs < arrayListHeadTimeMs ? "LinkedList" : "ArrayList"));

        System.out.printf("%-30s | %-15d | %-15d | %-15s%n",
                "add(item) tail [" + APPEND_TAIL_COUNT + " items]",
                arrayListTailTimeMs, linkedListTailTimeMs,
                (arrayListTailTimeMs < linkedListTailTimeMs ? "ArrayList" : "LinkedList"));

        System.out.printf("%-30s | %-15d | %-15d | %-15s%n",
                "get(index) [" + RANDOM_READ_COUNT + " reads]",
                arrayListGetTimeMs, linkedListGetTimeMs,
                (arrayListGetTimeMs < linkedListGetTimeMs ? "ArrayList" : "LinkedList"));

        System.out.println("=====================================================================\n");
    }
}
