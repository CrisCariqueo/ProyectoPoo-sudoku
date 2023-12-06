package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Sudoku;

public class TableroSudoku extends JPanel {
    private JTextField[][] listaTxt;
    private int txtAncho;
    private int txtAltura;
    private int txtMargen;
    private int txtTamañoLetra;
    private Color panelBackground;
    private Color txtBackground1;
    private Color txtForeground1;
    private Color txtBackground2;
    private Color txtForeground2;
    private Color txtBackground3;
    private Color txtForeground3;
    private Color txtBackground4;
    private Color txtForeground4;

    private Sudoku sudoku;
    private ArrayList<JTextField> listaTxtAux;
    private ArrayList<JTextField> listaTxtGenerados;
    public JTextField txtSelected;

    public TableroSudoku() {
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        listaTxt = new JTextField[9][9];
        txtAncho = 35;
        txtAltura = 36;
        txtMargen = 4;
        txtTamañoLetra = 27;
        panelBackground = Color.BLACK;
        txtBackground1 = Color.WHITE;
        txtForeground1 = Color.BLACK;
        txtBackground2 = Color.WHITE;
        txtForeground2 = Color.BLACK;
        txtBackground3 = Color.WHITE;
        txtForeground3 = Color.BLACK;
        txtBackground4 = Color.RED;
        txtForeground4 = Color.WHITE;
        sudoku = new Sudoku();
        listaTxtAux = new ArrayList<>();
        listaTxtGenerados = new ArrayList<>();
        txtSelected = new JTextField();
    }

    public void crearSudoku() {
        this.setLayout(null);
        this.setSize(txtAncho * 9 + (txtMargen * 4), txtAltura * 9 + (txtMargen * 4));
        this.setBackground(panelBackground);
        crearCamposTxt();
    }

