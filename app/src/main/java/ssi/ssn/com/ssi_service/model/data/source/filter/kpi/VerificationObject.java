package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public enum VerificationObject {

    LESS_THEN(0, "<"),
    LESS_THEN_EQUALS(1, "<="),
    GREATER_THEN(2, ">"),
    GREATER_THEN_EQUALS(3, ">="),
    EQUALS(4, "=");

    private int id;
    private String icon;

    private VerificationObject(int id, String icon){
        this.id = id;
        this.icon = icon;
    }

    public boolean check(int first, int second){
        switch (id){
            case 1:
                return first < second;
            case 2:
                return first <= second;
            case 3:
                return first > second;
            case 4:
                return first >= second;
            case 5:
                return first == second;
        }
        throw new NullPointerException("No verification Object with id [" + id + "] found...");
    }

    public boolean check(long first, long second){
        switch (id){
            case 1:
                return first < second;
            case 2:
                return first <= second;
            case 3:
                return first > second;
            case 4:
                return first >= second;
            case 5:
                return first == second;
        }
        throw new NullPointerException("No verification Object with id [" + id + "] found...");
    }

    public boolean check(double first, double second){
        switch (id){
            case 1:
                return first < second;
            case 2:
                return first <= second;
            case 3:
                return first > second;
            case 4:
                return first >= second;
            case 5:
                return first == second;
        }
        throw new NullPointerException("No verification Object with id [" + id + "] found...");
    }

    public boolean check(String first, String second){
        switch (id){
            case 5:
                return first == second;
        }
        throw new NullPointerException("No verification Object with id [" + id + "] found...");
    }

    public String getIcon(){
        return icon;
    }

    public int getID(){
        return id;
    }
}
