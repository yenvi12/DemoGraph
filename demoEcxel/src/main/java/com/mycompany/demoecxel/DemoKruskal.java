import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
// Định nghĩa một cạnh trong đồ thị
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Sắp xếp các cạnh theo trọng số
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

// Định nghĩa một tập hợp con để sử dụng trong thuật toán Kruskal
class Subset {
    int parent, rank;
}

// Class chứa thuật toán Kruskal
class KruskalAlgorithm {
    private int V, E; // Số đỉnh và số cạnh trong đồ thị
    private Edge edge[]; // Mảng chứa các cạnh

    // Khởi tạo đồ thị với số đỉnh V và số cạnh E
    KruskalAlgorithm(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    // Tìm tập con chứa đỉnh u
    int find(Subset subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // Hợp nhất hai tập con chứa x và y
    void Union(Subset subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Tìm cây MST bằng thuật toán Kruskal
    void KruskalMST() {
        Edge result[] = new Edge[V]; // Chứa các cạnh của cây MST
        int e = 0; // Số cạnh đã thêm vào cây MST
        int i = 0; // Chỉ số cạnh được xét đến

        // Sắp xếp các cạnh theo trọng số
        Arrays.sort(edge);

        // Tạo một mảng con để lưu các tập con của các đỉnh
        Subset subsets[] = new Subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new Subset();

        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        // Chọn các cạnh theo thứ tự tăng dần của trọng số
        i = 0;
        while (e < V - 1) {
            Edge next_edge = edge[i++];
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // Nếu cạnh không tạo thành chu trình khi thêm vào cây MST thì thêm nó vào cây
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }

        // In ra các cạnh của cây MST và tổng trọng số của cây
        System.out.println("Following are the edges in the constructed MST:");
        int minimumCost = 0;
        for (i = 0; i < e; ++i) {
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.println("Minimum Cost Spanning Tree: " + minimumCost);
    }
    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream(new File("data.xlsx"));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // Sử dụng sheet đầu tiên

        int V = sheet.getPhysicalNumberOfRows(); // Số hàng trong bảng tính
        int E = V * (V - 1) / 2; // Số cạnh tối đa trong một đồ thị đầy đủ

        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(V, E);

        int edgeCount = 0;
        for (int i = 0; i < V; i++) {
            Row row = sheet.getRow(i);
            for (int j = i + 1; j < V; j++) {
                Cell cell = row.getCell(j);
                int weight = (int) cell.getNumericCellValue();
                kruskalAlgorithm.edge[edgeCount].src = i;
                kruskalAlgorithm.edge[edgeCount].dest = j;
                kruskalAlgorithm.edge[edgeCount].weight = weight;
                edgeCount++;
            }
        }

        kruskalAlgorithm.KruskalMST();

        file.close();
    }
    
}