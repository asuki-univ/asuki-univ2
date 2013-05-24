package suffixarray;


public class TernaryQuickSort {
    public static void sort(SuffixArrayItem[] items) {
        sort(items, 0, items.length, 0);
    }

    private static void sort(SuffixArrayItem[] items, int left, int right, int depth) {
        int size = right - left;
        if (size <= 1)
            return;

        // pivot を１つ選ぶ。もし、pivot が見つからなければ、depth より長い文字列がないということなので、全部同じ文字列なので、ソート済みとなる。
        int pivot = findPivotChar(items, left, right, depth);

        int leftPivotPos = left, rightPivotPos = right - 1;
        int i = left, j = right - 1;

        // 区間 (left, right) を４分割する
        while (true) {
            while (i <= j) {
                int k = items[i].suffix.length() <= depth ? -1 : items[i].suffix.charAt(depth);
                if (k > pivot)
                    break;
                else if (k == pivot)
                    swap(items, i, leftPivotPos++);
                ++i;
            }
            while (i <= j) {
                int k = items[j].suffix.length() <= depth ? -1 : items[j].suffix.charAt(depth);
                if (k < pivot)
                    break;
                else if (k == pivot)
                    swap(items, j, rightPivotPos--);
                --j;
            }
            if (i > j)
                break;
            swap(items, i++, j--);
        }

        // pivot と等しいものを左に
        int leftSwapSize = Math.min(leftPivotPos - left, i - leftPivotPos);
        for (int k = 0; k < leftSwapSize; ++k)
            swap(items, left + k, j - k);
        leftPivotPos = left + (i - leftPivotPos);

        int rightSwapSize = Math.min(right - rightPivotPos - 1, rightPivotPos - j);
        for (int k = 0; k < rightSwapSize; ++k)
            swap(items, right - k - 1, i + k);
        rightPivotPos = right - (rightPivotPos - j);

        sort(items, left, leftPivotPos, depth);
        if (rightPivotPos - leftPivotPos != right - left || items[left].suffix.length() >= depth)
            sort(items, leftPivotPos, rightPivotPos, depth + 1);
        sort(items, rightPivotPos, right, depth);
    }

    // pivot となる文字を返す。もし長さが足りなければ、-1 が pivot となる。
    private static int findPivotChar(SuffixArrayItem[] items, int left, int right, int depth) {
        assert (left + 1 <= right);
        int a = items[left].suffix.length() <= depth ? -1 : items[left].suffix.charAt(depth);
        int b = items[(left + right) / 2].suffix.length() <= depth ? -1 : items[(left + right) / 2].suffix.charAt(depth);
        int c = items[right - 1].suffix.length() <= depth ? -1 : items[right - 1].suffix.charAt(depth);

        // a, b, c の中で真ん中を選ぶ
        if (a <= b) {
            return (b <= c) ? b : Math.max(a, c);
        } else {
            return (c <= b) ? b : Math.min(a, c);
        }
    }

    private static void swap(SuffixArrayItem[] ss, int a, int b) {
        SuffixArrayItem t = ss[a];
        ss[a] = ss[b];
        ss[b] = t;
    }
}