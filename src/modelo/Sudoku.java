package modelo;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.JTextField;

public class Sudoku {
    private int[][] sudoku;

    public Sudoku() {
        sudoku = new int[9][9];
        limpiarSudoku();
    }

    public boolean resolverSudoku() {
        /*
        var ref = new Object(){
            int i=0;
            int j;
            boolean b = true;
        };

        Arrays.stream(sudoku).anyMatch(i->{
            ref.j=0;
            boolean c=Arrays.stream(i).anyMatch(j->{
                if(j == 0){
                    int[] valor = {1,2,3,4,5,6,7,8,9};
                    ref.b = Arrays.stream(valor).anyMatch(val->{
                        if(validarFila(ref.i, val) && validarColumna(ref.j, val) && validarCuadrante(ref.i, ref.j, val)){
                            sudoku[ref.i][ref.j] = val;
                            if(resolverSudoku()) {
                                return true;
                            }
                        }
                        return false;
                    });
                    return true;
                }
                ref.j++;
                return false;
            });
            if(c) return false;
            ref.i++;
            return true;
        });
        return ref.b;*/


        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                if (sudoku[i][j] == 0) {
                    for (int valor = 1; valor <= 9; valor++) {
                        if (validarFila(i, valor) && validarColumna(j, valor) && validarCuadrante(i, j, valor)) {
                            sudoku[i][j] = valor;
                            if (resolverSudoku()) {
                                return true;
                            }
                            sudoku[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean comprobarSudoku(){
        /*
        var ref = new Object(){
            int i=0;
            int j=0;
            int aux;
        };
        return Arrays.stream(sudoku).anyMatch(i->{
            ref.j=0;
            boolean c = Arrays.stream(i).anyMatch(j->{
                int aux = sudoku[ref.i][ref.j];
                sudoku[ref.i][ref.j] = 0;
                if (!validarFila(ref.i, aux) || !validarColumna(ref.j, aux) || !validarCuadrante(ref.i, ref.j, aux)) {
                    sudoku[ref.i][ref.j] = aux;
                    return false;
                }
                sudoku[ref.i][ref.j] = aux;
                ref.j++;
                return true;
            });
            if(!c) return false;
            ref.i++;
            return true;
        });*/



        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = sudoku[i][j];
                sudoku[i][j] = 0;
                if (!validarFila(i, aux) || !validarColumna(j, aux) || !validarCuadrante(i, j, aux)) {
                    sudoku[i][j]=aux;
                    return false;
                }
                sudoku[i][j]=aux;
            }
        }
        return true;
    }

    public boolean validarCuadrante(int i, int j, int valor) {
        int posI = subCuadranteActual(i);
        int posJ = subCuadranteActual(j);

        /*
        int[] xArr = {posI-3, posI-2, posI-1};
        int[] yArr = {posJ-3, posJ-2, posJ-1};
        var ref = new Object(){
            int k =xArr[0];
            int l =yArr[0];
        };
        return Arrays.stream(xArr).anyMatch(k->{
            boolean b =Arrays.stream(yArr).anyMatch(l->{
                if (sudoku[ref.k][ref.l] == valor) {
                    return false;
                }
                ref.l++;
                return true;
            });
            ref.k++;
            return !b;
        });*/


        for (int k = posI - 3; k < posI; k++) {
            for (int l = posJ - 3; l < posJ; l++) {
                if (sudoku[k][l] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    public void limpiarSudoku() {
        Arrays.stream(sudoku).forEach(i->{
            Arrays.stream(i).forEach(j->{
                j = 0;
            });
        });
    }

    public int subCuadranteActual(int pos) {
        if (pos <= 2) {
            return 3;
        } else if (pos <= 5) {
            return 6;
        } else {
            return 9;
        }
    }

    public boolean validarFila(int i, int valor) {
        /*
        return IntStream.range(0, sudoku[i].length)
                .noneMatch(j -> sudoku[i][j] == valor);
         */


        for (int j = 0; j < sudoku[i].length; j++) {
            if (sudoku[i][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarColumna(int j, int valor) {
        /*
        var ref = new Object(){
            boolean b = true;
        };
        Arrays.stream(sudoku).forEach(i->{
            if(i[j] == valor)
                ref.b = false;
        });
        return ref.b;
        */

        for (int[] ints : sudoku) {
            if (ints[j] == valor)
                return false;
        }
        return true;
    }

    public void generarSudoku(int nivel) {
        limpiarSudoku();
        Random random = new Random();

        /*
        int[] i1 = {0,1,2};
        int[] j1 = {0,1,2};
        int[] k1 = {0};
        Arrays.stream(j1).forEach(i->{
            k1[0] = 0;
            Arrays.stream(j1).forEach(j->{
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, k1[0], num)) {
                    sudoku[i][k1[0]] = num;
                } else {
                    k1[0]--;
                }
                k1[0]++;
            });
        });*/

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        /*
        int[] i2 = {3,4,5};
        int[] j2 = {3,4,5};
        int[] k2 = {0};
        Arrays.stream(j2).forEach(i->{
            k2[0] = 0;
            Arrays.stream(j2).forEach(j->{
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, k2[0], num)) {
                    sudoku[i][k2[0]] = num;
                } else {
                    k2[0]--;
                }
                k2[0]++;
            });
        });*/

        for (int i=3; i<6; i++){
            for (int j=3; j<6; j++){
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }

        /*
        int[] i3 = {6,7,8};
        int[] j3 = {6,7,8};
        int[] k3 = {0};
        Arrays.stream(j3).forEach(i->{
            k3[0] = 0;
            Arrays.stream(j3).forEach(j->{
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, k3[0], num)) {
                    sudoku[i][k3[0]] = num;
                } else {
                    k3[0]--;
                }
                k3[0]++;
            });
        });*/

        for (int i=6; i<9; i++) {
            for (int j=6; j<9; j++) {
                int num = random.nextInt(9) + 1;
                if (validarCuadrante(i, j, num)) {
                    sudoku[i][j] = num;
                } else {
                    j--;
                }
            }
        }
        resolverSudoku();
/*
        var ref = new Object(){
            int i = 0;
            int j = 0;
        };
        Arrays.stream(sudoku).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                int aux = ref.j;
                int rand = random.nextInt(nivel + 1);
                ref.j += rand;


                for (int k = aux; k < j && k < sudoku.length; k++) {
                    sudoku[ref.i][k] = 0;
                }
                ref.j++;
            });
            ref.i++;
        });*/

        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                int aux = j;
                int rand = random.nextInt(nivel + 1);
                j += rand;
                for (int k = aux; k < j && k < sudoku.length; k++) {
                    sudoku[i][k] = 0;
                }
            }
        }
    }

    public int[][] getSudoku() {
        return sudoku;
    }
    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }
}