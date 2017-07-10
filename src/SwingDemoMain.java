import com.sun.prism.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 * Created by Huangxiutao on 2017/7/10.
 */
public class SwingDemoMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("mainPanel");
        JPanel rootPanel=new SwingDemoMain().mainPanel;
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);//窗口大小
        frame.setLocationRelativeTo(rootPanel);
        frame.setVisible(true);
    }

    private JComboBox comboBoxDbType;
    private JComboBox comboBoxDb;
    private JTextField textFieldIPAddr;
    private JTextField textFieldPort;
    private JPanel mainPanel;
    private JTextField textFieldUser;
    private JPasswordField textFieldPassword;
    private JButton buttonConnect;
    private JButton buttonExport;
    private JLabel labelMsg;

    public SwingDemoMain() {
        comboBoxDbType.addItem("MySQL");
        comboBoxDbType.addItem("SQL Server");
        comboBoxDbType.addItem("Oracle");
        comboBoxDbType.addItem("DB2");

        //连接按钮事件
        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==buttonConnect){
                    if (null!=comboBoxDbType.getSelectedItem()&&!StringUtil.idEmpty(textFieldIPAddr.getText())&&!StringUtil.idEmpty(textFieldPort.getText())&&!StringUtil.idEmpty(textFieldUser.getText())&&!StringUtil.idEmpty(String.copyValueOf(textFieldPassword.getPassword()))){
                        Connection con=DBUtils.getMySqlCon(textFieldIPAddr.getText().trim(),Integer.valueOf(textFieldPort.getText().trim()),"information_schema",textFieldUser.getText().trim(),String.copyValueOf(textFieldPassword.getPassword()).trim());
                        if(null!=con){
                            labelMsg.setForeground(new java.awt.Color(34,139,34));
                            labelMsg.setText("连接成功");
                            try {
                                if (con!=null) con.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                JOptionPane.showMessageDialog(null,e1.getMessage(),"错误信息",ERROR_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(null,"数据库连接失败！","错误信息",ERROR_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"请填写完整的信息！","提示信息",WARNING_MESSAGE);
                    }
                }
            }
        });

        //输入密码后加载数据库
        textFieldPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("密码输入框获得焦点》》》》");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(comboBoxDbType.getSelectedItem());
                System.out.println(textFieldIPAddr.getText());
                System.out.println(textFieldPort.getText());
                System.out.println(textFieldUser.getText());
                System.out.println(textFieldPassword.getPassword());

                if (null!=comboBoxDbType.getSelectedItem()&&!StringUtil.idEmpty(textFieldIPAddr.getText())&&!StringUtil.idEmpty(textFieldPort.getText())&&!StringUtil.idEmpty(textFieldUser.getText())&&!StringUtil.idEmpty(String.copyValueOf(textFieldPassword.getPassword()))){
                    Connection con=DBUtils.getMySqlCon(textFieldIPAddr.getText().trim(),Integer.valueOf(textFieldPort.getText().trim()),"information_schema",textFieldUser.getText().trim(),String.copyValueOf(textFieldPassword.getPassword()).trim());
                    PreparedStatement st= null;
                    ResultSet rs=null;
                    if(null!=con){
                        try {
                            //最原始的写法。。。。感觉好陌生了。。。。
                            st = con.prepareStatement("SELECT SCHEMA_NAME FROM SCHEMATA");
                            rs=st.executeQuery();
                            while (rs.next()){
                                comboBoxDb.addItem(rs.getString(1));//从1开始的。。。。
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }finally {
                            try {
                                if (rs!=null) rs.close();
                                if (st!=null) st.close();
                                if (con!=null) con.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"获取数据库连接失败！","错误信息",ERROR_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"请填写完整的信息！","提示信息",WARNING_MESSAGE);
                }
            }
        });
    }
}
