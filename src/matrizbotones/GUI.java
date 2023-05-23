package matrizbotones;

import gestorArchivos.Archivo;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 *
 * Proyecto 1. Sistemas Inteligentes
 */
public class GUI extends javax.swing.JFrame {

    Main main = new Main();

    int TamX = 0;
    int TamY = 0;
    private JPanel[][] paneles;

    String camino = obtenerRutaIcono("camino");
    String roca = obtenerRutaIcono("roca");
    String muro = obtenerRutaIcono("muro");
    String meta = obtenerRutaIcono("meta");

    String iconoActual;

    JButton botonPresionado;
    JButton botonHeuristica;

    Nodo nodoInicial;
    Nodo nodoObjetivo;

    int x;
    int y;

    public GUI() {
        initComponents();
    }

    /**
     * Método para btener la ruta en la que se encuentra uno de los iconos a
     * mostrar en el tablero
     *
     * @param nombreImagen nombre de la imagen a buscar
     * @return la ruta de la imagen
     */
    private String obtenerRutaIcono(String nombreImagen) {
        ClassLoader classLoader = Main.class.getClassLoader();
        URL urlImagen = classLoader.getResource("imagenes/" + nombreImagen + ".png");

        if (urlImagen == null) {
            System.out.println("No se encontró la imagen.");
        }
        String rutaImagen = urlImagen.getPath();
        return rutaImagen;
    }