    public void crearCamposTxt() {
        int x = txtMargen;
        int y = txtMargen;

        var ref = new Object(){
            int x = txtMargen;
            int y = txtMargen;
            int i=0;
            int j;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                JTextField txt = new JTextField();

                this.add(txt);
                txt.setBounds(ref.x, ref.y, txtAncho, txtAltura);
                txt.setBackground(txtBackground1);
                txt.setForeground(txtForeground1);
                txt.setFont(new Font("Montserrat", Font.BOLD, txtTamañoLetra));

                txt.setEditable(false);
                txt.setCursor(new Cursor(Cursor.HAND_CURSOR));
                txt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                txt.setVisible(true);

                ref.x += txtAncho;
                if((ref.j+1) % 3 ==0)
                    ref.x += txtMargen;

                listaTxt[ref.i][ref.j] = txt;
                generarEventos(txt);
                ref.j++;
            });
            ref.x = txtMargen;
            ref.y += txtAltura;
            if((ref.i+1) % 3 == 0)
                ref.y += txtMargen;
            ref.i++;
        });
    }

    public boolean txtGenerado(JTextField txt) {
        var ref = new Object() {
            boolean esGenerado = false;
        };
        listaTxtGenerados.forEach(jTxt->{
            if(txt==jTxt) ref.esGenerado = true;
        });
        return ref.esGenerado;
    }

    public void generarEventos(JTextField txt) {
        MouseListener evento = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed(txt);
                txtSelected = txt;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        KeyListener eventoTecla = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (txtGenerado(txt)) {
                    return;
                } else { //permite borrar numeros escritos
                    if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                        txt.setText("");

                    if (e.getKeyChar() >= 49 && e.getKeyChar() <= 57) {
                        txt.setText(String.valueOf(e.getKeyChar()));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        txt.addMouseListener(evento);
        txt.addKeyListener(eventoTecla);
    }

    public void pressed(JTextField txt) {
        listaTxtAux.forEach(jTxt->{
            jTxt.setBackground(txtBackground1);
            jTxt.setForeground(txtForeground1);
            jTxt.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
        });
        listaTxtAux.clear();

        listaTxtGenerados.forEach(jTxt->{
            jTxt.setBackground(txtBackground4);
            jTxt.setForeground(txtForeground4);
        });

        var ref = new Object() {
            int i = 0;
            int j = 0;
        };
        Arrays.stream(listaTxt).forEach(m->{
            ref.j = 0;
            Arrays.stream(m).forEach(n->{
                if(n == txt){
                    Arrays.stream(listaTxt).forEach(jTextFields->{
                        jTextFields[ref.j].setBackground(txtBackground2);
                        listaTxtAux.add(jTextFields[ref.j]);
                    });

                    Arrays.stream(m).forEach(fila->{
                        fila.setBackground(txtBackground2);
                        listaTxtAux.add(fila);
                    });
                    int posI = sudoku.subCuadranteActual(ref.i);
                    int posJ = sudoku.subCuadranteActual(ref.j);


                    var re = new Object(){
                        int[] k = {posI-3, posI-2, posI-1};
                        int[] l = {posJ-3, posJ-2, posJ-1};
                    };
                    Arrays.stream(re.k).forEach(a->{
                        Arrays.stream(re.l).forEach(b->{
                            listaTxt[a][b].setBackground(txtBackground2);
                            listaTxtAux.add(listaTxt[a][b]);
                        });
                    });

                    listaTxt[ref.i][ref.j].setBackground(txtBackground3);
                    listaTxt[ref.i][ref.j].setForeground(txtForeground3);
                    listaTxt[ref.i][ref.j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                    return;
                }
                ref.j++;
            });
            ref.i++;
        });
    }

    public void generarSudoku(int nivel) {
        limpiarTxt();
        sudoku.generarSudoku(nivel);
        int[][] sudokuGenerado = sudoku.getSudoku();
        var ref = new Object(){
            int i=0;
            int j=0;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                if (sudokuGenerado[ref.i][ref.j] != 0) {
                    listaTxt[ref.i][ref.j].setText(String.valueOf(sudokuGenerado[ref.i][ref.j]));
                    listaTxt[ref.i][ref.j].setBackground(txtBackground4);
                    listaTxt[ref.i][ref.j].setForeground(txtForeground4);
                    listaTxtGenerados.add(listaTxt[ref.i][ref.j]);
                }
                ref.j++;
            });
            ref.i++;
        });
    }

    public boolean crearSudokuPersonalizado() {
        sudoku.limpiarSudoku();
        var ref = new Object(){
            int i=0;
            int j=0;
            boolean bool = true;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                if(!(j.getText().isEmpty())){
                    int num = Integer.parseInt(j.getText());
                    if(sudoku.validarColumna(ref.j, num) && sudoku.validarFila(ref.i, num) && sudoku.validarCuadrante(ref.i, ref.j, num)){
                        sudoku.getSudoku()[ref.i][ref.j] = num;
                        j.setBackground(txtBackground4);
                        j.setForeground(txtForeground4);
                        j.setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                        listaTxtGenerados.add(j);
                    } else {
                        listaTxtGenerados.clear();
                        JOptionPane.showMessageDialog(null, "Sudoku Incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                        ref.bool = false;
                        return;
                    }
                } else {
                    listaTxt[ref.i][ref.j].setBackground(txtBackground1);
                    listaTxt[ref.i][ref.j].setForeground(txtForeground1);
                    listaTxt[ref.i][ref.j].setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                }
                ref.j++;
            });
            ref.i++;
        });

        return true;
    }

    public void limpiarTxt() {
        var ref = new Object(){
            int i=0;
            int j=0;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                listaTxt[ref.i][ref.j].setText("");
                listaTxt[ref.i][ref.j].setBackground(txtBackground1);
                listaTxt[ref.i][ref.j].setForeground(txtForeground1);
                listaTxt[ref.i][ref.j].setBorder(BorderFactory.createLineBorder(panelBackground, 1));
                ref.j++;
            });
            ref.i++;
        });

        listaTxtGenerados.clear();
    }

    public void limpiar(){
        var ref = new Object(){
          int i=0;
          int j=0;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                final boolean[] b = {false};
                listaTxtGenerados.forEach(txt->{
                    if(j == txt){
                        b[0] = true;
                        return;
                    }
                });
                if(!b[0])
                    j.setText("");
                ref.j++;
            });
            ref.i++;
        });
    }

    public void comprobar(){
        int[][] sudo = new int[9][9];
        var ref = new Object(){
            int i=0;
            int j=0;
            boolean b;
        };
        ref.b = Arrays.stream(listaTxt).anyMatch(i -> {
                ref.j = 0;
                ref.b = Arrays.stream(i).anyMatch(j -> {
                        if (listaTxt[ref.i][ref.j].getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Sudoku incompleto", "Error", JOptionPane.ERROR_MESSAGE);
                            return true;
                        } else {
                            sudo[ref.i][ref.j] = Integer.parseInt(j.getText());
                        }
                        ref.j++;
                        return false;
                        });
                ref.i++;
                return ref.b;
        });
        if(ref.b) return;

        sudoku.setSudoku(sudo);
        if (sudoku.comprobarSudoku())
            JOptionPane.showMessageDialog(null, "Sudoku correcto", "Sudoku", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "No hay solución", "Error", JOptionPane.ERROR_MESSAGE);
    }
    public void resolver(){
        sudoku.limpiarSudoku();
        var ref = new Object(){
            int i=0;
            int j;
        };
        Arrays.stream(listaTxt).forEach(i->{
            ref.j=0;
            Arrays.stream(i).forEach(j->{
                listaTxtGenerados.forEach(txt->{
                    if(txt ==j)
                        sudoku.getSudoku()[ref.i][ref.j] = Integer.parseInt(txt.getText());
                });
                ref.j++;
            });
            ref.i++;
        });
        
        if(sudoku.resolverSudoku()){
            ref.i=0;
            Arrays.stream(listaTxt).forEach(i->{
                ref.j=0;
                Arrays.stream(i).forEach(j->{
                    j.setText(String.valueOf(sudoku.getSudoku()[ref.i][ref.j]));
                    ref.j++;
                });
                ref.i++;
            });
        } else
            JOptionPane.showMessageDialog(null,"No hay solución","Error",JOptionPane.ERROR_MESSAGE);
    }

    public Color getTxtBackground4() {
        return txtBackground4;
    }
    public void setTxtBackground4(Color txtBackground4) {
        this.txtBackground4 = txtBackground4;
    }
    public Color getTxtForeground4() {
        return txtForeground4;
    }
    public void setTxtForeground4(Color txtForeground4) {
        this.txtForeground4 = txtForeground4;
    }
    public Sudoku getSudoku() {
        return sudoku;
    }
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }
    public ArrayList<JTextField> getListaTxtAux() {
        return listaTxtAux;
    }
    public void setListaTxtAux(ArrayList<JTextField> listaTxtAux) {
        this.listaTxtAux = listaTxtAux;
    }
    public JTextField[][] getListaTxt() {
        return listaTxt;
    }
    public void setListaTxt(JTextField[][] listaTxt) {
        this.listaTxt = listaTxt;
    }
    public int getTxtAncho() {
        return txtAncho;
    }
    public void setTxtAncho(int txtAncho) {
        this.txtAncho = txtAncho;
    }
    public int getTxtAltura() {
        return txtAltura;
    }
    public void setTxtAltura(int txtAltura) {
        this.txtAltura = txtAltura;
    }
    public int getTxtMargen() {
        return txtMargen;
    }
    public void setTxtMargen(int txtMargen) {
        this.txtMargen = txtMargen;
    }
    public int getTxtTamañoLetra() {
        return txtTamañoLetra;
    }
    public void setTxtTamañoLetra(int txtTamañoLetra) {
        this.txtTamañoLetra = txtTamañoLetra;
    }
    public Color getPanelBackground() {
        return panelBackground;
    }
    public void setPanelBackground(Color panelBackground) {
        this.panelBackground = panelBackground;
    }
    public Color getTxtBackground1() {
        return txtBackground1;
    }
    public void setTxtBackground1(Color txtBackground1) {
        this.txtBackground1 = txtBackground1;
    }
    public Color getTxtForeground1() {
        return txtForeground1;
    }
    public void setTxtForeground1(Color txtForeground1) {
        this.txtForeground1 = txtForeground1;
    }
    public Color getTxtBackground2() {
        return txtBackground2;
    }
    public void setTxtBackground2(Color txtBackground2) {
        this.txtBackground2 = txtBackground2;
    }
    public Color getTxtForeground2() {
        return txtForeground2;
    }
    public void setTxtForeground2(Color txtForeground2) {
        this.txtForeground2 = txtForeground2;
    }
    public Color getTxtBackground3() {
        return txtBackground3;
    }
    public void setTxtBackground3(Color txtBackground3) {
        this.txtBackground3 = txtBackground3;
    }
    public Color getTxtForeground3() {
        return txtForeground3;
    }
    public void setTxtForeground3(Color txtForeground3) {
        this.txtForeground3 = txtForeground3;
    }
}