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
public class GUIbueno extends javax.swing.JFrame {

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

    List<Nodo> recorrido;

    int x;
    int y;

    public GUIbueno() {
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

//    private void pintarRecorrido(List<Nodo> recorrido) {
//        for (int i = 0; i < paneles.length; i++) {
//            for (int j = 0; j < paneles[0].length; j++) {
//                if(i == recorrido.get(0).getX() && j == recorrido.get(0).getY()){
//                    ImageIcon icono = new ImageIcon(camino);
//                    JLabel label = new JLabel(icono);
//                    paneles[i][j].add(label);
//                    paneles[i][j].repaint();
//                    pnlTablero.repaint();
//                    recorrido.remove(0);
//                }
//            }
//        }
//        RedibujarTablero();
//    }
    private void pintarRecorrido(List<Nodo> recorrido) {
        ImageIcon icono = new ImageIcon(camino);
        for (Nodo nodo : recorrido) {
            int x = nodo.getX();
            int y = nodo.getY();

            JLabel label = new JLabel(icono);
            label.setBounds(x, y, icono.getIconWidth(), icono.getIconHeight());
            paneles[x][y].add(label);
        }
        paneles[x][y].revalidate();
        paneles[x][y].repaint();
        RedibujarTablero();
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
                    RedibujarTablero();
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
        jPanel1 = new javax.swing.JPanel();
        txtEuristica = new javax.swing.JTextField();
        btnCalcularEuristica = new javax.swing.JButton();
        selectorEuristica = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnBeamSearch = new javax.swing.JButton();
        btnHillClimbing = new javax.swing.JButton();
        btnAEstrella = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnCostoUniforme = new javax.swing.JButton();
        btnProfundidad = new javax.swing.JButton();
        btnAnchura = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnFinal = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGap(0, 516, Short.MAX_VALUE)
        );
        pnlTableroLayout.setVerticalGroup(
            pnlTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        txtEuristica.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtEuristica.setPreferredSize(new java.awt.Dimension(111, 25));

        btnCalcularEuristica.setPreferredSize(new java.awt.Dimension(111, 25));
        btnCalcularEuristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularEuristicaActionPerformed(evt);
            }
        });

        selectorEuristica.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        selectorEuristica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Manhattan", "Euclidiana" }));
        selectorEuristica.setPreferredSize(new java.awt.Dimension(111, 25));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Heuristicas");

        btnBeamSearch.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnBeamSearch.setText("Beam Search");
        btnBeamSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeamSearchActionPerformed(evt);
            }
        });

        btnHillClimbing.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnHillClimbing.setText("Hill Climbing");
        btnHillClimbing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHillClimbingActionPerformed(evt);
            }
        });

        btnAEstrella.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAEstrella.setText("Recorrido A*");
        btnAEstrella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAEstrellaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Búsquedas NO informadas");

        btnCostoUniforme.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnCostoUniforme.setText("Costo uniforme");
        btnCostoUniforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostoUniformeActionPerformed(evt);
            }
        });

        btnProfundidad.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnProfundidad.setText("Profundidad");
        btnProfundidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfundidadActionPerformed(evt);
            }
        });

        btnAnchura.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btnAnchura.setText("Anchura");
        btnAnchura.setPreferredSize(new java.awt.Dimension(103, 25));
        btnAnchura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnchuraActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Búsquedas informadas");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtEuristica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(29, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBeamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(selectorEuristica, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCalcularEuristica, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnHillClimbing, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAEstrella, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(btnAnchura, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProfundidad, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCostoUniforme))
                .addGap(27, 27, 27))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInicio)
                            .addComponent(btnFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInicio)
                .addGap(18, 18, 18)
                .addComponent(btnFinal)
                .addGap(51, 51, 51)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCalcularEuristica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectorEuristica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtEuristica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        pnlTablero.removeAll();
        btnInicio.setEnabled(true);
        btnFinal.setEnabled(true);
        generarMapa(main.getMatriz());
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        setIconoActual(camino);
        btnInicio.setEnabled(false);
        botonPresionado = btnInicio;
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnCostoUniformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostoUniformeActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaNOInformada BNI = new BusquedaNOInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BNI.recorridoCostoUniforme(nodoInicial, nodoObjetivo, "E");
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnCostoUniformeActionPerformed

    private void btnAEstrellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAEstrellaActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaInformada BI = new BusquedaInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BI.recorridoAEstrella(nodoInicial, nodoObjetivo, "E");
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnAEstrellaActionPerformed

    private void btnHillClimbingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHillClimbingActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaInformada BI = new BusquedaInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BI.recorridoHillClimbing(nodoInicial, nodoObjetivo, "E");
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnHillClimbingActionPerformed

    private void btnBeamSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeamSearchActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaInformada BI = new BusquedaInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BI.recorridoBeamSearch(nodoInicial, nodoObjetivo, "E");
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnBeamSearchActionPerformed

    private void btnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalActionPerformed
        setIconoActual(meta);
        btnFinal.setEnabled(false);
        botonPresionado = btnFinal;
    }//GEN-LAST:event_btnFinalActionPerformed

    private void btnAnchuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnchuraActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaNOInformada BNI = new BusquedaNOInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BNI.recorridoEnAnchura(nodoInicial, nodoObjetivo);
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnAnchuraActionPerformed

    private void btnCalcularEuristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularEuristicaActionPerformed
        double heuristica;
        if (selectorEuristica.getSelectedItem() == "Manhattan") {
            heuristica = nodoInicial.calcularHeuristic(nodoInicial, nodoObjetivo, "M");
            txtEuristica.setText(String.format("%.3f", heuristica));
        }

        if (selectorEuristica.getSelectedItem() == "Euclidiana") {
            heuristica = nodoInicial.calcularHeuristic(nodoInicial, nodoObjetivo, "E");
            txtEuristica.setText(String.format("%.3f", heuristica));
        }
    }//GEN-LAST:event_btnCalcularEuristicaActionPerformed

    private void btnProfundidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfundidadActionPerformed
        Matriz laberinto = new Matriz(main.getMatriz());
        String tipoHeuristica = "E";
        BusquedaNOInformada BNI = new BusquedaNOInformada(laberinto.getNodos(nodoObjetivo, tipoHeuristica), main.getMatriz());
        recorrido = BNI.recorridoEnProfundidad(nodoInicial, nodoObjetivo);
        //recorrido.remove(0);
        //recorrido.remove(recorrido.size()-1);
        pintarRecorrido(recorrido);
    }//GEN-LAST:event_btnProfundidadActionPerformed

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
    private javax.swing.JButton btnCalcularEuristica;
    private javax.swing.JButton btnCostoUniforme;
    private javax.swing.JButton btnFinal;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnHillClimbing;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnProfundidad;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlTablero;
    private javax.swing.JComboBox<String> selectorEuristica;
    private javax.swing.JTextField txtEuristica;
    // End of variables declaration//GEN-END:variables
}
