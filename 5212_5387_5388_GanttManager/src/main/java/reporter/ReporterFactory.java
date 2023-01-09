package reporter;

import java.util.List;

import backend.ReportType;
import domainclasses.Task;

public class ReporterFactory {
    
    private static ReporterFactory instance = null;

    private ReporterFactory() {
    }

    public static ReporterFactory getInstance() {
        if (instance == null) {
            instance = new ReporterFactory();
        }
        return instance;
    }

    public static IReporter getReporter(String path, List<Task> tasks, ReportType type) {
        Reporter reporter = new Reporter(path, tasks, type);
        if (type == ReportType.TEXT) {
            return reporter;
        } else if (type == ReportType.HTML) {
            return reporter;
        } else if (type == ReportType.MD) {
            return reporter;
        }
        else {
            return null;
        }
    }
}
