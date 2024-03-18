
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PrimAlgorithm {
    static final int V = 5;

    // Tìm đỉnh có key nhỏ nhất từ các đỉnh chưa được kết nối với cây MST
    int minKey(int key[], boolean mstSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    // In cây MST tìm được
    void printMST(int parent[], int graph[][]) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }

    // Tìm cây MST cho đồ thị được biểu diễn trong một ma trận kề
    void primMST(int graph[][]) {
        int parent[] = new int[V];
        int key[] = new int[V];
        boolean mstSet[] = new boolean[V];

        // Khởi tạo tất cả các key là vô cùng và mstSet là false
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Bắt đầu từ đỉnh 0
        key[0] = 0;
        parent[0] = -1; // Đỉnh đầu tiên của MST không có đỉnh cha

        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);

            // Thêm đỉnh đã chọn vào cây MST
            mstSet[u] = true;

            // Cập nhật các key và đỉnh cha của các đỉnh kề của đỉnh vừa chọn
            for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        // In cây MST tìm được
        printMST(parent, graph);
    }

    public static void main(String[] args) throws IOException {
        // Đọc dữ liệu từ file Excel
        FileInputStream file = new FileInputStream(new File("data.xlsx"));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0); // Sử dụng sheet đầu tiên

        // Khởi tạo ma trận kề từ dữ liệu đọc được
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < V; j++) {
                graph[i][j] = (int) row.getCell(j).getNumericCellValue();
            }
        }

        PrimAlgorithm primAlgorithm = new PrimAlgorithm();
        primAlgorithm.primMST(graph);

        file.close();
    }
}
