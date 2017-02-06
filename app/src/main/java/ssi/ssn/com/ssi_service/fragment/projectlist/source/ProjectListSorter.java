package ssi.ssn.com.ssi_service.fragment.projectlist.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;

public class ProjectListSorter {

    private static String TAG = ProjectListSorter.class.getSimpleName();

    public static List<Project> sortProjectsByStatus(List<Project> list) {
        Map<Status, List<Project>> statusMap = new HashMap<Status, List<Project>>() {
            {
                put(Status.OK, new ArrayList<Project>());
                put(Status.ERROR, new ArrayList<Project>());
                put(Status.NOT_AVAILABLE, new ArrayList<Project>());
                put(Status.NOT_OBSERVATION, new ArrayList<Project>());
            }
        };
        for (Status status : statusMap.keySet()) {
            for (Project project : list) {
                if (project.getStatus().equals(status)) {
                    statusMap.get(status).add(project);
                }
            }
        }

        List<Project> projects = new ArrayList<>();
        for (Status status : statusMap.keySet()) {
            if (!statusMap.get(status).isEmpty()) {
                projects.addAll(sortProjectsByName(statusMap.get(status)));
            }
        }
        return projects;
    }

    public static List<Project> sortProjectsByName(List<Project> list) {
        char start = 'a';
        List<Project> sorted = new ArrayList<Project>();

        for (int i = 0; i <= 25; i++) {
            for (Project project : list) {
                if (project.getProjectName() == null) {
                    sorted.add(0, project);
                    continue;
                }
                if (project.getProjectName().startsWith("" + start) || project.getProjectName().startsWith(("" + start).toUpperCase())) {
                    sorted.add(project);
                }
            }
            start++;
        }
        return sorted;
    }
}
