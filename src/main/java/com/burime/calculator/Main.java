package com.burime.calculator;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("=== Лабораторная работа № 10: Тестовые двойники и графы ===");

        String edgeListData = """
                4
                1 2
                1 3
                3 4
                3 5
                """;

        IGraphReader reader = new EdgeListGraphReader();
        IGraphWriter writer = new AdjacencyListGraphWriter();

        Graph graph = new Graph(reader, writer);
        graph.load(new StringReader(edgeListData));

        System.out.println("Вершины: " + graph.getVertexList());
        System.out.println("Рёбер: " + graph.getEdgeList().size());

        // Обратный обход дерева (Post-order): дети перед родителем
        System.out.println("Обратный обход дерева (Post-order): " + graph.postOrderTraversal());

        // Сохранение в Список связности (СС)
        StringWriter stringWriter = new StringWriter();
        graph.save(stringWriter);
        System.out.println("Экспортированный Список связности (СС):\n" + stringWriter);
    }
}