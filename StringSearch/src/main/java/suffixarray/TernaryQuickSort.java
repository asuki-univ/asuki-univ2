package suffixarray;

public class TernaryQuickSort {
    public static void sort(String[] ss) {
        sort(ss, 0, ss.length, 0);
    }

    private static void sort(String[] ss, int left, int right, int depth) {
        System.out.printf("sort : left = %d, right = %d, depth = %d\n", left, right, depth);

        int size = right - left;
        if (size <= 1)
            return;

        // pivot を１つ選ぶ。もし、pivot が見つからなければ、depth より長い文字列がないということなので、全部同じ文字列なので、ソート済みとなる。
        int pivot = findPivotChar(ss, left, right, depth);

        int leftPivotPos = left, rightPivotPos = right - 1;
        int i = left, j = right - 1;

        // 区間 (left, right) を４分割する
        while (true) {
            while (i <= j) {
                int k = ss[i].length() <= depth ? -1 : ss[i].charAt(depth);
                if (k > pivot)
                    break;
                else if (k == pivot)
                    swap(ss, i, leftPivotPos++);
                ++i;
            }
            while (i <= j) {
                int k = ss[j].length() <= depth ? -1 : ss[j].charAt(depth);
                if (k < pivot)
                    break;
                else if (k == pivot)
                    swap(ss, j, rightPivotPos--);
                --j;
            }
            if (i > j)
                break;
            swap(ss, i++, j--);
        }

        // pivot と等しいものを左に
        int leftSwapSize = Math.min(leftPivotPos - left, i - leftPivotPos);
        for (int k = 0; k < leftSwapSize; ++k)
            swap(ss, left + k, j - k);
        leftPivotPos = left + (i - leftPivotPos);

        int rightSwapSize = Math.min(right - rightPivotPos - 1, rightPivotPos - j);
        for (int k = 0; k < rightSwapSize; ++k)
            swap(ss, right - k - 1, i + k);
        rightPivotPos = right - (rightPivotPos - j);

        sort(ss, left, leftPivotPos, depth);
        if (rightPivotPos - leftPivotPos != right - left || ss[left].length() >= depth)
            sort(ss, leftPivotPos, rightPivotPos, depth + 1);
        sort(ss, rightPivotPos, right, depth);
    }

    // pivot となる文字を返す。もし長さが足りなければ、-1 が pivot となる。
    private static int findPivotChar(String[] ss, int left, int right, int depth) {
        assert (left + 1 <= right);
        int a = ss[left].length() <= depth ? -1 : ss[left].charAt(depth);
        int b = ss[(left + right) / 2].length() <= depth ? -1 : ss[(left + right) / 2].charAt(depth);
        int c = ss[right - 1].length() <= depth ? -1 : ss[right - 1].charAt(depth);

        // a, b, c の中で真ん中を選ぶ
        if (a <= b) {
            return (b <= c) ? b : Math.max(a, c);
        } else {
            return (c <= b) ? b : Math.min(a, c);
        }
    }

    private static void swap(String[] ss, int a, int b) {
        String t = ss[a];
        ss[a] = ss[b];
        ss[b] = t;
    }
}