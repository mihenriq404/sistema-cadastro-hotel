package br.com.grupo.sistemacadastro.gui;

import br.com.grupo.sistemacadastro.model.Hospedagem;
import br.com.grupo.sistemacadastro.repository.HospedagemRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HospedagemFrame extends JFrame {

    private JTextField campoNomeHospede;
    private JTextField campoNumeroQuarto;
    private JTextField campoDataEntrada;
    private JTextField campoDataSaida;

    private JTable tabelaHospedagens;
    private DefaultTableModel modeloTabela;

    private final HospedagemRepository repository =
            new HospedagemRepository();

    private static final int ESPACAMENTO = 10;

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Cria e configura a janela do sistema.
    public HospedagemFrame() {

        setTitle("Sistema de Cadastro de Hospedagens");

        setSize(800, 550);

        setMinimumSize(new Dimension(650, 450));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        criarInterface();
        carregarHospedagens();
    }

    // Cria todos os componentes visuais da tela.
    private void criarInterface() {

        JPanel painelPrincipal =
                new JPanel(new BorderLayout(
                        ESPACAMENTO,
                        ESPACAMENTO
                ));

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        ESPACAMENTO,
                        ESPACAMENTO,
                        ESPACAMENTO,
                        ESPACAMENTO
                )
        );

        // Cria o formulário com os dados da hospedagem.
        JPanel painelFormulario =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                ESPACAMENTO,
                                ESPACAMENTO
                        )
                );

        JLabel labelNomeHospede =
                new JLabel("Nome do hóspede:");

        campoNomeHospede =
                new JTextField();

        JLabel labelNumeroQuarto =
                new JLabel("Número do quarto:");

        campoNumeroQuarto =
                new JTextField();

        JLabel labelDataEntrada =
                new JLabel("Data de entrada:");

        campoDataEntrada =
                new JTextField();

        JLabel labelDataSaida =
                new JLabel("Data de saída:");

        campoDataSaida =
                new JTextField();

        painelFormulario.add(labelNomeHospede);
        painelFormulario.add(campoNomeHospede);

        painelFormulario.add(labelNumeroQuarto);
        painelFormulario.add(campoNumeroQuarto);

        painelFormulario.add(labelDataEntrada);
        painelFormulario.add(campoDataEntrada);

        painelFormulario.add(labelDataSaida);
        painelFormulario.add(campoDataSaida);

        // Cria os botões de操作ação do sistema.
        JButton botaoCadastrar =
                new JButton("Cadastrar");

        JButton botaoAtualizar =
                new JButton("Atualizar");

        JButton botaoExcluir =
                new JButton("Excluir");

        JButton botaoLimpar =
                new JButton("Limpar");

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                ESPACAMENTO,
                                5
                        )
                );

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        // Junta o formulário e os botões na parte superior.
        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout(
                                0,
                                ESPACAMENTO
                        )
                );

        painelSuperior.add(
                painelFormulario,
                BorderLayout.NORTH
        );

        painelSuperior.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        criarTabela();

        JScrollPane scrollPane =
                new JScrollPane(tabelaHospedagens);

        painelPrincipal.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelPrincipal.add(
                scrollPane,
                BorderLayout.CENTER
        );

        setContentPane(painelPrincipal);

        // Executa o cadastro quando o botão é clicado.
        botaoCadastrar.addActionListener(
                e -> cadastrar()
        );

        // Executa a atualização quando o botão é clicado.
        botaoAtualizar.addActionListener(
                e -> atualizar()
        );

        // Executa a exclusão quando o botão é clicado.
        botaoExcluir.addActionListener(
                e -> excluir()
        );

        // Limpa os campos do formulário.
        botaoLimpar.addActionListener(
                e -> limparCampos()
        );

        // Preenche os campos ao selecionar uma hospedagem.
        tabelaHospedagens
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        preencherCampos();
                    }
                });
    }

    // Cria a tabela que exibe as hospedagens.
    private void criarTabela() {

        modeloTabela =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Hóspede",
                                "Quarto",
                                "Entrada",
                                "Saída"
                        },
                        0
                ) {

                    // Impede a edição direta das células da tabela.
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        tabelaHospedagens =
                new JTable(modeloTabela);

        tabelaHospedagens.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabelaHospedagens.setRowHeight(25);

        tabelaHospedagens
                .getTableHeader()
                .setReorderingAllowed(false);
    }

    // Cadastra uma nova hospedagem.
    private void cadastrar() {

        String nomeHospede =
                campoNomeHospede.getText().trim();

        String numeroQuartoTexto =
                campoNumeroQuarto.getText().trim();

        String dataEntradaTexto =
                campoDataEntrada.getText().trim();

        String dataSaidaTexto =
                campoDataSaida.getText().trim();

        if (nomeHospede.isBlank()
                || numeroQuartoTexto.isBlank()
                || dataEntradaTexto.isBlank()
                || dataSaidaTexto.isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }

        try {

            Integer numeroQuarto =
                    Integer.parseInt(numeroQuartoTexto);

            LocalDate dataEntrada =
                    LocalDate.parse(
                            dataEntradaTexto,
                            FORMATO_DATA
                    );

            LocalDate dataSaida =
                    LocalDate.parse(
                            dataSaidaTexto,
                            FORMATO_DATA
                    );

            if (numeroQuarto <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "O número do quarto deve ser válido."
                );

                return;
            }

            if (!dataSaida.isAfter(dataEntrada)) {

                JOptionPane.showMessageDialog(
                        this,
                        "A data de saída deve ser posterior à data de entrada."
                );

                return;
            }

            Hospedagem hospedagem =
                    new Hospedagem(
                            nomeHospede,
                            numeroQuarto,
                            dataEntrada,
                            dataSaida
                    );

            repository.cadastrar(hospedagem);

            JOptionPane.showMessageDialog(
                    this,
                    "Hospedagem cadastrada com sucesso!"
            );

            limparCampos();

            carregarHospedagens();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "O número do quarto deve ser um número válido."
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite as datas no formato dd/MM/yyyy."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao cadastrar hospedagem: "
                            + e.getMessage()
            );
        }
    }

    // Atualiza a hospedagem selecionada.
    private void atualizar() {

        int linhaSelecionada =
                tabelaHospedagens.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma hospedagem para atualizar."
            );

            return;
        }

        Long id =
                (Long) modeloTabela.getValueAt(
                        linhaSelecionada,
                        0
                );

        String nomeHospede =
                campoNomeHospede.getText().trim();

        String numeroQuartoTexto =
                campoNumeroQuarto.getText().trim();

        String dataEntradaTexto =
                campoDataEntrada.getText().trim();

        String dataSaidaTexto =
                campoDataSaida.getText().trim();

        if (nomeHospede.isBlank()
                || numeroQuartoTexto.isBlank()
                || dataEntradaTexto.isBlank()
                || dataSaidaTexto.isBlank()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }

        try {

            Integer numeroQuarto =
                    Integer.parseInt(numeroQuartoTexto);

            LocalDate dataEntrada =
                    LocalDate.parse(
                            dataEntradaTexto,
                            FORMATO_DATA
                    );

            LocalDate dataSaida =
                    LocalDate.parse(
                            dataSaidaTexto,
                            FORMATO_DATA
                    );

            if (numeroQuarto <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "O número do quarto deve ser válido."
                );

                return;
            }

            if (!dataSaida.isAfter(dataEntrada)) {

                JOptionPane.showMessageDialog(
                        this,
                        "A data de saída deve ser posterior à data de entrada."
                );

                return;
            }

            Hospedagem hospedagem =
                    new Hospedagem(
                            id,
                            nomeHospede,
                            numeroQuarto,
                            dataEntrada,
                            dataSaida
                    );

            repository.atualizar(hospedagem);

            JOptionPane.showMessageDialog(
                    this,
                    "Hospedagem atualizada com sucesso!"
            );

            limparCampos();

            carregarHospedagens();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "O número do quarto deve ser um número válido."
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Digite as datas no formato dd/MM/yyyy."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao atualizar hospedagem: "
                            + e.getMessage()
            );
        }
    }

    // Exclui a hospedagem selecionada.
    private void excluir() {

        int linhaSelecionada =
                tabelaHospedagens.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma hospedagem para excluir."
            );

            return;
        }

        Long id =
                (Long) modeloTabela.getValueAt(
                        linhaSelecionada,
                        0
                );

        int confirmacao =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente excluir esta hospedagem?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacao == JOptionPane.YES_OPTION) {

            try {

                repository.excluir(id);

                JOptionPane.showMessageDialog(
                        this,
                        "Hospedagem excluída com sucesso!"
                );

                limparCampos();

                carregarHospedagens();

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao excluir hospedagem: "
                                + e.getMessage()
                );
            }
        }
    }

    // Busca as hospedagens no banco e atualiza a tabela.
    private void carregarHospedagens() {

        try {

            List<Hospedagem> hospedagens =
                    repository.listar();

            modeloTabela.setRowCount(0);

            for (Hospedagem hospedagem : hospedagens) {

                modeloTabela.addRow(
                        new Object[]{
                                hospedagem.getId(),
                                hospedagem.getNomeHospede(),
                                hospedagem.getNumeroQuarto(),
                                hospedagem.getDataEntrada()
                                        .format(FORMATO_DATA),
                                hospedagem.getDataSaida()
                                        .format(FORMATO_DATA)
                        }
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar hospedagens: "
                            + e.getMessage()
            );
        }
    }

    // Preenche os campos ao selecionar uma hospedagem.
    private void preencherCampos() {

        int linhaSelecionada =
                tabelaHospedagens.getSelectedRow();

        if (linhaSelecionada == -1) {
            return;
        }

        Long id =
                (Long) modeloTabela.getValueAt(
                        linhaSelecionada,
                        0
                );

        try {

            Hospedagem hospedagem =
                    repository.buscarPorId(id);

            if (hospedagem != null) {

                campoNomeHospede.setText(
                        hospedagem.getNomeHospede()
                );

                campoNumeroQuarto.setText(
                        String.valueOf(
                                hospedagem.getNumeroQuarto()
                        )
                );

                campoDataEntrada.setText(
                        hospedagem.getDataEntrada()
                                .format(FORMATO_DATA)
                );

                campoDataSaida.setText(
                        hospedagem.getDataSaida()
                                .format(FORMATO_DATA)
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao buscar hospedagem: "
                            + e.getMessage()
            );
        }
    }

    // Limpa os campos e remove a seleção da tabela.
    private void limparCampos() {

        campoNomeHospede.setText("");
        campoNumeroQuarto.setText("");
        campoDataEntrada.setText("");
        campoDataSaida.setText("");

        tabelaHospedagens.clearSelection();
    }
}
