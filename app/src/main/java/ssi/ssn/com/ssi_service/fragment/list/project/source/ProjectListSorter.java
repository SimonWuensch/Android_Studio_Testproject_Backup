package ssi.ssn.com.ssi_service.fragment.list.project.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        projects.addAll(sortProjectsByName(statusMap.get(Status.ERROR)));
        projects.addAll(sortProjectsByName(statusMap.get(Status.OK)));
        projects.addAll(sortProjectsByName(statusMap.get(Status.NOT_AVAILABLE)));
        projects.addAll(sortProjectsByName(statusMap.get(Status.NOT_OBSERVATION)));
        return projects;
    }

    public static List<Project> sortProjectsByName(List<Project> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        Collections.sort(list, new Comparator<Project>() {
            @Override
            public int compare(Project lProject, Project rProject) {
                return lProject.getProjectName().compareTo(rProject.getProjectName());
            }
        });
        return list;
    }
}
