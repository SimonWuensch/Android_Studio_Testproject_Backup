package ssi.ssn.com.ssi_service.test;

public class VersionRest_2_1 extends TestRestResponse {

    // Response geholt von Server http://192.168.66.97:8080/lighthouse-core/ - "project":{"name":"TZ","location":"Giebelstadt","orderNr":"2x1"

    private static String LOGIN = "{\"id\":1003665,\"key\":\"UU9x-oNXlkDtPiQYexF8gZEPa-RaLSBCipbwqLsr\",\"status\":\"LOGGED_IN\",\"actor\":{\"key\":\"admin\"},\"rights\":[\"scada-write\",\"notification-write\",\"security-read\",\"security-write\",\"kpi-read\",\"notification-read\",\"kpi-write\",\"scada-read\"],\"createdOn\":1489145797573,\"lastModifiedOn\":1489146022726,\"clientAddress\":\"192.168.66.254\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\",\"language\":\"de\"}";
    private static String LOGOUT = "{\"id\":1003665,\"key\":\"UU9x-oNXlkDtPiQYexF8gZEPa-RaLSBCipbwqLsr\",\"status\":\"LOGGED_OUT\",\"createdOn\":1489145797573,\"lastModifiedOn\":1489146068295,\"clientAddress\":\"192.168.66.254\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\",\"language\":\"de\"}";
    private static String APPLICATION = "{\"state\":{\"since\":1487836731041,\"status\":\"RUNNING\"},\"enabledModules\":[\"Kpi.UserCharts\",\"Kpi.UserDashboards\",\"Scada\",\"Scada.Widgets\"],\"project\":{\"name\":\"TZ\",\"location\":\"Giebelstadt\",\"orderNr\":\"2x1\"},\"build\":{\"version\":\"2.1.0.0-DEV\",\"number\":\"8775\",\"builtBy\":\"Scott.Hady\",\"builtOn\":1487572665156},\"time\":{\"stamp\":1489143667083,\"offset\":3600000}}";
    private static String APPLICATION_CONFIG = "<runtime-config runtime-type=\"TEST\" version=\"2.0\" license-key=\"941debed69bb433a0f035fce5a92d0733cc725ec\"> <product> <platform-modules> <application-module suppress-threshold=\"0\" stop-on-error=\"false\"/> <security-module> <sessions timeout=\"180\" floating-max-active=\"20\" validation-period=\"1800\" remove-after=\"172800\"/> </security-module> <notification-module remove-after=\"12\"/> <kpi-module user-charts-enabled=\"true\" user-dashboards-enabled=\"true\" remove-after=\"12\"/> <components-module> <app-server service-name=\"LIGHTHOUSE\"> <connection address=\"http://localhost:9990\"/> <application-connection address=\"http://localhost:8080\"/> </app-server> <opcua-server initialize-client=\"true\" manage=\"true\" start-timeout=\"5000\" stop-timeout=\"5000\" overwatch=\"60\"> <connection address=\"opc.tcp://localhost:6110\"/> </opcua-server> <database> <connection address=\"jdbc:oracle:thin:@//localhost:1521/XE\" user=\"lighthouse\" password=\"lighthouse\"/> </database> </components-module> </platform-modules> <plugin-modules> <scada-module widgets-enabled=\"true\" livebit-enabled=\"false\" enabled=\"true\" auto-start=\"true\"> <fire-alarms enabled=\"false\"/> </scada-module> <smt-module enabled=\"false\" auto-start=\"false\"> <smt-adapter address=\"http://localhost:8081/smt-adapter\" user=\"lh\" password=\"lh\"/> </smt-module> </plugin-modules> </product> <project name=\"TZ\" order-nr=\"2x1\" location=\"Giebelstadt\"> <customer name=\"TZ\" address=\"Giebelstadt\"/> </project> </runtime-config>";
    private static String COMPONENT_OPCUA = "{\"name\":\"OpcUa\",\"config\":{\"home\":\"${RUNTIME_HOME}/components/opcua\",\"address\":\"opc.tcp://localhost:6110\",\"manage\":true,\"startTimeout\":5000,\"stopTimeout\":5000,\"overwatch\":60},\"state\":{\"name\":\"OpcUa\",\"managed\":true,\"status\":\"ONLINE\",\"since\":1489732031130}}";
    //private static String COMPONENT_OPCUA = "{\"name\":\"OPCUA\",\"config\":{\"home\":\"${RUNTIME_HOME}/components/opcua\",\"address\":\"opc.tcp://localhost:7110\",\"manage\":true,\"startTimeout\":15000,\"stopTimeout\":10000,\"overwatch\":60},\"state\":{\"name\":\"OpcUa\",\"managed\":true,\"status\":\"NOT_ONLINE\",\"since\":1483534924779}}";
    private static String MODULE_APPLICATION_STATE = "{\"name\":\"Application\",\"status\":\"RUNNING\",\"since\":1487836731041}";
    private static String MODULE_SECURITY_STATE = "{\"name\":\"Security\",\"status\":\"RUNNING\",\"since\":1487836730520}";
    private static String MODULE_COMPONENT_STATE = "{\"name\":\"Component\",\"status\":\"RUNNING\",\"since\":1482487804560}";
    //private static String MODULE_COMPONENT_STATE = "{\"name\":\"Component\",\"status\":\"NOT_RUNNING\",\"since\":1482487804560}";
    private static String MODULE_NOTIFICATION_STATE = "{\"name\":\"Notification\",\"status\":\"RUNNING\",\"since\":1482487804601}";
    private static String MODULE_KPI_STATE = "{\"name\":\"Kpi\",\"status\":\"RUNNING\",\"since\":1482487804584}";
    private static String MODULE_SCADA_STATE = "{\"name\":\"Scada\",\"status\":\"RUNNING\",\"since\":1482487804668}";
    //private static String MODULE_SMT_STATE = "{\"name\":\"SMT\",\"status\":\"RUNNING\",\"since\":1482487804668}";
    private static String OS = "{\"name\":\"Windows Server 2012 R2\",\"version\":\"6.3\",\"arch\":\"amd64\",\"processors\":6,\"avgSystemLoad\":-1.0,\"processCpuLoad\":0.0007378449213758091,\"processCpuLoadLast\":0.0007378449213758091,\"systemCpuLoad\":0.0050212264379012606,\"systemCpuLoadLast\":0.0050212264379012606,\"diskFreeSpace\":125968,\"jvmImplSpecifics\":{\"jvmImplClassName\":\"sun.management.OperatingSystemImpl\",\"CommittedVirtualMemorySize\":\"2681110528\",\"CommittedVirtualMemorySize0\":\"2681110528\",\"FreePhysicalMemorySize\":\"27370958848\",\"FreeSwapSpaceSize\":\"28562452480\",\"ProcessCpuLoad\":\"7.378449213758091E-4\",\"ProcessCpuTime\":\"2602156250000\",\"SystemCpuLoad\":\"0.0050212264379012606\",\"TotalPhysicalMemorySize\":\"34090897408\",\"TotalSwapSpaceSize\":\"39191171072\"},\"disk\":{\"usable\":125968,\"total\":142764,\"fillPercent\":\"11.77\"}}";
    private static String SCADA_SYSTEMS = "[{\"name\":\"C01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.28\",\"nodePath\":\"PLANT.PHYSICAL.CS.C01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1488160754278,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"F01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.25\",\"nodePath\":\"PLANT.PHYSICAL.CS.F01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731754,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"R01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.21\",\"nodePath\":\"PLANT.PHYSICAL.SRM.R01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731756,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"R42\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.31\",\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731757,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"STS11\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.13\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS11\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731758,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"STS12\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.16\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS12\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731759,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"STS21\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.44\",\"nodePath\":\"PLANT.PHYSICAL.SRM.STS21\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731760,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"TL01\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.10\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL01\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731761,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"TL20\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.40\",\"nodePath\":\"PLANT.PHYSICAL.CS.TL20\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTED\",\"connectionStatusTimestamp\":1487836731763,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"},{\"name\":\"W111\",\"group\":\"DEFAULT\",\"address\":\"192.168.66.26\",\"nodePath\":\"PLANT.PHYSICAL.Wrapper.W111\",\"type\":\"S7\",\"integrationType\":\"S7\",\"systemType\":\"S7\",\"connectionStatus\":\"CONNECTION_ERROR\",\"connectionStatusTimestamp\":1487836731765,\"connectionActive\":false,\"responsiveness\":\"UNKNOWN\"}]";
    private static String SCADA_SCANNERS = "[]";
    private static String SESSIONSCURRENT_LOGGED_IN = "{\"id\":1004018,\"key\":\"PNhhdy7-P087C5GHfi20_znkNxVGtCG3Qu2bVn1n\",\"status\":\"LOGGED_IN\",\"actor\":{\"key\":\"admin\"},\"rights\":[\"scada-write\",\"notification-write\",\"security-read\",\"security-write\",\"kpi-read\",\"notification-read\",\"kpi-write\",\"scada-read\"],\"createdOn\":1489145797573,\"lastModifiedOn\":1489147174147,\"clientAddress\":\"192.168.66.254\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\",\"language\":\"de\"}";
    private static String SESSIONSCURRENT_LOGGED_OUT = "{\"id\":1004057,\"key\":\"sPBqwVuCAKaR2-ICtcdhrtiK8OS85Tc1bf-i1yLB\",\"status\":\"LOGGED_OUT\",\"createdOn\":1489145797573,\"lastModifiedOn\":1489147252974,\"clientAddress\":\"192.168.66.254\",\"clientAgent\":\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\",\"language\":\"de\"}";

    private static String NOTIFICATION = "{\"count\":18,\"data\":[{\"id\":1002777,\"startTime\":1489143259633,\"text\":\"Positionsfehler  Zentrierung\",\"textmap\":{\"DE\":\"Positionsfehler  Zentrierung\",\"EN\":\"Position error  interlock\"},\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0107.F0107RBC084\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":6010,\"definition\":{\"key\":\"a07a266d7f5151e230822780465585ca54cf3712\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":1811}},{\"id\":1002778,\"startTime\":1489143259633,\"text\":\"Freimass\",\"textmap\":{\"DE\":\"Freimass\",\"EN\":\"Clearance\"},\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0107.F0107LSL085\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":6108,\"definition\":{\"key\":\"b8460ee7bc8fb56486d81d488d10ee94a115b315\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":1813}},{\"id\":1001552,\"startTime\":1489139556662,\"text\":\"C01WC1100 Meldung Fensterband ist Leer\",\"textmap\":{\"DE\":\"C01WC1100 Meldung Fensterband ist Leer\",\"EN\":\"C01WC1100 message window conveyer is empty\"},\"nodePath\":\"PLANT.PHYSICAL.CS.C01.C0100.C0100XXX201\",\"tKey\":\"PLANT.NS.TRIGGERS.C01\",\"tIdx\":619,\"definition\":{\"key\":\"6eaa193a4f897e099be820fbf3a11ddae52c2d58\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3949}},{\"id\":1001114,\"startTime\":1489138179816,\"text\":\"TL20 +P01 Meldung warte auf Order\",\"textmap\":{\"DE\":\"TL20 +P01 Meldung warte auf Order\",\"EN\":\"TL20 +P01 message wait for order\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL2001\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":175,\"definition\":{\"key\":\"405f2c146930684fdbb41252269359f046511409\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3980}},{\"id\":977423,\"startTime\":1489062822679,\"text\":\"NotHalt Gassentür vorn (10.4)(51;2;1;0)\",\"textmap\":{\"DE\":\"NotHalt Gassentür vorn (10.4)(51;2;1;0)\",\"EN\":\"EStop aisle door front (10.4)(51;2;1;0)\"},\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"tKey\":\"PLANT.NS.TRIGGERS.R42\",\"tIdx\":4,\"definition\":{\"key\":\"ca82cf7cf4d762f81af9d3d001cd97833aaa8e25\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":2005}},{\"id\":977424,\"startTime\":1489062822679,\"text\":\"NotHalt Gassentür hinten (10.5)(51;2;1;0)\",\"textmap\":{\"DE\":\"NotHalt Gassentür hinten (10.5)(51;2;1;0)\",\"EN\":\"EStop aisle door rear (10.5)(51;2;1;0)\"},\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"tKey\":\"PLANT.NS.TRIGGERS.R42\",\"tIdx\":5,\"definition\":{\"key\":\"e34592ce3b00b61181dbd39e7da455563a784b9e\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":2006}},{\"id\":977425,\"startTime\":1489062822679,\"text\":\"Störung nur am RBG quittierbar (141.5)(51;2;1;0)\",\"textmap\":{\"DE\":\"Störung nur am RBG quittierbar (141.5)(51;2;1;0)\",\"EN\":\"Fault reset only possible at SRM (141.5)(51;2;1;0)\"},\"nodePath\":\"PLANT.PHYSICAL.SRM.R42\",\"tKey\":\"PLANT.NS.TRIGGERS.R42\",\"tIdx\":1053,\"definition\":{\"key\":\"824d5a184aceb42bfab859bce143b7cd035d5a86\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3054}},{\"id\":591893,\"startTime\":1487836800541,\"text\":\"Verbindung unterbrochen\",\"textmap\":{\"DE\":\"Verbindung unterbrochen\",\"EN\":\"Connection lost\"},\"nodePath\":\"PLANT.PHYSICAL.Wrapper.W111\",\"definition\":{\"key\":\"a3247a110ded748649648afe98491faed5d547e5\",\"type\":\"SCADA\",\"severity\":\"WARN\"}},{\"id\":591825,\"startTime\":1487836733040,\"text\":\"TL2013LSD003 Wartungsintervall abgelaufen\",\"textmap\":{\"DE\":\"TL2013LSD003 Wartungsintervall abgelaufen\",\"EN\":\"TL2013LSD003 maintenance interval expired\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":64,\"definition\":{\"key\":\"1ba4471acd4c78caa638b6f34a488e040dd9b60c\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3618}},{\"id\":591827,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 23 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 23 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 23 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":546,\"definition\":{\"key\":\"5ba33eb0fe20f09a749fc4b55a6069fc34a75e56\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591828,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 33 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 33 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 33 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":642,\"definition\":{\"key\":\"905a44eb7408190d964e62254988cc0e36c5822a\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591829,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 41 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 41 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 41 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":674,\"definition\":{\"key\":\"7a4d0fefb97d0390110afaa2ac4bebe4c101d00c\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591830,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 43 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 43 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 43 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":738,\"definition\":{\"key\":\"12d08a1f81b43b9672439a931d1186a441a82787\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591831,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 52 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 52 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 52 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":802,\"definition\":{\"key\":\"7bf48058bc31e4cc26e034afa18a18d8d4ddb3eb\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591832,\"startTime\":1487836733040,\"text\":\"TL2014 Node-ID 63 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL2014 Node-ID 63 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL2014 Node-ID 63 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL20.TL20Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL20\",\"tIdx\":930,\"definition\":{\"key\":\"e729610942f12fe9a8386a0ad09a7310972a7165\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591823,\"startTime\":1487836733008,\"text\":\"TL0113LAM003 Wartungsintervall abgelaufen\",\"textmap\":{\"DE\":\"TL0113LAM003 Wartungsintervall abgelaufen\",\"EN\":\"TL0113LAM003 maintenance interval expired\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL01.TL01Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL01\",\"tIdx\":64,\"definition\":{\"key\":\"c60ca3a6edeec5f9edc919550108bff0ff1a4aac\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3618}},{\"id\":591824,\"startTime\":1487836733008,\"text\":\"TL0114 Node-ID 21 Meldung einer oder mehrere Motoren abgeschaltet\",\"textmap\":{\"DE\":\"TL0114 Node-ID 21 Meldung einer oder mehrere Motoren abgeschaltet\",\"EN\":\"TL0114 Node-ID 21 message one or some motors off\"},\"nodePath\":\"PLANT.PHYSICAL.CS.TL01.TL01Lift\",\"tKey\":\"PLANT.NS.TRIGGERS.TL01\",\"tIdx\":482,\"definition\":{\"key\":\"00cb229ef7f107a4b57617201bfa082526c1884b\",\"type\":\"SCADA\",\"severity\":\"INFO\",\"docNr\":3961}},{\"id\":591801,\"startTime\":1487836731055,\"text\":\"APP-INFO: Application Started\",\"textmap\":{\"DE\":\"APP-INFO: Application Started\",\"EN\":\"APP-INFO: Application Started\"},\"definition\":{\"key\":\"5e07799b997382e6a55da379bed44f07e9898d90\",\"type\":\"APPLICATION\",\"severity\":\"INFO\"}}]}";
    private static String NOTIFICATION_COUNT = "18";
    private static String NOTIFICATION_FILTER_F0107LSL085 = "{\"count\":1,\"data\":[{\"id\":1002778,\"startTime\":1489143259633,\"text\":\"Freimass\",\"textmap\":{\"DE\":\"Freimass\",\"EN\":\"Clearance\"},\"nodePath\":\"PLANT.PHYSICAL.CS.F01.F0107.F0107LSL085\",\"tKey\":\"PLANT.NS.TRIGGERS.F01\",\"tIdx\":6108,\"definition\":{\"key\":\"b8460ee7bc8fb56486d81d488d10ee94a115b315\",\"type\":\"SCADA\",\"severity\":\"ERROR\",\"docNr\":1813}}]}";
    private static String NOTIFICATION_FILTER_NULL = "{ \"count\":0, \"data\":[ ] }";

    private static String KPI_DEFINITIONS = "[{\"key\":\"lh.application.CpuProcessLoad\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Lighthouse Process CPU Usage\",\"units\":\"%\",\"description\":\"CPU usage of Lighthouse\",\"removeAfter\":6},{\"key\":\"lh.application.CpuSystemLoad\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"System CPU Usage\",\"units\":\"%\",\"description\":\"Overall CPU usage of system\",\"removeAfter\":6},{\"key\":\"lh.application.DiskFreeSpace\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"MIN\",\"location\":\"Lighthouse\",\"name\":\"Lighthouse File System Free Space\",\"units\":\"MB\",\"description\":\"Amount of space left in File System (Disk)\",\"removeAfter\":6},{\"key\":\"lh.notification.Active\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Active Notifications\",\"units\":\"Count\",\"description\":\"Number of Active Notifications by Type\",\"spectrumCategories\":[\"ERROR\",\"WARN\",\"INFO\"],\"removeAfter\":6},{\"key\":\"lh.notification.Activated\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Activated Notifications\",\"units\":\"Count\",\"description\":\"Number of Notifications Activated\",\"spectrumCategories\":[\"ERROR\",\"WARN\",\"INFO\"],\"removeAfter\":6},{\"key\":\"lh.security.ActiveSessions\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"MAX\",\"location\":\"Lighthouse\",\"name\":\"Active Sessions\",\"units\":\"Sessions\",\"description\":\"Number of Active Sessions\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.Updates\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Trigger Updates\",\"units\":\"Updates\",\"description\":\"Number of OPC-UA trigger updates\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.ProcessingTime\",\"provider\":\"Lighthouse\",\"type\":\"AVERAGE\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"AVG\",\"location\":\"Lighthouse\",\"name\":\"Trigger Processing Time\",\"units\":\"MS/Update\",\"description\":\"Amount of time to process the trigger updates\",\"removeAfter\":6},{\"key\":\"lh.scada.trigger.LostNotifications\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Lost Notifications\",\"units\":\"Notifications\",\"description\":\"Number of Lost Notifications\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Sent\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Sent\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams sent\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Received\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Received\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams received\",\"removeAfter\":6},{\"key\":\"lh.scada.ngkp.Ignored\",\"provider\":\"Lighthouse\",\"type\":\"SINGULAR_LONG\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"Lighthouse\",\"name\":\"Telegrams Ignored\",\"units\":\"Telegrams\",\"description\":\"Number of NGKP telegrams ignored\",\"removeAfter\":6},{\"key\":\"KPI.CS.F01.F0101CC001.Occupied\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"F0101CC001\",\"name\":\"Occupation of F0101CC001\",\"units\":\"MS\",\"description\":\"Occupation of F0101CC001\",\"spectrumCategories\":[\"Occupied\",\"Unoccupied\"],\"removeAfter\":6},{\"key\":\"KPI.SRM.R01.Status\",\"provider\":\"Lighthouse\",\"type\":\"SPECTRUM\",\"minimalGranularity\":\"MINUTE\",\"aggregationStrategy\":\"SUM\",\"location\":\"R01\",\"name\":\"R01 Status\",\"units\":\"MS\",\"description\":\"Status of SRM R01\",\"spectrumCategories\":[\"Zero\",\"One\",\"Two\",\"Three\"],\"removeAfter\":6}]";
    private static String KPI_MESUREMENTS = "[{\"defKey\":\"lh.application.CpuProcessLoad\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0006944492210976702,\"avg\":0.0006944492210976702,\"numSamples\":1,\"min\":0.0006944492210976702,\"max\":0.0006944492210976702,\"stdDev\":0.0},{\"defKey\":\"lh.application.CpuSystemLoad\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0041598169459409196,\"avg\":0.0041598169459409196,\"numSamples\":1,\"min\":0.0041598169459409196,\"max\":0.0041598169459409196,\"stdDev\":0.0},{\"defKey\":\"lh.application.DiskFreeSpace\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":125894},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":0},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":2.0,\"channelIndex\":1},{\"defKey\":\"lh.notification.Active\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":13.0,\"channelIndex\":2},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":2},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":0},{\"defKey\":\"lh.notification.Activated\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":1},{\"defKey\":\"lh.security.ActiveSessions\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":50.0,\"avg\":50.0,\"numSamples\":2,\"min\":50.0,\"max\":50.0,\"stdDev\":0.0},{\"defKey\":\"lh.scada.trigger.Updates\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0},{\"defKey\":\"lh.scada.trigger.ProcessingTime\",\"type\":\"AVERAGE\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"avg\":0.0,\"numSamples\":0,\"min\":0.0,\"max\":0.0,\"stdDev\":0.0},{\"defKey\":\"lh.scada.trigger.LostNotifications\",\"type\":\"SINGULAR_LONG\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0},{\"defKey\":\"KPI.CS.F01.F0101CC001.Occupied\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":0},{\"defKey\":\"KPI.CS.F01.F0101CC001.Occupied\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":60000.0,\"channelIndex\":1},{\"defKey\":\"KPI.SRM.R01.Status\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":60000.0,\"channelIndex\":0},{\"defKey\":\"KPI.SRM.R01.Status\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":1},{\"defKey\":\"KPI.SRM.R01.Status\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":2},{\"defKey\":\"KPI.SRM.R01.Status\",\"type\":\"SPECTRUM\",\"granularity\":\"MINUTE\",\"timestamp\":1491552840000,\"value\":0.0,\"channelIndex\":3}]";

    public VersionRest_2_1() {
        super("2.1");

        addResponse("/services/security/login/admin/admin", LOGIN);
        addResponse("/services/security/logout", LOGOUT);
        addResponse("/services/application", APPLICATION);
        addResponse("/services/application/config", APPLICATION_CONFIG);
        //addResponse("/services/component/license", COMPONENT_LICENSE); deleted 2.1
        addResponse("/services/component/OpcUa", COMPONENT_OPCUA);
        //addResponse("/services/component/webmi", COMPONENT_WEBMI);
        addResponse("/services/application/state", MODULE_APPLICATION_STATE);
        addResponse("/services/security/state", MODULE_SECURITY_STATE);
        addResponse("/services/component/state", MODULE_COMPONENT_STATE);
        addResponse("/services/notification/state", MODULE_NOTIFICATION_STATE);
        addResponse("/services/kpi/state", MODULE_KPI_STATE);
        addResponse("/services/scada/state", MODULE_SCADA_STATE);
        //addResponse("/services/smt/state", MODULE_SMT_STATE);
        addResponse("/services/application/diagnostics/os", OS);
        addResponse("/services/scada/systems", SCADA_SYSTEMS);
        addResponse("/services/scada/scanners", SCADA_SCANNERS);
        addResponse("/services/security/sessions/current", SESSIONSCURRENT_LOGGED_IN);

        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>>", NOTIFICATION);
        addResponse("/services/notification/count?orderBy=$<<startTime>> DESC&condition=$<<active>>", NOTIFICATION_COUNT);
        addResponse("/services/notification/table?condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> = 'Freimass') AND ($<<nodePath>> IN ('F0107LSL085')))", NOTIFICATION_FILTER_F0107LSL085);
        addResponse("/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> = '') AND ($<<nodePath>> IN ('')))", NOTIFICATION_FILTER_NULL);

        addResponse("/services/kpi/definitions", KPI_DEFINITIONS);
        addResponse("/services/kpi/measurements", KPI_MESUREMENTS);
    }
}
