package sample;
public class transfer {
int d;
int[] gx;
int k;
int[] m;
public  transfer(int D, int[] GX, int K, int[] M ){
    this.d = D;
    this.k = K;
    gx = new int[d+k];
    m = new int[k+d];
    //увеличиваем количество элементов массивов сразу до разсера d+k чтобы потом было удобнее работать,
    // старшие (добавленые) биты заполняются 0
    for (int i = 0; i < d+k; i++){
        if (i < d+1) {
            gx[i] = GX[i];
        }
        else{
            gx[i] = 0;
        }
        if (i < k){
            m[i] = M[i];
        }
        else{
            m[i] = 0;
        }
    }
    //System.out.println("gx  ");
   // for (int  i = k+d-1; i >= 0; i--){
   //     System.out.print(gx[i] + "  ");
//    }
 //   System.out.println();
//    System.out.println("m  ");
 //   for (int  i = k+d-1; i >= 0; i--){
  //      System.out.print(m[i] + "  ");
 //   }
 //   System.out.println();
}
int[] Coder(){
   int[] res = new int[k+d];
    int[] cx = new int[k+d];
    for (int i = 0; i <k+d; i++){
        cx[i] = m[i];
        res[i] = m[i];
    }
    CX(cx, d);
  //  System.out.println("CX ");
  //  for (int  i = k+d-1; i >= 0; i--){
  //      System.out.print(cx[i] + "  ");
  //  }
  //  System.out.println();
    AX(res, d, cx);
  //  System.out.println("AX  ");
  //  for (int  i = k+d-1; i >= 0; i--){
  //      System.out.print(res[i] + "  ");
 //   }
  //  System.out.println();
    return res;
}
int[] Decoder (int[] error, int[] m){
    int[] res = new int[k+d];
    for(int i = 0; i < m.length; i++) {
    res[i] = m[i];
    }
    xor(res, error); //складываем по модулю 2 с вектором ошибок
    return res; //декодированое сообщение
}
//метод для определения была ли ошибка
int Error(int[] m){
    int E;
    int count = 0;
    //берем по модулю многочлена и если разультат будет 0 векор то ошибки нет
    mod(m, gx);
  //  System.out.println("m  ");
   // for (int  i = k+d-1; i >= 0; i--){
   //     System.out.print(m[i] + "  ");
   // }
   // System.out.println();
    for (int i = 0; i < m.length; i++){
        if (m[i]==0){
            count++;
        }
    }
    if (count == m.length){
        E = 0;
    }
    else{
        E = 1;
    }
    return E;
}

//для Coder
void AX(int[] ax, int d, int[] cx){
    //домножаем массив на x^r (r это то же самое что d)
    //умножение происходит методо переноса элементов массива на позицию i+d, d-степень усножения
    for (int i = 0; i < d; i++){
        if (i+d < cx.length) {
            ax[i + d] = ax[i];
        }
        ax[i] = 0;
    }
    //и потом прибовляем cx
    xor(ax, cx);
}
void CX(int[] cx, int d){
    for (int i = 0; i < d; i++){
            if (i+d < cx.length) {
                cx[i + d] = cx[i];
            }
            cx[i] = 0;
    }
    mod(cx, gx);
}
//алгоритм взятия по модулю, мы ищим максимальную степерь(те позицию где значение 1) у нашего многочлена и у модуля,
// потом из степени многочлена вычичтаем степень модуля и если разность будет больше или равно нулю,
// то значение модуля домножаем на разность и складываем по модулю 2, выполняем это действие
// пока разность не станет меньше чем 0
void mod (int[]one, int[] two){
    int[] res = new int[one.length];
    int maxOne = 0;
    int maxTwo = 0;
    int c;
    for (int i = 0; i < two.length; i++){
        if (two[i] == 1){
            maxTwo = i;
        }
    }
    for (int p = 0; p <one.length; p++){
        for (int i = 0; i < one.length; i++){
            if (one[i] == 1){
                maxOne = i;
            }
        }
        c = maxOne - maxTwo;
        if (c >= 0){
            for (int i = 0; i < one.length-c;i++){
                if(i<c){
                    res[i] = 0;
                }
                res[i+c] = two[i];
            }
            xor(one, res);
            maxOne = 0;
        }
        else{
            break;
        }
    }
}
//сложение по модулю 2, проходимся по значениям массива и если они равны то записываем 0, иначе 1
void xor(int[] one, int[] two){
    for (int i = 0; i < one.length; i++){
        if (one[i]==two[i]){
            one[i] = 0;
        }
        else{
            one[i] = 1;
        }
    }
}
}
