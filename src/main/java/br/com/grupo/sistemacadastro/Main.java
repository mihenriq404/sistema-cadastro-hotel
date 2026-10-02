package br.com.grupo.sistemacadastro;

import br.com.grupo.sistemacadastro.gui.HospedagemFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Inicia a interface gráfica do sistema.
        SwingUtilities.invokeLater(() -> {

            HospedagemFrame frame =
                    new HospedagemFrame();

            frame.setVisible(true);
        });
    }
}