package sample;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import sample.SQLConnection;

import java.sql.Connection;

public class ReportFunctions {
    public void getTopFiveUsers(){
        try {
            Connection conc = SQLConnection.getInstance().getConnection();
            String reportPath = "C:\\Users\\kamal\\IdeaProjects\\DB_PROJECT_GUI\\report\\TopFiveUsers.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conc);
            JasperViewer.viewReport(jasperPrint);
            conc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTopTenBooks(){
        try {
            Connection conc = SQLConnection.getInstance().getConnection();
            String reportPath = "C:\\Users\\kamal\\IdeaProjects\\DB_PROJECT_GUI\\report\\TopTenBooks.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conc);
            JasperViewer.viewReport(jasperPrint);
            conc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTopSales(){
        try {
            Connection conc = SQLConnection.getInstance().getConnection();
            String reportPath = "C:\\Users\\kamal\\IdeaProjects\\DB_PROJECT_GUI\\report\\Sales.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conc);
            JasperViewer.viewReport(jasperPrint);
            conc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