    /**
     * Método para pintar inicialmente la matriz leida
     *
     * @param matrizDatos la matriz a mostrar
     */
    public void generarMapa(String[][] matrizDatos) {
        pnlTablero.setLayout(new GridLayout(matrizDatos.length, matrizDatos[0].length));
        //System.out.println(pnlTablero.getWidth() + "," + pnlTablero.getHeight());

        paneles = new JPanel[matrizDatos.length][matrizDatos[0].length];

        for (int i = 0; i < matrizDatos.length; i++) {
            for (int j = 0; j < matrizDatos[0].length; j++) {
                paneles[i][j] = new JPanel();
                ObtenerTamanioObjetos(pnlTablero.getWidth(), pnlTablero.getHeight());
                paneles[i][j].setSize(TamX, TamY);
                paneles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                paneles[i][j].setBackground(Color.CYAN);

                if ("M".equals(matrizDatos[i][j])) {
                    ImageIcon icono = new ImageIcon(muro);
                    JLabel label = new JLabel(icono);
                    paneles[i][j].setBackground(Color.CYAN);
                    paneles[i][j].add(label);
                    pnlTablero.add(paneles[i][j]);
                } else if ("R".equals(matrizDatos[i][j])) {
                    ImageIcon icono = new ImageIcon(roca);
                    JLabel label = new JLabel(icono);
                    paneles[i][j].setBackground(Color.CYAN);
                    paneles[i][j].add(label);
                    pnlTablero.add(paneles[i][j]);
                } else {
                    pnlTablero.add(paneles[i][j]);
                }
                paneles[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JPanel panel = (JPanel) e.getSource();
                        x = -1;
                        y = -1;
                        calcularCoordenada(panel);
                        RedibujarTablero();
                        //System.out.println("Coordenadas del panel seleccionado: (" + x + ", " + y + ")");
                        //System.out.println("icono actual " + getIconoActual());
                        if (botonPresionado == btnInicio) {
                            nodoInicial = new Nodo(x, y);
                            iconoActual = null;
                            System.out.println("nodo inicio: (" + nodoInicial.getX() + "," + nodoInicial.getY() + ")");
                        }
                        if (botonPresionado == btnFinal) {
                            nodoObjetivo = new Nodo(x, y);
                            iconoActual = null;
                            System.out.println("nodo objetivo: (" + nodoObjetivo.getX() + "," + nodoObjetivo.getY() + ")");
                        }

                    }

                });

            }
        }
        getContentPane().add(pnlTablero);
        setVisible(true);
    }

    private void pintarRecorrido(List<Nodo> recorrido) {
        for (int i = 0; i < paneles.length; i++) {
            for (int j = 0; j < paneles[0].length; j++) {

            }
        }
    }

    private void calcularCoordenada(JPanel panel) {
        for (int i = 0; i < paneles.length; i++) {
            for (int j = 0; j < paneles[0].length; j++) {
                if (paneles[i][j] == panel) {
                    x = i;
                    y = j;
                    ImageIcon icono = new ImageIcon(getIconoActual());
                    JLabel label = new JLabel(icono);
                    paneles[i][j].add(label);
                    paneles[i][j].repaint();
                    pnlTablero.repaint();
                    repaint();
                    revalidate();
                    break;
                }
            }
            if (x != -1) {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMenu = new javax.swing.JPanel();
        btnReiniciar = new javax.swing.JButton();
        btnGenerar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        pnlTablero = new javax.swing.JPanel();
        btnAnchura = new javax.swing.JButton();
        btnProfundidad = new javax.swing.JButton();
        btnCostoUniforme = new javax.swing.JButton();
        btnAEstrella = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnHillClimbing = new javax.swing.JButton();
        btnBeamSearch = new javax.swing.JButton();
        btnFinal = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();
        btnManhattan = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnEuclidiana = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Matríz Dinamica");
        setName("frmTablero"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(500, 500));

        btnReiniciar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnReiniciar.setText("Reiniciar");
        btnReiniciar.setToolTipText("");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        btnGenerar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(btnGenerar)
                .addGap(39, 39, 39)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnReiniciar)
                .addContainerGap(142, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar)
                    .addComponent(btnReiniciar)))
        );

        pnlTablero.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlTablero.setToolTipText("");

        javax.swing.GroupLayout pnlTableroLayout = new javax.swing.GroupLayout(pnlTablero);
        pnlTablero.setLayout(pnlTableroLayout);
        pnlTableroLayout.setHorizontalGroup(
            pnlTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
        );
        pnlTableroLayout.setVerticalGroup(
            pnlTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        btnAnchura.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAnchura.setText("Anchura");
        btnAnchura.setPreferredSize(new java.awt.Dimension(103, 25));
        btnAnchura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnchuraActionPerformed(evt);
            }
        });

        btnProfundidad.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnProfundidad.setText("Profundidad");

        btnCostoUniforme.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnCostoUniforme.setText("Costo uniforme");
        btnCostoUniforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostoUniformeActionPerformed(evt);
            }
        });

        btnAEstrella.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAEstrella.setText("Recorrido A*");
        btnAEstrella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAEstrellaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Búsquedas informadas");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Búsquedas NO informadas");

        btnHillClimbing.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnHillClimbing.setText("Hill Climbing");
        btnHillClimbing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHillClimbingActionPerformed(evt);
            }
        });

        btnBeamSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnBeamSearch.setText("Beam Search");
        btnBeamSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeamSearchActionPerformed(evt);
            }
        });

        btnFinal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnFinal.setText("Marcar final");
        btnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalActionPerformed(evt);
            }
        });

        btnInicio.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnInicio.setText("Marcar Inicio");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        btnManhattan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnManhattan.setText("Manhattan");
        btnManhattan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManhattanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Heuristicas");

        btnEuclidiana.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnEuclidiana.setText("Euclidiana");
        btnEuclidiana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEuclidianaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAEstrella, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnHillClimbing, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBeamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManhattan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(btnEuclidiana, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnAnchura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProfundidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCostoUniforme, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnInicio)
                        .addGap(18, 18, 18)
                        .addComponent(btnFinal)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnchura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProfundidad)
                        .addGap(18, 18, 18)
                        .addComponent(btnCostoUniforme)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAEstrella)
                        .addGap(18, 18, 18)
                        .addComponent(btnHillClimbing)
                        .addGap(18, 18, 18)
                        .addComponent(btnBeamSearch)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnManhattan)
                        .addGap(18, 18, 18)
                        .addComponent(btnEuclidiana)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        //Main main = new Main();
        main.crearMatriz();
        String matriz[][] = main.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("");
        }
        generarMapa(matriz);
    }//GEN-LAST:event_btnGenerarActionPerformed

    /**
     * Metodo que redibuja el elemto pnlTablero
     */
    private void RedibujarTablero() {
        pnlTablero.validate();
        pnlTablero.repaint();
    }

    public void setIconoActual(String iconoActual) {
        this.iconoActual = iconoActual;
    }

    public String getIconoActual() {
        return iconoActual;
    }


    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed

    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        setIconoActual(camino);
        btnInicio.setEnabled(false);
        botonPresionado = btnInicio;
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnCostoUniformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostoUniformeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCostoUniformeActionPerformed

    private void btnAEstrellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAEstrellaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAEstrellaActionPerformed

    private void btnHillClimbingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHillClimbingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHillClimbingActionPerformed

    private void btnBeamSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeamSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBeamSearchActionPerformed

    private void btnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalActionPerformed
        setIconoActual(meta);
        btnFinal.setEnabled(false);
        botonPresionado = btnFinal;
    }//GEN-LAST:event_btnFinalActionPerformed

    private void btnAnchuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnchuraActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "";
        if (botonHeuristica == btnManhattan) {
            tipoHeuristica = "E";
        }
        if (botonHeuristica == btnEuclidiana) {
            tipoHeuristica = "E";
        }
        BusquedaNOInformada BNI = new BusquedaNOInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        List<Nodo> recorridoAnchura = BNI.recorridoEnAnchura(nodoInicial, nodoObjetivo);
    }//GEN-LAST:event_btnAnchuraActionPerformed

    private void btnManhattanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManhattanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnManhattanActionPerformed

    private void btnEuclidianaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEuclidianaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEuclidianaActionPerformed

    /**
     * Metodo que calcula el tamaño de ancho y alto de los botones acorde a la
     * cantidad de elementos en la matriz
     */
    private void ObtenerTamanioObjetos(int cantX, int cantY) {
        TamX = pnlTablero.getWidth() / cantX;
        TamY = pnlTablero.getHeight() / cantY;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAEstrella;
    private javax.swing.JButton btnAnchura;
    private javax.swing.JButton btnBeamSearch;
    private javax.swing.JButton btnCostoUniforme;
    private javax.swing.JButton btnEuclidiana;
    private javax.swing.JButton btnFinal;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnHillClimbing;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnManhattan;
    private javax.swing.JButton btnProfundidad;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlTablero;
    // End of variables declaration//GEN-END:variables
}
