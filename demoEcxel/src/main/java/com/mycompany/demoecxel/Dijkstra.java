
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Dijkstra {
    private int V;
    private int[][] graph;

    public Dijkstra(int vertices) {
        this.V = vertices;
        this.graph = new int[vertices][vertices];
    }

    public int min_distance(int[] dist, boolean[] visited) {
        int min_dist = Integer.MAX_VALUE;
        int min_index = -1;
        for (int v = 0; v < V; v++) {
            if (dist[v] < min_dist && !visited[v]) {
                min_dist = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    public void dijkstra(int src) {
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        dist[src] = 0;

        for (int i = 0; i < V; i++) {
            int u = min_distance(dist, visited);
            visited[u] = true;
            for (int v = 0; v < V; v++) {
                if (graph[u][v] > 0 && !visited[v] && dist[v] > dist[u] + graph[u][v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        print_solution(dist);
    }

    public void print_solution(int[] dist) {
        System.out.println("Vertex \tDistance from Source");
        for (int node = 0; node < V; node++) {
            System.out.println(node + "\t" + dist[node]);
        }
    }

    public void readfile() {
        try {
            FileInputStream file = new FileInputStream(new File("data.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("Unknown Type" + "\t");
                    }
                }
                System.out.println();
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
