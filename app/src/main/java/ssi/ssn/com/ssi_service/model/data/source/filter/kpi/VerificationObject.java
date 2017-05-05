package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public enum VerificationObject {

    LESS_THEN(1),
    LESS_THEN_EQUALS(2),
    GREATER_THEN(3),
    GREATER_THEN_EQUALS(4),
    EQUALS(5);

    private int id;

    private VerificationObject(int id){
        this.id = id;
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
}
