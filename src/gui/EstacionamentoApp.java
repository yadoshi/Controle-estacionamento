package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.CarroDAO;
import model.entities.Carro;
import model.dao.DaoFactory;

public class EstacionamentoApp extends JFrame {

    private CarroDAO carroDAO;

    public EstacionamentoApp() {
        carroDAO = DaoFactory.createCarroDao();

        setTitle("Controle de Estacionamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	adicionarCarro();
            }
        });
        buttonsPanel.add(addButton);

        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	excluirCarroPorPlaca();
            }
        });
        buttonsPanel.add(deleteButton);

        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	atualizarCarro();            }
        });
        buttonsPanel.add(updateButton);

        JButton relatorioButton = new JButton("Gerar Relatório");
        relatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio();
            }
        });
        buttonsPanel.add(relatorioButton);

        add(buttonsPanel, BorderLayout.CENTER);
    }
    
    private void adicionarCarro() {
    	  JTextField marcaField = new JTextField(10);
          JTextField placaField = new JTextField(10);
          JTextField corField = new JTextField(10);
          JTextField horarioEntradaField = new JTextField(10);
          JTextField horarioSaidaField = new JTextField(10);

          JPanel myPanel = new JPanel();
          myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
          myPanel.add(new JLabel("Marca:"));
          myPanel.add(marcaField);
          myPanel.add(new JLabel("Placa:"));
          myPanel.add(placaField);
          myPanel.add(new JLabel("Cor:"));
          myPanel.add(corField);
          myPanel.add(new JLabel("Horário de Entrada:"));
          myPanel.add(horarioEntradaField);
          myPanel.add(new JLabel("Horário de Saída:"));
          myPanel.add(horarioSaidaField);

          int result = JOptionPane.showConfirmDialog(null, myPanel, "Adicionar Carro",
              JOptionPane.OK_CANCEL_OPTION);
          if (result == JOptionPane.OK_OPTION) {
              String marca = marcaField.getText();
              String placa = placaField.getText();
              String cor = corField.getText();
              int horarioEntrada = Integer.parseInt(horarioEntradaField.getText());
              int horarioSaida = Integer.parseInt(horarioSaidaField.getText());

              Carro novoCarro = new Carro(marca, placa, cor, horarioEntrada, horarioSaida);
              carroDAO.insert(novoCarro);
              JOptionPane.showMessageDialog(this, "Carro adicionado com sucesso!");
          }
    }
    
    private void excluirCarroPorPlaca() {
        String placa = JOptionPane.showInputDialog("Digite a placa do carro que deseja excluir:");

        if (placa != null && !placa.isEmpty()) {
            Carro carroParaExcluir = carroDAO.findByPlaca(placa);

            if (carroParaExcluir != null) {
                int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o carro com placa " + placa + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                
                if (confirmacao == JOptionPane.YES_OPTION) {
                    carroDAO.deleteByPlaca(placa);
                    JOptionPane.showMessageDialog(this, "Carro excluído com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Carro com a placa " + placa + " não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Placa não fornecida.");
        }
    }
    
    private void atualizarCarro() {
        String placa = JOptionPane.showInputDialog("Digite a placa do carro que deseja atualizar:");

        if (placa != null && !placa.isEmpty()) {
            Carro carroParaAtualizar = carroDAO.findByPlaca(placa);

            if (carroParaAtualizar != null) {
                JTextField marcaField = new JTextField(carroParaAtualizar.getMarca(), 10);
                JTextField corField = new JTextField(carroParaAtualizar.getCor(), 10);
                JTextField horarioEntradaField = new JTextField(String.valueOf(carroParaAtualizar.getHorarioEntrada()), 10);
                JTextField horarioSaidaField = new JTextField(String.valueOf(carroParaAtualizar.getHorarioSaida()), 10);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
                myPanel.add(new JLabel("Marca:"));
                myPanel.add(marcaField);
                myPanel.add(new JLabel("Cor:"));
                myPanel.add(corField);
                myPanel.add(new JLabel("Horário de Entrada:"));
                myPanel.add(horarioEntradaField);
                myPanel.add(new JLabel("Horário de Saída:"));
                myPanel.add(horarioSaidaField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "Atualizar Carro",
                    JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String marca = marcaField.getText();
                    String cor = corField.getText();
                    int horarioEntrada = Integer.parseInt(horarioEntradaField.getText());
                    int horarioSaida = Integer.parseInt(horarioSaidaField.getText());

                    carroParaAtualizar.setMarca(marca);
                    carroParaAtualizar.setCor(cor);
                    carroParaAtualizar.setHorarioEntrada(horarioEntrada);
                    carroParaAtualizar.setHorarioSaida(horarioSaida);

                    carroDAO.update(carroParaAtualizar);
                    JOptionPane.showMessageDialog(this, "Carro atualizado com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Carro com a placa " + placa + " não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Placa não fornecida.");
        }
    }
    
    private void gerarRelatorio() {
        JFrame relatorioFrame = new JFrame("Relatório de Carros");
        relatorioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        relatorioFrame.setSize(600, 400);
        relatorioFrame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        
        model.addColumn("Placa");
        model.addColumn("Marca");
        model.addColumn("Cor");
        model.addColumn("Horário de Entrada");
        model.addColumn("Horário de Saída");

        List<Carro> carros = carroDAO.findAll();
        for (Carro carro : carros) {
            Object[] row = {
                carro.getPlaca(),
                carro.getMarca(),
                carro.getCor(),
                carro.getHorarioEntrada(),
                carro.getHorarioSaida()
            };
            model.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        relatorioFrame.getContentPane().add(scrollPane);
        
        relatorioFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EstacionamentoApp().setVisible(true);
            }
        });
    }
}