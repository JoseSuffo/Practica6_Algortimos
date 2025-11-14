package Ordenamientos;

public class Ordenamiento {
    public Ordenamiento() {
    }

    //Método auxiliar para ordenamiento Quicksort
    public void quicksort(int[] array) {
        if (array == null || array.length == 0) return;
        recursivo(array, 0, array.length - 1);
    }

    //Método de ordenamiento Quicksort
    private void recursivo(int[] array, int izquierda, int derecha) {
        if (izquierda >= derecha || array.length == 0) return;

        int pivote = array[(izquierda + derecha) / 2];
        int i = izquierda, j = derecha;
        while (i <= j) {
            while (array[i] < pivote) i++;
            while (array[j] > pivote) j--;
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        if (izquierda < j) recursivo(array, izquierda, j);
        if (i < derecha) recursivo(array, i, derecha);
    }

    //Método de ordenamiento MergeSort
    public void merge(int[] arr, int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    //Método auxiliar para ordenamiento Mergesort
    public void mergeSort(int[] arr, int l, int r){
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    //Método de ordenamiento ShellSort
    public void shellSort(int[] a){
        int inter=a.length+1;
        while(inter>1){
            inter=inter/2;
            boolean bandera=true;
            while(bandera){
                bandera=false;
                int i=0;
                while((i+inter)<=a.length-1){
                    if(a[i]>a[i+inter]){
                        int aux=a[i];
                        a[i]=a[i+inter];
                        a[i+inter]=aux;
                        bandera=true;
                    }
                    i++;
                }
            }
        }
    }

    //Método de ordenamiento Selección Directa
    public void seleccionDirecta(int[] a){
        for(int i=0; i<a.length-1; i++){
            int menor=a[i];
            int k=i;
            for(int j=i+1; j<a.length; j++){
                if(a[j]<menor){
                    menor=a[j];
                    k=j;
                }
            }
            a[k]=a[i];
            a[i]=menor;
        }
    }

    //Método de ordenamiento CountingSort
    public static void countingSort(int[] array, int posicion) {
        int tamanio = array.length;
        int[] salida = new int[tamanio];
        int[] contador = new int[10];

        for (int i = 0; i < tamanio; i++) {
            int index = (array[i] / posicion) % 10;
            contador[index]++;
        }

        for (int i = 1; i < 10; i++) {
            contador[i] += contador[i - 1];
        }

        for (int i = tamanio - 1; i >= 0; i--) {
            int index = (array[i] / posicion) % 10;
            salida[contador[index] - 1] = array[i];
            contador[index]--;
        }

        for (int i = 0; i < tamanio; i++) {
            array[i] = salida[i];
        }
    }

    //Método de ordenamiento RadixSort
    public void radixSort(int[] array) {
        if (array.length == 0) return;
        int min = array[0];
        for (int val : array) if (val < min) min = val;

        if (min < 0) {
            for (int i = 0; i < array.length; i++) {
                array[i] -= min;
            }
        }

        int elementoMax = obtenerMax(array);
        for (int posicion = 1; elementoMax / posicion > 0; posicion *= 10) {
            countingSort(array, posicion);
        }

        if (min < 0) {
            for (int i = 0; i < array.length; i++) {
                array[i] += min;
            }
        }
    }

    //Método auxiliar para ordenamiento RadixSort
    public int obtenerMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    //Métodos de ordenamientos llamados desde Java
    public void sort(int[] a) {
        java.util.Arrays.sort(a);
    }

    public void parallelSort(int[] a) {
        java.util.Arrays.parallelSort(a);
    }
}