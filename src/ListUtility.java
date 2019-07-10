import java.util.List;
import java.util.Collections;
import java.util.ListIterator;

public class ListUtility {
	
	static void sort(List list) {
		Object[] a = list.toArray();
		Object[] aux = a.clone();
		mergeSort(aux, a, 0, a.length);
        ListIterator i = list.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set(a[j]);
        }
		Collections.reverse(list);
	}
	
	private static void mergeSort(Object[] src, Object[] dest, int low, int high) {
		int length = high - low;

        if (length < 7) {
            for (int i=low; i<high; i++) {
                for (int j=i; j>low && ((Comparable)dest[j-1]).compareTo(dest[j])>0; j--) {
                	Object t = dest[j];
                    dest[j] = dest[j-1];
                    dest[j-1] = t;
                }
            }
            return;
        }

        int mid = (low + high) / 2;
        mergeSort(dest, src, low, mid);
        mergeSort(dest, src, mid, high);

        if (((Comparable)src[mid-1]).compareTo(src[mid]) <= 0) {
            System.arraycopy(src, low, dest, low, length);
            return;
        }

        for(int i = low, p = low, q = mid; i < high; i++) {
            if (q >= high || p < mid && ((Comparable)src[p]).compareTo(src[q])<=0) {
                dest[i] = src[p++];
            } else {
                dest[i] = src[q++];
            }
        }
	}
}