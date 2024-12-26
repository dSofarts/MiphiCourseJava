package BinarySearch;

import java.util.Comparator;
import java.util.List;

public class MyCollections {

  public static <T> int binarySearch(List<? extends T> list, T key) {
    return binarySearch(list, key, null);
  }

  public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      T midVal = list.get(mid);
      int cmp = (c == null) ? ((Comparable<? super T>) midVal).compareTo(key) : c.compare(midVal, key);
      if (cmp < 0) {
        low = mid + 1;
      } else if (cmp > 0) {
        high = mid - 1;
      } else {
        return mid;
      }
    }
    return -(low + 1);
  }
}
