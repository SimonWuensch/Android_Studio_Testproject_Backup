package ssi.ssn.com.ssi_service.model.network.request;

public class AbstractRequest {

    class VersionValue {
        private String version;
        private String value;

        public VersionValue(String version, String value) {
            this.version = version;
            this.value = value;
        }

        public String getVersion() {
            return version;
        }

        public String getValue() {
            return value;
        }
    }
}
