package br.com.grupo.sistemacadastro.repository;

import br.com.grupo.sistemacadastro.database.ConnectionFactory;
import br.com.grupo.sistemacadastro.model.Hospedagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospedagemRepository {

    // Cadastra uma nova hospedagem no banco de dados.
    public void cadastrar(Hospedagem hospedagem) throws SQLException {

        String sql = """
                INSERT INTO hospedagens
                (nome_hospede, numero_quarto, data_entrada, data_saida)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, hospedagem.getNomeHospede());
            statement.setInt(2, hospedagem.getNumeroQuarto());
            statement.setDate(
                    3,
                    Date.valueOf(hospedagem.getDataEntrada())
            );
            statement.setDate(
                    4,
                    Date.valueOf(hospedagem.getDataSaida())
            );

            statement.executeUpdate();

            // Recupera o ID gerado automaticamente pelo banco.
            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    hospedagem.setId(resultSet.getLong(1));
                }
            }
        }
    }

    // Lista todas as hospedagens cadastradas no banco.
    public List<Hospedagem> listar() throws SQLException {

        String sql = """
                SELECT id, nome_hospede, numero_quarto,
                       data_entrada, data_saida
                FROM hospedagens
                """;

        List<Hospedagem> hospedagens = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Hospedagem hospedagem = new Hospedagem(
                        resultSet.getLong("id"),
                        resultSet.getString("nome_hospede"),
                        resultSet.getInt("numero_quarto"),
                        resultSet.getDate("data_entrada").toLocalDate(),
                        resultSet.getDate("data_saida").toLocalDate()
                );

                hospedagens.add(hospedagem);
            }
        }

        return hospedagens;
    }

    // Busca uma hospedagem específica pelo seu ID.
    public Hospedagem buscarPorId(Long id) throws SQLException {

        String sql = """
                SELECT id, nome_hospede, numero_quarto,
                       data_entrada, data_saida
                FROM hospedagens
                WHERE id = ?
                """;

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Hospedagem(
                            resultSet.getLong("id"),
                            resultSet.getString("nome_hospede"),
                            resultSet.getInt("numero_quarto"),
                            resultSet.getDate("data_entrada").toLocalDate(),
                            resultSet.getDate("data_saida").toLocalDate()
                    );
                }
            }
        }

        return null;
    }

    // Atualiza os dados de uma hospedagem existente.
    public void atualizar(Hospedagem hospedagem) throws SQLException {

        String sql = """
                UPDATE hospedagens
                SET nome_hospede = ?,
                    numero_quarto = ?,
                    data_entrada = ?,
                    data_saida = ?
                WHERE id = ?
                """;

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, hospedagem.getNomeHospede());
            statement.setInt(2, hospedagem.getNumeroQuarto());
            statement.setDate(
                    3,
                    Date.valueOf(hospedagem.getDataEntrada())
            );
            statement.setDate(
                    4,
                    Date.valueOf(hospedagem.getDataSaida())
            );
            statement.setLong(5, hospedagem.getId());

            statement.executeUpdate();
        }
    }

    // Exclui uma hospedagem pelo seu ID.
    public void excluir(Long id) throws SQLException {

        String sql = "DELETE FROM hospedagens WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            statement.executeUpdate();
        }
    }
}