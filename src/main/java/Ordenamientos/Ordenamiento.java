package Ordenamientos;

public class Ordenamiento {
    public Ordenamiento() {

    }

    public void quicksort(int[] a){
        recursivo(a, 0, a.length-1);
    }

    public void recursivo(int[]a, int ini, int fin){
        int izq=ini;
        int der=fin;
        int pos=ini;
        boolean band=true;
        while(band){
            band=false;
            while(a[pos]<=a[der] && pos!=der){
                der--;
            }
            if(pos!=der){
                int aux=a[pos];
                a[pos]=a[der];
                a[der]=aux;
                pos=der;
            }
            while(a[pos]>=a[izq] && pos!=izq){
                izq++;
            }
            if(pos!=izq){
                int aux=a[pos];
                a[pos]=a[izq];
                a[izq]=aux;
                pos=izq;
                band=true;
            }
        }
        if(pos-1>ini){
            recursivo(a,ini,pos-1);
        }
        if(pos+1<fin){
            recursivo(a,pos+1,fin);
        }
    }

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

    public void mergeSort(int[] arr, int l, int r){
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

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

    public void radixSort(int[] array) {
        int elementoMax = obtenerMax(array);
        for (int posicion = 1; elementoMax / posicion > 0; posicion *= 10) {
            countingSort(array, posicion);
        }
    }

    public int obtenerMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}
